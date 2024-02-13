/*
package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.in.web.dto.AuditModel;
import ru.ylab.adapters.in.web.listener.ApplicationContextInitializationListener;
import ru.ylab.application.in.GetAuditInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

*/
/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 *//*

@ExtendWith(MockitoExtension.class)
class AuditServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    GetAuditInfo getAuditInfo;

    @Mock
    ApplicationContextInitializationListener applicationContextInitializationListener;

    @InjectMocks
    AuditServlet auditServlet;


    @Test
    public void testDoGet_PositiveCase() throws ServletException, IOException {
        var localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        // Создание mock списка AuditModel
        List<AuditModel> audits = new ArrayList<>();
        audits.add(new AuditModel(6L, 1L, "Авторизация выполнена!", localDateTime));
        audits.add(new AuditModel(7L, 1L, "Авторизация выполнена!", localDateTime));

        // Настройка поведения mock объектов и вызов метода doGet
        when(getAuditInfo.execute()).thenReturn(audits);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        auditServlet.doGet(request, response);
        writer.flush();

        String expectedJson = """
                [
                    {
                        "id": 6,
                        "userId": 1,
                        "info": "Авторизация выполнена!",
                        "dateTime": "%s"
                    },
                    {
                        "id": 7,
                        "userId": 1,
                        "info": "Авторизация выполнена!",
                        "dateTime": "%s"
                    }
                ]
                """.formatted(formattedDateTime, formattedDateTime);
        // Проверка, что полученный JSON соответствует ожидаемому
//        String expectedJson = "[{\"id\":\"6\",\"userId\":\"1\"}," +
//                              "{\"user\":\"User 2\",\"action\":\"Action 2\"}]";
        assertThat(stringWriter.toString()).isEqualTo(expectedJson);

        // Проверка, что статус ответа установлен на SC_OK
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}*/
