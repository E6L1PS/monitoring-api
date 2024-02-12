package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.ylab.adapters.in.web.Json;
import ru.ylab.adapters.in.web.listener.MyServletContextListener;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.IncorrectPasswordException;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.in.LoginUser;
import ru.ylab.application.model.LoginModel;
import ru.ylab.application.service.LoginUserImpl;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@WebServlet("/login")
public class AuthServlet extends HttpServlet {

    private final LoginUser loginUser;

    {
        try {
            loginUser = MyServletContextListener.context.getObject(LoginUserImpl.class);
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
