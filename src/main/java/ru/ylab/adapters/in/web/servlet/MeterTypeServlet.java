package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.adapters.util.Json;
import ru.ylab.adapters.in.web.dto.MeterTypeModel;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.service.AddNewMeterTypeImpl;
import ru.ylab.application.service.GetUtilityMeterTypesImpl;
import ru.ylab.domain.model.Role;

import java.io.IOException;
import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@WebServlet("/type")
public class MeterTypeServlet extends HttpServlet {

    private final GetUtilityMeterTypes getUtilityMeterTypes;

    private final AddNewMeterType addNewMeterType;

    {
        try {
            getUtilityMeterTypes = ApplicationContextInitializationListener.context.getObject(GetUtilityMeterTypesImpl.class);
            addNewMeterType = ApplicationContextInitializationListener.context.getObject(AddNewMeterTypeImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MeterTypeModel> meterTypes = MeterTypeMapper.INSTANCE.metersToListMeterTypeModel(getUtilityMeterTypes.execute());
        byte[] bytes = Json.objectMapper.writeValueAsBytes(meterTypes);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getOutputStream().write(bytes);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity userEntity = (UserEntity) req.getSession().getAttribute("user");
        Role userRole = userEntity.getRole();
        String type = req.getParameter("type");
        if (Role.ADMIN.equals(userRole)) {
            if (type != null) {
                addNewMeterType.execute(type);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
