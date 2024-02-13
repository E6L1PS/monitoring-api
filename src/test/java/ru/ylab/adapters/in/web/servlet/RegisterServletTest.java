package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
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

import java.io.*;

import static org.mockito.Mockito.*;

/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class RegisterServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RegisterUser registerUser;

    @InjectMocks
    RegisterServlet registerServlet;

    static BufferedReader reader;

    static PrintWriter writer;

    @BeforeAll
    static void setup() throws IOException {
        String jsonBody = "{\"username\":\"test\",\"password\":\"password\"}";
        reader = new BufferedReader(new StringReader(jsonBody));
        writer = new PrintWriter(new StringWriter());
    }

    @Test
    @DisplayName("Регистрация пользователя - статус 201")
    void testDoPost_201() throws ServletException, IOException {
        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);

        registerServlet.doPost(request, response);

        verify(registerUser).execute(any());
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

//    @Test
//    @DisplayName("Регистрация пользователя с невалидными данными - статус 400")
//    void testDoPost_NotValid_400() throws ServletException, IOException {
//        when(request.getReader()).thenReturn(reader);
//        when(response.getWriter()).thenReturn(writer);
//        when(registerUser.execute(any(User.class)))
//                .thenThrow(new NotValidUsernameOrPasswordException("Username already exists"));
//
//
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//       // verify(response.getWriter()).println("Username already exists");
//    }

    @Test
    @DisplayName("Регистрация пользователя с уже существующим username - статус 409")
    void testDoPost_UsernameAlreadyExists_409() throws ServletException, IOException {
        when(request.getReader()).thenReturn(reader);
        when(registerUser.execute(any(User.class))).thenThrow(new UsernameAlreadyExistsException("Username already exists"));

        registerServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
        //verify(response.getWriter()).println("Username already exists");
    }

}