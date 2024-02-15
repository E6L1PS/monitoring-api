
package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.application.in.GetAuditInfo;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class AuditServletTest extends ServletMocks {

    @Mock
    GetAuditInfo getAuditInfo;

    @InjectMocks
    AuditServlet auditServlet;

    @Test
    @DisplayName("Получение всех аудитов - статус 200")
    void testDoGet_200() throws Exception {
        when(response.getWriter()).thenReturn(printWriter);
        when(getAuditInfo.execute()).thenReturn(Collections.emptyList());

        auditServlet.doGet(request, response);

        verify(printWriter).write("[]");
        verify(getAuditInfo).execute();
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
