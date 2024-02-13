package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.ylab.adapters.util.Json;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.IncorrectPasswordException;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.in.LoginUser;
import ru.ylab.adapters.in.web.dto.LoginModel;
import ru.ylab.application.service.LoginUserImpl;
import ru.ylab.aspect.annotation.Loggable;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final LoginUser loginUser;

    {
        try {
            loginUser = ApplicationContextInitializationListener.context.getObject(LoginUserImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        LoginModel loginModel = Json.objectMapper.readValue(jsonBody.toString(), LoginModel.class);

        try {
            UserEntity userEntity = loginUser.execute(loginModel);
            HttpSession session = req.getSession(true);
            session.setAttribute("user", userEntity);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            resp.getWriter().println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
