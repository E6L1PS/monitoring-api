package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.domain.model.MeterType;
import ru.ylab.domain.model.Role;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class MeterTypeServletTest extends ServletMocks {

    @Mock
    GetUtilityMeterTypes getUtilityMeterTypes;

    @Mock
    AddNewMeterType addNewMeterType;

    @InjectMocks
    MeterTypeServlet meterTypeServlet;

    @Nested
    @DisplayName("Тесты get запросов")
    class GetTests {

        @BeforeEach
        void setUp() throws IOException {
            when(response.getWriter()).thenReturn(printWriter);
        }

        @Test
        @DisplayName("Получение всех типов счетчика - статус 200")
        void testDoGet_200() throws Exception {
            when(getUtilityMeterTypes.execute()).thenReturn(Collections.emptyList());

            meterTypeServlet.doGet(request, response);

            verify(getUtilityMeterTypes).execute();
            verify(printWriter).write("[]");
            verify(response).setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Nested
    @DisplayName("Тесты post запросов")
    class PostTests {

        @BeforeEach
        void setUp() throws IOException {
            when(request.getSession()).thenReturn(session);
            when(request.getReader()).thenReturn(bufferedReader);
            String jsonBody = "{\"name\":\"test\"}";
            when(bufferedReader.readLine()).thenReturn(jsonBody).thenReturn(null);
        }

        @Test
        @DisplayName("Создание нового типа счетчика - статус 201")
        void testDoPost_201() throws Exception {
            when(session.getAttribute("user"))
                    .thenReturn(UserEntity.builder().role(Role.ADMIN).build());

            meterTypeServlet.doPost(request, response);

            verify(addNewMeterType).execute(any(MeterType.class));
            verify(response).setStatus(HttpServletResponse.SC_CREATED);
        }

        @Test
        @DisplayName("Не пройдена авторизация при попытке создать новый счетчик - статус 201")
        void testDoPost_403() throws Exception {
            when(session.getAttribute("user"))
                    .thenReturn(UserEntity.builder().role(Role.USER).build());

            meterTypeServlet.doPost(request, response);

            verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}