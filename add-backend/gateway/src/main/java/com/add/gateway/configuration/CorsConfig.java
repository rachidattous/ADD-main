package com.add.gateway.configuration;

import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

        @Bean
        CorsWebFilter corsWebFilter() {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.setAllowedOrigins(Arrays.asList("*"));
                corsConfig.setMaxAge(8000L);
                corsConfig.setAllowCredentials(false);
                corsConfig.addAllowedMethod("*");
                corsConfig.addAllowedHeader("*");
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfig);
                return new CorsWebFilter(source);
        }

        @Bean
        public RouteLocator applicationRouteLocator(RouteLocatorBuilder builder) {
                return builder
                                .routes()
                                .route(this::authRoute)
                                .route(this::notificationRoute)
                                .route(this::fileRoute)
                                .route(this::searchRoute)
                                .route(this::sbAdminRoute)
                                .route(this::eurekaRoute)
                                .build();
        }

        public Buildable<Route> authRoute(PredicateSpec spec) {

                return spec
                                .path("/api/auth/**")
                                .filters(this::authFilters)
                                .uri("lb://AUTH");

        }

        public UriSpec authFilters(GatewayFilterSpec gatewayFilterSpec) {
                return gatewayFilterSpec
                                .rewritePath("/api/auth/(?<remaining>.*)", "/api/auth/${remaining}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_FIRST")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE");

        }

        public Buildable<Route> notificationRoute(PredicateSpec spec) {

                return spec
                                .path("/api/notifications/**")
                                .filters(this::notificationFilters)
                                .uri("lb://NOTIFICATION");

        }

        public UriSpec notificationFilters(GatewayFilterSpec gatewayFilterSpec) {
                return gatewayFilterSpec
                                .rewritePath("/api/notifications/(?<remaining>.*)", "/api/notifications/${remaining}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_LAST")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE");

        }

        public Buildable<Route> fileRoute(PredicateSpec spec) {

                return spec
                                .path("/api/file/**")
                                .filters(this::fileFilters)
                                .uri("lb://FILE");

        }

        public UriSpec fileFilters(GatewayFilterSpec gatewayFilterSpec) {
                return gatewayFilterSpec
                                .rewritePath("/api/file/(?<remaining>.*)", "/api/file/${remaining}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_FIRST")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE");

        }

        public Buildable<Route> searchRoute(PredicateSpec spec) {

                return spec
                                .path("/api/search/**")
                                .filters(this::searchFilters)
                                .uri("lb://SEARCH");

        }

        public UriSpec searchFilters(GatewayFilterSpec gatewayFilterSpec) {
                return gatewayFilterSpec
                                .rewritePath("/api/search/(?<remaining>.*)", "/api/search/${remaining}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_FIRST")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE");

        }

        public Buildable<Route> sbAdminRoute(PredicateSpec spec) {

                return spec
                                .path("/api/sbAdmin/**")
                                .filters(this::sbAdminFilters)
                                .uri("lb://SBADMIN");

        }

        public UriSpec sbAdminFilters(GatewayFilterSpec gatewayFilterSpec) {
                return gatewayFilterSpec
                                .rewritePath("/api/sbAdmin/(?<remaining>.*)", "/api/sbAdmin/${remaining}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_FIRST")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE");

        }

        public Buildable<Route> eurekaRoute(PredicateSpec spec) {

                return spec
                                .path("/api/eureka/**")
                                .filters(this::eurekaFilters)
                                .uri("lb://EUREKA");

        }

        public UriSpec eurekaFilters(GatewayFilterSpec gatewayFilterSpec) {
                return gatewayFilterSpec
                                .rewritePath("/api/eureka/(?<remaining>.*)", "/api/eureka/${remaining}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_FIRST")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE");

        }

}
