package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.in.GetAllUtilityMeter;
import ru.ylab.application.in.GetAllUtilityMeterById;
import ru.ylab.application.in.GetLastUtilityMeter;
import ru.ylab.application.in.GetUtilityMeterByMonth;
import ru.ylab.application.service.SubmitUtilityMeterImpl;
import ru.ylab.domain.model.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class MeterServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    UserEntity userEntity;

    @Mock
    BufferedReader bufferedReader;

    @Mock
    PrintWriter printWriter;

    @Mock
    GetAllUtilityMeter getAllUtilityMeter;

    @Mock
    GetAllUtilityMeterById getAllUtilityMeterById;

    @Mock
    GetLastUtilityMeter getLastUtilityMeter;

    @Mock
    GetUtilityMeterByMonth getUtilityMeterByMonth;

    @Mock
    SubmitUtilityMeterImpl submitUtilityMeter;

    @InjectMocks
    MeterServlet meterServlet;

    @Nested
    @DisplayName("Тесты get запросов")
    class GetTests {

        @BeforeEach
        void setUp() throws IOException {
            when(request.getSession()).thenReturn(session);
            when(session.getAttribute("user")).thenReturn(userEntity);
            when(response.getWriter()).thenReturn(printWriter);
        }

        @Test
        @DisplayName("Получение всех показаний с ролью ADMIN - статус 200")
        void testDoGetForAdmin_200() throws ServletException, IOException {
            when(request.getPathInfo()).thenReturn(null);
            when(userEntity.getRole()).thenReturn(Role.ADMIN);
            when(getAllUtilityMeter.execute()).thenReturn(Collections.emptyList());

            meterServlet.doGet(request, response);

            verify(getAllUtilityMeter).execute();
            verify(printWriter).write(anyString());
            verify(response).setStatus(HttpServletResponse.SC_OK);
        }

        @Test
        @DisplayName("Получение всех показаний с ролью USER - статус 200")
        void testDoGetForUser_200() throws ServletException, IOException {
            when(request.getPathInfo()).thenReturn(null);
            when(userEntity.getRole()).thenReturn(Role.USER);
            when(getAllUtilityMeterById.execute(anyLong())).thenReturn(Collections.emptyList());

            meterServlet.doGet(request, response);

            verify(getAllUtilityMeterById).execute(anyLong());
            verify(response).setStatus(HttpServletResponse.SC_OK);
        }

        @Test
        @DisplayName("Получение последних показаний по id - статус 200")
        void testDoGetForLast_200() throws ServletException, IOException {
            when(request.getPathInfo()).thenReturn("/last");
            when(userEntity.getRole()).thenReturn(Role.USER);
            when(getLastUtilityMeter.execute(anyLong())).thenReturn(Collections.emptyList());

            meterServlet.doGet(request, response);

            verify(getLastUtilityMeter).execute(anyLong());
            verify(response).setStatus(HttpServletResponse.SC_OK);
        }

        @Test
        @DisplayName("Получение показаний по id и месяцу - статус 200")
        void testDoGetForMonth_200() throws ServletException, IOException {
            when(request.getPathInfo()).thenReturn("/month/3");
            when(userEntity.getRole()).thenReturn(Role.USER);
            when(getUtilityMeterByMonth.execute(eq(3), anyLong())).thenReturn(Collections.emptyList());

            meterServlet.doGet(request, response);

            verify(getUtilityMeterByMonth).execute(eq(3), anyLong());
            verify(response).setStatus(HttpServletResponse.SC_OK);
        }

        @Test
        @DisplayName("Неверный эндпоинт - статус 404")
        void testDoGetNotFound_404() throws ServletException, IOException {
            when(request.getPathInfo()).thenReturn("/invalidPath");
            when(userEntity.getRole()).thenReturn(Role.USER);

            meterServlet.doGet(request, response);

            verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("Тесты post запросов")
    class PostTests {

        @BeforeEach
        void setUp() throws IOException {
            when(request.getSession()).thenReturn(session);
            when(session.getAttribute("user")).thenReturn(userEntity);
            when(response.getWriter()).thenReturn(printWriter);
            when(request.getReader()).thenReturn(bufferedReader);
            when(bufferedReader.readLine())
                    .thenReturn("{\"key\": \"31\"}")
                    .thenReturn(null);
            when(userEntity.getId()).thenReturn(1L);
        }

        @Test
        @DisplayName("Валидные показания - статус 201")
        void testDoPostValidData_201() throws ServletException, IOException {
            when(submitUtilityMeter.execute(anyMap(), anyLong())).thenReturn(Collections.emptyList());

            meterServlet.doPost(request, response);

            verify(printWriter).write("[]");
            verify(submitUtilityMeter).execute(anyMap(), anyLong());
            verify(response).setStatus(HttpServletResponse.SC_CREATED);
        }

        @Test
        @DisplayName("Невалидные показания - статус 400")
        void testDoPostInvalidMeterTypeException_400() throws ServletException, IOException, NotValidMeterTypeException {
            when(submitUtilityMeter.execute(anyMap(), anyLong()))
                    .thenThrow(new NotValidMeterTypeException("Invalid meter type"));

            meterServlet.doPost(request, response);

            verify(printWriter).println("Invalid meter type");
            verify(submitUtilityMeter).execute(anyMap(), anyLong());
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        @Test
        @DisplayName("Превышен месячный лимит отправки - статус 403")
        void testDoPostMonthlySubmitLimitException_403() throws ServletException, IOException, MonthlySubmitLimitException {
            when(submitUtilityMeter.execute(anyMap(), anyLong()))
                    .thenThrow((new MonthlySubmitLimitException("Monthly submit limit exceeded")));

            meterServlet.doPost(request, response);

            verify(printWriter).println("Monthly submit limit exceeded");
            verify(submitUtilityMeter).execute(anyMap(), anyLong());
            verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}