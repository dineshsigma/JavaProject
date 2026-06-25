package com.example.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import com.example.demo.config.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

           return path.contains("/auth");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String headerValue = request.getHeader(HEADER_NAME);
        
        System.out.println("Incoming Request: " + request.getRequestURI());
        System.out.println("Authorization Header: " + headerValue);
//        System.out.println("validation:" +  !headerValue.equals(VALID_TOKEN));
        
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
        	String token = headerValue.substring(7);
        	if (jwtUtil.validateToken(token)) {
        		String username = jwtUtil.getUsername(token);

        		UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        		SecurityContextHolder.getContext().setAuthentication(auth);
        		
        	}else {
        		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(INVALID_TOKEN);
                return;

        	}
        }else {
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