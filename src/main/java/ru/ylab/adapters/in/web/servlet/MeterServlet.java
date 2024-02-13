package ru.ylab.adapters.in.web.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ru.ylab.adapters.in.web.dto.UtilityMeterModel;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.adapters.util.Json;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.in.*;
import ru.ylab.application.service.*;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Создан: 10.02.2024.
 *
 * @author Pesternikov Danil
 */
@AllArgsConstructor
@Loggable
@WebServlet("/meter/*")
public class MeterServlet extends HttpServlet {

    private final GetAllUtilityMeter getAllUtilityMeter;
    private final GetAllUtilityMeterById getAllUtilityMeterById;
    private final GetLastUtilityMeter getLastUtilityMeter;
    private final GetUtilityMeterByMonth getUtilityMeterByMonth;
    private final SubmitUtilityMeter submitUtilityMeter;

    public MeterServlet() {
        try {
            this.getAllUtilityMeter = ApplicationContextInitializationListener.context.getObject(GetAllUtilityMeterImpl.class);
            this.getAllUtilityMeterById = ApplicationContextInitializationListener.context.getObject(GetAllUtilityMeterByIdImpl.class);
            this.getLastUtilityMeter = ApplicationContextInitializationListener.context.getObject(GetLastUtilityMeterImpl.class);
            this.getUtilityMeterByMonth = ApplicationContextInitializationListener.context.getObject(GetUtilityMeterByMonthImpl.class);
            this.submitUtilityMeter = ApplicationContextInitializationListener.context.getObject(SubmitUtilityMeterImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity userEntity = (UserEntity) req.getSession().getAttribute("user");
        Role userRole = userEntity.getRole();
        Long userEntityId = userEntity.getId();

        String pathInfo = req.getPathInfo();
        List<UtilityMeterModel> meters = null;

        if (pathInfo == null) {
            meters = switch (userRole) {
                case ADMIN -> getAllUtilityMeter.execute();
                case USER -> getAllUtilityMeterById.execute(userEntityId);
            };
        } else if (pathInfo.equals("/last")) {
            meters = getLastUtilityMeter.execute(userEntityId);
        } else if (pathInfo.startsWith("/month/")) {
            String[] pathSegments = pathInfo.split("/");
            if (pathSegments.length == 3 && pathSegments[2].matches("\\d+")) {
                Integer numberMonth = Integer.parseInt(pathSegments[2]);
                meters = getUtilityMeterByMonth.execute(numberMonth, userEntityId);
            }
        }

        if (meters == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            byte[] bytes = Json.objectMapper.writeValueAsBytes(meters);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(bytes);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity userEntity = (UserEntity) req.getSession().getAttribute("user");
        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        Map<String, Double> utilityMeters = Json.objectMapper.readValue(
                jsonBody.toString(), new TypeReference<>() {
                });
        log(utilityMeters.toString());

        try {
            submitUtilityMeter.execute(utilityMeters, userEntity.getId());
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NotValidMeterTypeException e) {
            resp.getWriter().println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (MonthlySubmitLimitException e) {
            resp.getWriter().println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
