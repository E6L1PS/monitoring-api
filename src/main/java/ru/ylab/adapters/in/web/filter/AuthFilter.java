package ru.ylab.adapters.in.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.domain.model.Role;

import java.io.IOException;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        Role currentRoleUser = null;
        if (session != null) {
            UserEntity userEntity = (UserEntity) session.getAttribute("user");
            currentRoleUser = userEntity.getRole();
        }

        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");

        System.out.println(httpRequest.getServletPath());

        switch (httpRequest.getServletPath()) {
            case "/login", "/register" -> {
                chain.doFilter(request, response);
            }
            case "/meter" -> {
                if (session != null) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    System.out.println("Вы не авторизованы!");
                }
            }
            case "/audit" -> {
                if (session != null) {
                    if (Role.ADMIN.equals(currentRoleUser)) {
                        chain.doFilter(request, response);
                    } else {
                        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        System.out.println("У вас нету доступа");
                    }
                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    System.out.println("Вы не авторизованы!");
                }
            }
            default -> {
                httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                System.out.println("Не найдено!");
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
