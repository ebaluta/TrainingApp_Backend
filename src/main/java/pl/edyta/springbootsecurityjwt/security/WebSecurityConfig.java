package pl.edyta.springbootsecurityjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.edyta.springbootsecurityjwt.security.jwt.AuthEntryPointJwt;
import pl.edyta.springbootsecurityjwt.security.jwt.AuthTokenFilter;
import pl.edyta.springbootsecurityjwt.security.services.UserDetailsServiceImpl;

/**
 * @EnableWebSecurity - allows Spring to find and automatically apply the class to the global Web Security
 * @EnableGlobalMEthodSecurity - provides AOP security on methods
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    /**
     * Spring will load User details to perform authentication and authorization, so it has UserDetailsService interface
     * that we need to implement
     */
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * This method tells Spring Security how we configure CORS and CSRD, when we want to require all users to be
     * authenticated or not,which filter (AuthTokenFilter) and when we want it work (filter before UsernamePasswordAutFilter),
     * whick Exception Handler is chosen (AuthEntryPointJwt)
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/trainings/**").permitAll()
                .antMatchers("/api/plan/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
