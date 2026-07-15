package com.example.demo.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.commons.utlity.CommonConstants;
import com.example.demo.config.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HeaderAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	// ✅ Constructor Injection
	public HeaderAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	private static final String HEADER_NAME = "Authorization";
//    private static final String VALID_TOKEN = "dinesh"; // ✅ your custom value

	private static final String MISSING_TOKEN = "{\"message\":\"Unauthorized - Missing Token\"}";
	private static final String INVALID_TOKEN = "{\"message\":\"Unauthorized - Invalid Token\"}";

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		System.out.println("Checking path: " + path);

		System.out.println("Checking path validations: " + path.startsWith("/api/auth"));

		return path.contains("/auth") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")
				|| path.equals("/swagger-ui.html");

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String headerValue = request.getHeader(HEADER_NAME);

		System.out.println("Incoming Request: " + request.getRequestURI());
		System.out.println("Authorization Header: " + headerValue);

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String correlationId = httpRequest.getHeader("X-Correlation-Id");

		if (correlationId == null || correlationId.isBlank()) {
			correlationId = UUID.randomUUID().toString();
		}

		System.out.println("correlationId" + correlationId);

		System.out.println("CONTENT TYPE : " + request.getContentType());

		MDC.put(CommonConstants.CORRELATIONID, correlationId);
		httpRequest.setAttribute(CommonConstants.CORRELATIONID, correlationId);
//        System.out.println("validation:" +  !headerValue.equals(VALID_TOKEN));

		if (headerValue != null && headerValue.startsWith("Bearer ")) {
			String token = headerValue.substring(7);
			if (jwtUtil.validateToken(token)) {
				String username = jwtUtil.getUsername(token);

				Claims claims = jwtUtil.getClaimsFromToken(token);

				String roleName = claims.get("role", String.class);

				MDC.put(CommonConstants.ROLENAME, roleName);

				List<GrantedAuthority> authorities = Collections
						.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName));
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);

			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write(INVALID_TOKEN);
				return;

			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(MISSING_TOKEN);
			return;

		}

		// ✅ Validate header
//        if (headerValue == null || !headerValue.equals(VALID_TOKEN)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("{\"error\":\"Unauthorized - Invalid Token\"}");
//            return;
//        }

		// ✅ Continue request
		filterChain.doFilter(request, response);
	}
}