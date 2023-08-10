package lt.monikos.meepley.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // Protect endpoints at /api/<type>/secure
        http.authorizeRequests(configurer ->
                        configurer
                                .requestMatchers("/api/games/secure/**",
                                        "/api/reviews/secure/**",
                                        "/api/account/**",
                                        "/api/messages/secure/**",
                                        "/api/histories/**",
                                        "/api/admin/secure/**")
                                .authenticated()
                                .anyRequest().permitAll())
                .oauth2ResourceServer()
                .jwt();

        http.cors();

        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        return http.build();
    }
}
