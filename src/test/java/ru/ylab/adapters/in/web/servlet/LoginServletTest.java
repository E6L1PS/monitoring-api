package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
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

import java.io.*;

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
class LoginServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    LoginUser loginUser;

    @Mock
    HttpSession session;

    @InjectMocks
    LoginServlet loginServlet;

    static BufferedReader reader;

    static PrintWriter writer;

    @BeforeAll
    static void setup() throws IOException {
        String jsonBody = "{\"username\":\"test\",\"password\":\"password\"}";
        reader = new BufferedReader(new StringReader(jsonBody));
        writer = new PrintWriter(new StringWriter());
    }

    @Test
    @DisplayName("Аутентификация пользователя - статус 200")
    void testDoPost_200() throws Exception {
        UserEntity userEntity = UserEntity.builder().build();
        when(request.getReader()).thenReturn(reader);
        when(loginUser.execute(any(User.class))).thenReturn(userEntity);
        when(request.getSession(true)).thenReturn(session);

        loginServlet.doPost(request, response);

        verify(session).setAttribute(eq("user"), eq(userEntity));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("Аутентификация пользователя - статус 400")
    void testDoPostUserNotFound() throws IOException, ServletException {
        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(loginUser.execute(any(User.class))).thenThrow(new UserNotFoundException(""));

        loginServlet.doPost(request, response);

        verify(response).getWriter();
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Аутентификация пользователя - статус 400")
    void testDoPostIncorrectPassword() throws IOException, ServletException {
        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(loginUser.execute(any(User.class))).thenThrow(new IncorrectPasswordException(""));

        loginServlet.doPost(request, response);

        verify(response).getWriter();
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}