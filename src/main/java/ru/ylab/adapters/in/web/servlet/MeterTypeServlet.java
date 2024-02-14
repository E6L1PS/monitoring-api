package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.adapters.util.Json;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.service.AddNewMeterTypeImpl;
import ru.ylab.application.service.GetUtilityMeterTypesImpl;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.MeterType;
import ru.ylab.domain.model.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@AllArgsConstructor
@Loggable
@WebServlet("/type")
public class MeterTypeServlet extends HttpServlet {

    private final GetUtilityMeterTypes getUtilityMeterTypes;

    private final AddNewMeterType addNewMeterType;

    public MeterTypeServlet() {
        try {
            this.getUtilityMeterTypes = ApplicationContextInitializationListener.context.getObject(GetUtilityMeterTypesImpl.class);
            this.addNewMeterType = ApplicationContextInitializationListener.context.getObject(AddNewMeterTypeImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MeterType> meterTypes = getUtilityMeterTypes.execute();
        List<MeterTypeDto> meterTypesDto = MeterTypeMapper.INSTANCE.toListDto(meterTypes);
        byte[] bytes = Json.objectMapper.writeValueAsBytes(meterTypesDto);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getOutputStream().write(bytes);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity userEntity = (UserEntity) req.getSession().getAttribute("user");
        Role userRole = userEntity.getRole();
        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        MeterTypeDto meterTypeDto = Json.objectMapper.readValue(jsonBody.toString(), MeterTypeDto.class);
        MeterType meterType = MeterTypeMapper.INSTANCE.toDomain(meterTypeDto);

        if (Role.ADMIN.equals(userRole)) {
            addNewMeterType.execute(meterType);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
