package com.trilogyed.adminservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);

    }

    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/customers/customer", "/customers/customer/*", "/products/product", "/products/products/*",
                        "/inventory/inventory", "/inventory/inventory/*", "/invoice/invoice", "/invoice/invoice/*", "/levelUp/levelUp", "/levelUp/levelUp/*")
                .hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.PUT, "/customers/customer/*", "/products/product/*", "/inventory/inventory/*",
                        "/levelUp/levelUp/*", "/invoice/invoice").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.POST, "/customers/customer").hasAuthority("ROLE_LEAD")
                .mvcMatchers(HttpMethod.POST, "/products/product", "/inventory/inventory",
                        "/invoice/invoice", "/levelUp/levelUp").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.DELETE, "/customers/customer/*", "/products/products/*",
                        "/inventory/inventory/*", "/invoice/invoice/*", "/levelUp/levelUp/*").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/allDone")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }
}
