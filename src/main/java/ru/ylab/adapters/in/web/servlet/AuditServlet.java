package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.adapters.in.web.dto.AuditModel;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.util.Json;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.application.service.GetAuditInfoImpl;
import ru.ylab.aspect.annotation.Loggable;

import java.io.IOException;
import java.util.List;

/**
 * Создан: 11.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@WebServlet("/audit")
public class AuditServlet extends HttpServlet {

    private final GetAuditInfo getAuditInfo;

    {
        try {
            getAuditInfo = ApplicationContextInitializationListener.context.getObject(GetAuditInfoImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AuditModel> audits = getAuditInfo.execute();
        String json = Json.objectMapper.writeValueAsString(audits);
        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}