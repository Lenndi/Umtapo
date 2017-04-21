package org.lenndi.umtapo.configuration.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security
            .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;

public final class TokenAuthenticationService {

  static final long EXPIRATIONTIME = 864_000_000; // 10 days
  static final String SECRET = "ThisIsASecret";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  private TokenAuthenticationService() {
  }

  static void addAuthentication(HttpServletResponse res, String username) {
    String jwt = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();

    try {
      res.getWriter().write("{ \"" + HEADER_STRING + "\" : \"" + TOKEN_PREFIX + " " + jwt + "\" }");
      res.getWriter().flush();
      res.getWriter().close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      // parse the token.
      String user = Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
          .getBody()
          .getSubject();

      if (user != null) {
          new UsernamePasswordAuthenticationToken(user, null, emptyList());
      } else {
          return null;
      }
    }

    return null;
  }
}
