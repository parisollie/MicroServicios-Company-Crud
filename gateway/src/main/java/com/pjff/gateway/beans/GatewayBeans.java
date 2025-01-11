package com.pjff.gateway.beans;


import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

//Vid 61
@Configuration
@AllArgsConstructor
public class GatewayBeans {

    @Bean
    @Profile(value = "eureka-of")
    //Vid 63,routeLocatorEurekaOff
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {
        return builder
                .routes()
                //Vid 61, creamos una nueva ruta
                .route(route -> route
                        //Todos los request *
                        //VId 64 le pone **,es para permitir el post
                        .path("/companies-crud/company/**")
                        .uri("http://localhost:8081")
                        //.uri("lb://companies-crud")
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .uri("http://localhost:7070")
                        //.uri("lb://report-ms")
                )
                .build();
    }
    //Vid 63
    @Bean
    @Profile(value = "eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route
                        .path("/companies-crud/company/**")
                        //lb = balanceo de cargas
                        .uri("lb://companies-crud")
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .build();
    }


    @Bean
    @Profile(value = "eureka-on-cb")
    public RouteLocator routeLocatorEurekaOnCB(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route
                        .path("/companies-crud/company/**")
                        //Vid 74
                        .filters(filter -> {
                            filter.circuitBreaker(config -> config
                                    .setName("gateway-cb")
                                    .setStatusCodes(Set.of("500", "400"))
                                    .setFallbackUri("forward:/companies-crud-fallback/company/*"));
                            return filter;
                        })
                        .uri("lb://companies-crud")
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .route(route -> route
                        .path("/companies-crud-fallback/company/**")
                        .uri("lb://companies-crud-fallback")
                )
                .build();
    }


}


