package ru.ylab.adapters.in.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import ru.ylab.adapters.in.web.listener.MyServletContextListener;
import ru.ylab.application.in.GetAllUtilityMeter;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.service.GetAllUtilityMeterImpl;
import ru.ylab.aspect.Loggable;

import java.io.IOException;
import java.util.List;

/**
 * Создан: 10.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@WebServlet("/meter")
@NoArgsConstructor
public class MeterServlet extends HttpServlet {

    private final GetAllUtilityMeter getAllUtilityMeter;

    {
        try {
            getAllUtilityMeter = MyServletContextListener.context.getObject(GetAllUtilityMeterImpl.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UtilityMeterModel> meters = getAllUtilityMeter.execute();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        byte[] bytes = objectMapper.writeValueAsBytes(meters);
        resp.getOutputStream().write(bytes);
    }

}
