package by.khomenko.nsp.webcat.client.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(description = "Redirect to login if not authorised",
        urlPatterns = {"/profile.html"})

//TODO Move this filter to client module.

public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getSession().getAttribute("customerId") == null) {
            String s = request.getRequestURI() + request.getQueryString();
            request.getSession().setAttribute("requestedURL", s);
            response.sendRedirect("signin.html");
        } else {
            chain.doFilter(request, response);
        }

    }

}
