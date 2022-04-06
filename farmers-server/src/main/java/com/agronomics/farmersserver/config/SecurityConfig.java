
package com.agronomics.farmersserver.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.agronomics.farmersserver.services.FarmerService;
import com.agronomics.farmersserver.util.JwtFilter;
import com.agronomics.farmersserver.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FarmerService farmersService;
  
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(farmersService);
       // auth.userDetailsService(dealerservice);
        
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean//(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	
        return super.authenticationManagerBean();
    }
    
    /*@Configuration
public class Webcorspolicy implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*").
        allowedOrigins("*").
        allowedMethods("*").
        allowedHeaders("*").
        allowCredentials(true);
    }
}*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
        .authorizeRequests().antMatchers("/v2/api-docs").permitAll()
        .antMatchers("/configuration/ui").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .antMatchers("/configuration/security").permitAll()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/swagger-ui/*").permitAll()
        .antMatchers("/webjars/**").permitAll()
        .antMatchers("/v2/**").permitAll()
        .antMatchers("/farmers/admin/editdet/{fid}").permitAll()
        .antMatchers("/farmers/admin/farmerslist","/farmers/id/{farmerid}","/farmers/deleteid/{farmerid}").permitAll()
        .antMatchers("/farmers/cropslist","/farmers/cropslist/{cropbyid}").permitAll()
        .antMatchers("/farmerregister","/auth","/swagger-ui/","/dealerregister","/").permitAll().anyRequest()
        .authenticated()
        .and().exceptionHandling().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}