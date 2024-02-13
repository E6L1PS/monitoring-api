package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.domain.model.Role;

import static org.mockito.Mockito.*;

/**
 * Создан: 13.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class MeterTypeServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    GetUtilityMeterTypes getUtilityMeterTypes;

    @Mock
    AddNewMeterType addNewMeterType;
    @Mock
    HttpSession session;

    @InjectMocks
    MeterTypeServlet meterTypeServlet;

//    @Test
//    void testDoGet_200() throws Exception {
//    }
//
//    @Test
//    void testDoPost_200() throws Exception {
//        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute("user"))
//                .thenReturn(UserEntity.builder().role(Role.ADMIN).build());
//
//        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
//
//    }
//
//    @Test
//    void testDoPost_403() throws Exception {
//        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute("user"))
//                .thenReturn(UserEntity.builder().role(Role.USER).build());
//
//        verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);
//    }
}