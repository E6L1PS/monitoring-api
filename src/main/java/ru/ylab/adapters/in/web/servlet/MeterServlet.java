package ru.ylab.adapters.in.web.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import ru.ylab.adapters.in.web.Json;
import ru.ylab.adapters.in.web.listener.MyServletContextListener;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.in.GetAllUtilityMeter;
import ru.ylab.application.in.GetAllUtilityMeterById;
import ru.ylab.application.in.SubmitUtilityMeter;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.service.GetAllUtilityMeterByIdImpl;
import ru.ylab.application.service.GetAllUtilityMeterImpl;
import ru.ylab.application.service.SubmitUtilityMeterImpl;
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
@Loggable
@WebServlet("/meter")
@NoArgsConstructor
public class MeterServlet extends HttpServlet {

    private final GetAllUtilityMeter getAllUtilityMeter;
    private final GetAllUtilityMeterById getAllUtilityMeterById;
    private final SubmitUtilityMeter submitUtilityMeter;

    {
        try {
            getAllUtilityMeter = MyServletContextListener.context.getObject(GetAllUtilityMeterImpl.class);
            getAllUtilityMeterById = MyServletContextListener.context.getObject(GetAllUtilityMeterByIdImpl.class);
            submitUtilityMeter = MyServletContextListener.context.getObject(SubmitUtilityMeterImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity userEntity = (UserEntity) req.getSession().getAttribute("user");
        Role userRole = userEntity.getRole();
        Long userEntityId = userEntity.getId();
        List<UtilityMeterModel> meters = switch (userRole) {
            case ADMIN -> getAllUtilityMeter.execute();
            case USER -> getAllUtilityMeterById.execute(userEntityId);
        };
        byte[] bytes = Json.objectMapper.writeValueAsBytes(meters);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.getOutputStream().write(bytes);
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
        System.out.println(utilityMeters);

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
