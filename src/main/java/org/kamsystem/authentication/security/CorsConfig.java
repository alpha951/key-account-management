package org.kamsystem.authentication.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow these origins
        config.addAllowedOrigin("http://localhost:5173");
        // For production, add your domain
        config.addAllowedOrigin("https://key.keshavcarpenter.tech");

        // Allow these HTTP methods
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");

        // Allow all headers
        config.addAllowedHeader("*");

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}