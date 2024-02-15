package ru.ylab.adapters.in.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Создан: 15.02.2024.
 *
 * @author Pesternikov Danil
 */
public abstract class ServletMocks {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    BufferedReader bufferedReader;

    @Mock
    PrintWriter printWriter;

}
