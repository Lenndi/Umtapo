package org.lendi.umtapo.configuration.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.Authentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, "
            + "X-Requested-With, Content-Type, Access-Control-Request-Method, "
            + "Access-Control-Request-Headers, X-XSRF-TOKEN, Authorization");
    response.setHeader("Access-Control-Expose-Headers", "Location");

    if (request.getMethod().equals("OPTIONS")) {
      try {
        res.getWriter().print("OK");
        res.getWriter().flush();
      } catch (final IOException e) {
        e.printStackTrace();
      }
    } else {
      Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) req);

      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(req, res);
    }
  }
}
