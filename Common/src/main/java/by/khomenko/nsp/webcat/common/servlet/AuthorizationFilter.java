package by.khomenko.nsp.webcat.common.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(description = "Check user's role",
        urlPatterns = {"/exampleOne.html", "/exampleTwo.html"})

public class AuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


    }


}
