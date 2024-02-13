package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ru.ylab.adapters.in.web.dto.RegisterModel;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.adapters.util.Json;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.service.RegisterUserImpl;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@AllArgsConstructor
@Loggable
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final RegisterUser registerUser;

    public RegisterServlet() {
        try {
            this.registerUser = ApplicationContextInitializationListener.context.getObject(RegisterUserImpl.class);
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
        RegisterModel registerModel = Json.objectMapper.readValue(jsonBody.toString(), RegisterModel.class);
        User user = UserMapper.INSTANCE.toUser(registerModel);

        try {
            var id = registerUser.execute(user);
            resp.getWriter().println(id);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NotValidUsernameOrPasswordException e) {
            resp.getWriter().println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (UsernameAlreadyExistsException e) {
            resp.getWriter().println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
