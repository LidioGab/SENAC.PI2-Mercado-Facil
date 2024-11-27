package com.gereciador.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final long TIMEOUT = 30000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null) {
            long loginTime = (Long) session.getAttribute("loginTime");
            long currentTime = System.currentTimeMillis();
            if (currentTime - loginTime > TIMEOUT) {
                session.invalidate();
                response.sendRedirect("index.html?timeout=true");
            } else {
                session.setAttribute("loginTime", currentTime); // Reset timer
                request.getRequestDispatcher("/templates/dashboard.html").forward(request, response);
            }
        } else {
            response.sendRedirect("index.html");
        }
    }
}
