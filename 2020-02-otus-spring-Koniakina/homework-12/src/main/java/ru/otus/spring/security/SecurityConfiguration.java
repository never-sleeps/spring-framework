package ru.otus.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.security.filter.AnonymousUserDetails;

/**
 *
 *    Роли           Главная страница     Изменение и           Удаление
 *                   со списком книг      добавление книг        книг
 *   ANONYMOUS         +                       -                  -
 *   USER              +                       +                  -
 *   ADMIN             +                       +                  +
 *
 *  Пользователь    user    admin
 *  Пароль          user    admin
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImp userDetailsService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/**/img/**", "/webjars/**", "/favicon.ico");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers( "/", "/index", "/book").permitAll()

                .and().authorizeRequests().antMatchers("/login").anonymous()

                .and()
                .authorizeRequests().antMatchers("/**/add", "/**/edit/*", "/**/update/*").hasAnyRole("USER", "ADMIN")

                .and()
                .authorizeRequests().antMatchers("/**/delete").hasRole("ADMIN")

                .and()
                .authorizeRequests().anyRequest().authenticated()

                .and()
                .formLogin()
                .defaultSuccessUrl("/", true)

                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

                .and().rememberMe()
                .key("otus")
                .tokenValiditySeconds(3600)
                .rememberMeCookieName("rme")

                .and()
                .anonymous().authorities("ROLE_ANONYMOUS").principal(new AnonymousUserDetails())

                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
        ;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }
}