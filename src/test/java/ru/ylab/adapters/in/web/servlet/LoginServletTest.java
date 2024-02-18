package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.IncorrectPasswordException;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.in.LoginUser;
import ru.ylab.domain.model.User;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class LoginServletTest extends ServletMocks {

    @Mock
    LoginUser loginUser;

    @InjectMocks
    LoginServlet loginServlet;

    @BeforeEach
    void setUp() throws IOException {
        String jsonBody = "{\"username\":\"test\",\"password\":\"password\"}";
        when(request.getReader()).thenReturn(bufferedReader);
        when(response.getWriter()).thenReturn(printWriter);
        when(bufferedReader.readLine()).thenReturn(jsonBody).thenReturn(null);
    }

    @Test
    @DisplayName("Аутентификация пользователя - статус 200")
    void testDoPost_200() throws Exception {
        UserEntity userEntity = UserEntity.builder().build();
        when(loginUser.execute(any(User.class))).thenReturn(userEntity);
        when(request.getSession(true)).thenReturn(session);

        loginServlet.doPost(request, response);

        verify(printWriter).println("Success!");
        verify(session).setAttribute(eq("user"), eq(userEntity));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("Аутентификация пользователя - статус 400")
    void testDoPostUserNotFound_400() throws IOException, ServletException {
        when(loginUser.execute(any(User.class)))
                .thenThrow(new UserNotFoundException("UserNotFoundException"));

        loginServlet.doPost(request, response);

        verify(printWriter).println("UserNotFoundException");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Аутентификация пользователя - статус 400")
    void testDoPostIncorrectPassword_400() throws IOException, ServletException {
        when(loginUser.execute(any(User.class)))
                .thenThrow(new IncorrectPasswordException("IncorrectPasswordException"));

        loginServlet.doPost(request, response);

        verify(printWriter).println("IncorrectPasswordException");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}