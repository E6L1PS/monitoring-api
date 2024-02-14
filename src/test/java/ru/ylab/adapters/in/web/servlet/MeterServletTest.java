package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.in.*;
import ru.ylab.domain.model.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
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
    GetAllUtilityMeter getAllUtilityMeter;

    @Mock
    GetAllUtilityMeterById getAllUtilityMeterById;

    @Mock
    GetLastUtilityMeter getLastUtilityMeter;

    @Mock
    GetUtilityMeterByMonth getUtilityMeterByMonth;

    @Mock
    SubmitUtilityMeter submitUtilityMeter;
    @Mock
    PrintWriter printWriter;

    @InjectMocks
    MeterServlet meterServlet;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(userEntity);
    }

    @Test
    @DisplayName("Получение всех показаний с ролью ADMIN - статус 200")
    void testDoGetForAdmin_200() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(null);
        when(userEntity.getRole()).thenReturn(Role.ADMIN);
        //when(getAllUtilityMeter.execute()).thenReturn(Collections.emptyList());
        when(response.getWriter()).thenReturn(printWriter);

        meterServlet.doGet(request, response);

        //verify(getAllUtilityMeter).execute();
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(printWriter).write(anyString());
    }

    @Test
    @DisplayName("Получение всех показаний с ролью USER - статус 200")
    void testDoGetForUser_200() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(null);
        when(userEntity.getRole()).thenReturn(Role.USER);
        lenient().when(getAllUtilityMeterById.execute(anyLong())).thenReturn(Collections.emptyList());
        when(response.getWriter()).thenReturn(printWriter);

        meterServlet.doGet(request, response);

        // verify(getAllUtilityMeter).execute();
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("Получение последних показаний по id - статус 200")
    void testDoGetForLast_200() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/last");
        when(userEntity.getRole()).thenReturn(Role.USER);
        lenient().when(getLastUtilityMeter.execute(anyLong())).thenReturn(Collections.emptyList());
        when(response.getWriter()).thenReturn(printWriter);

        meterServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("Получение показаний по id и месяцу - статус 200")
    void testDoGetForMonth_200() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/month/3");
        when(userEntity.getRole()).thenReturn(Role.USER);
        lenient().when(getUtilityMeterByMonth.execute(eq(3), anyLong())).thenReturn(Collections.emptyList());
        when(response.getWriter()).thenReturn(printWriter);

        meterServlet.doGet(request, response);

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

    @Test
    @DisplayName("Валидные показания - статус 201")
    void testDoPostValidData_201() throws ServletException, IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"key\": 1.0}")));
        when(userEntity.getId()).thenReturn(1L);
        when(response.getWriter()).thenReturn(printWriter);

        meterServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

//    @Test
//    @DisplayName("Невалидные показания - статус 400")
//    void testDoPostInvalidMeterTypeException_400() throws ServletException, IOException, NotValidMeterTypeException {
//        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"key\": \"31\"}")));
//        when(userEntity.getId()).thenReturn(1L);
//        when(submitUtilityMeter.execute(anyMap(), anyLong())).thenThrow((new NotValidMeterTypeException("Invalid meter type")));
//        when(response.getWriter()).thenReturn(printWriter);
//
//        meterServlet.doPost(request, response);
//
//        verify(printWriter).println("Invalid meter type");
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("Неверный эндпоинт - статус 403")
//    void testDoPostMonthlySubmitLimitException_403() throws ServletException, IOException, MonthlySubmitLimitException {
//        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"key\": 1.0}")));
//        when(userEntity.getId()).thenReturn(1L);
//
//        PrintWriter mockPrintWriter = mock(PrintWriter.class);
//        when(submitUtilityMeter.execute(anyMap(), anyLong())).thenThrow((new MonthlySubmitLimitException("Monthly submit limit exceeded")));
//
//        meterServlet.doPost(request, response);
//
//        verify(submitUtilityMeter).execute(anyMap(), eq(1L));
//        verify(mockPrintWriter).write(anyString());
//        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
//    }
}