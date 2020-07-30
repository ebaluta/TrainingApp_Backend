package pl.edyta.springbootsecurityjwt.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.edyta.springbootsecurityjwt.security.services.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * THis class makes a single execution for each request to our API. It provides a
 * doFilterInternal() method that we will implement parsing and validating JWT,
 * loading User details( using UserDetailService), checking Authorizarion( using
 * UsernamePasswordAuthenticationToken)
 * so AuthTokenFilter is a filter that executes once per request
 */

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwt = parseJwt(httpServletRequest);
            //if the request has jwt - we can validate it, parse username from it
            if(jwt!=null && jwtUtils.validateJwtToken(jwt)){
                String username=jwtUtils.getUserNameForJwtToken(jwt);
               // logger.trace(username);

                //from username we get UserDetails
                UserDetails userDetails= userDetailService.loadUserByUsername(username);
                //we create Authentication object
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                   userDetails,
                   null,
                   userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                //we set the current UserDetails in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }catch (Exception e){
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    /**
     * This method get JWT from the Authorization header (by removing Bearer prefix)
     * @param request
     * @return
     */
    private String parseJwt(HttpServletRequest request){
        String headerAuth=request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7,headerAuth.length());
        }

        return null;
    }
}
