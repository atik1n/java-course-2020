package me.salieri.Java_Course.security;

import io.jsonwebtoken.ExpiredJwtException;
import me.salieri.Java_Course.entity.User;
import me.salieri.Java_Course.service.UserService;
import me.salieri.Java_Course.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  @Autowired
  private UserService userService;
  @Autowired
  private AuthUtils authUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader("Authorization");
    String username = null;
    String jwtToken = null;
    String error = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Nullpo! ")) {
      jwtToken = requestTokenHeader.substring(8);
      try {
        username = authUtils.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        error = "Invalid token.";
      } catch (ExpiredJwtException e) {
        error = "Token has been expired.";
      }
    } else {
      error = "Invalid header.";
    }

    try {
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        User user = userService.loadUserByUsername(username);

        if (authUtils.validateToken(jwtToken, user)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              user, null, user.getAuthorities()
          );
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    } catch (UsernameNotFoundException e) {
      error = "No such user.";
    }

    chain.doFilter(request, response);
  }
}
