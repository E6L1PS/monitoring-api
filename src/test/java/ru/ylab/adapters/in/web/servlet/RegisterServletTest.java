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
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.domain.model.User;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class RegisterServletTest extends ServletMocks {

    @Mock
    RegisterUser registerUser;

    @InjectMocks
    RegisterServlet registerServlet;

    @BeforeEach
    void setUp() throws IOException {
        String jsonBody = "{\"username\":\"test\",\"password\":\"password\"}";
        when(request.getReader()).thenReturn(bufferedReader);
        when(response.getWriter()).thenReturn(printWriter);
        when(bufferedReader.readLine())
                .thenReturn(jsonBody)
                .thenReturn(null);
    }

    @Test
    @DisplayName("Регистрация пользователя - статус 201")
    void testDoPost_201() throws ServletException, IOException {
        when(registerUser.execute(any(User.class))).thenReturn(231L);

        registerServlet.doPost(request, response);

        verify(registerUser).execute(any(User.class));
        verify(printWriter).println("231");
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @DisplayName("Регистрация пользователя с невалидными данными - статус 400")
    void testDoPost_NotValid_400() throws ServletException, IOException {
        when(registerUser.execute(any(User.class)))
                .thenThrow(new NotValidUsernameOrPasswordException("Username already exists"));

        registerServlet.doPost(request, response);

        verify(registerUser).execute(any(User.class));
        verify(printWriter).println("Username already exists");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Регистрация пользователя с уже существующим username - статус 409")
    void testDoPost_UsernameAlreadyExists_409() throws ServletException, IOException {
        when(registerUser.execute(any(User.class)))
                .thenThrow(new UsernameAlreadyExistsException("Username already exists"));

        registerServlet.doPost(request, response);

        verify(registerUser).execute(any(User.class));
        verify(printWriter).println("Username already exists");
        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
    }

}