package ru.ylab.adapters.in.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.aspect.Loggable;
import ru.ylab.domain.model.Role;

import java.io.IOException;

/**
 * Создан: 10.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@WebServlet("/test")
public class TestServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    public TestServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        byte[] bytes = objectMapper.writeValueAsBytes(Role.USER);
        resp.getOutputStream().write(bytes);
    }
}
