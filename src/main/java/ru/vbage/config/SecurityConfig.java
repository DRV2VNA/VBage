package ru.vbage.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.vbage.security.jwt.JwtConfigurer;
import ru.vbage.security.jwt.JwtTokenProvider;

/**
 * The type Security config.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // AUTH ENDPOINTS
    private static final String AUTH_ENDPOINT = "/v1/api/auth/**";
    private static final String SIGNUP_ENDPOINT = "/v1/api/signup/**";

    private static final String PUBLIC_USERS_ENDPOINT = "/v1/api/user/public/**";
    private static final String PRIVATE_USERS_ENDPOINT = "/v1/api/user/private/**";

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Instantiates a new Security config.
     *
     * @param jwtTokenProvider the jwt token provider
     */
    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/v2/api-docs").permitAll()
                    .antMatchers(AUTH_ENDPOINT).permitAll()
                    .antMatchers(SIGNUP_ENDPOINT).permitAll()

                    .antMatchers(PUBLIC_USERS_ENDPOINT).permitAll()


                    .antMatchers(PRIVATE_USERS_ENDPOINT).hasAnyRole("USER", "ADMIN")

                    .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
