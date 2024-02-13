package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.application.in.*;

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
    GetAllUtilityMeter getAllUtilityMeter;

    @Mock
    GetAllUtilityMeterById getAllUtilityMeterById;

    @Mock
    GetLastUtilityMeter getLastUtilityMeter;

    @Mock
    GetUtilityMeterByMonth getUtilityMeterByMonth;

    @Mock
    SubmitUtilityMeter submitUtilityMeter;

    @InjectMocks
    MeterServlet meterServlet;

}