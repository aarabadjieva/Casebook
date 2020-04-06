package web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"", "/register", "/login", "/", "/faces/views/index.xhtml", "/views/index.xhtml"})
public class AuthUserFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String isLogged = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("username");
        if (isLogged!=null){
            ((HttpServletResponse) servletResponse).sendRedirect("/home");
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
