package com.pjff.gateway.beans;


import com.pjff.gateway.filters.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

//V-61,paso 2.0
@Configuration
@AllArgsConstructor
public class GatewayBeans {
    //V-114
    private final AuthFilter authFilter;
    //Bean para que cargue al contexto de spring
    @Bean
    //Paso 5.0,para decir que esta apagado
    @Profile(value = "eureka-of")
    //V-63,lo renombra a Off porque no depende de erueka
    //Paso 3.0,routeLocatorEurekaOff, inyectamos
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {
        return builder
                .routes()
                //Paso 4.0 creamos una nueva ruta
                .route(route -> route
                        //Todos los request *
                        //V-64 le pone **,es para permitir el post
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
    //V-63,Paso 6.0 eureka on ,para cuando queramos hacer los puertos random
    @Bean
    @Profile(value = "eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route
                        .path("/companies-crud/company/**")
                        //lb = balanceo de cargas y le ponemos el nombre del microservico
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

    //Vid 113
    @Bean
    @Profile(value = "oauth2")
    public RouteLocator routeLocatorOauth2(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route
                        .path("/companies-crud/company/**")
                        .filters(filter -> {
                            filter.circuitBreaker(config -> config
                                    .setName("gateway-cb")
                                    .setStatusCodes(Set.of("500", "400"))
                                    .setFallbackUri("forward:/companies-crud-fallback/company/*"));
                            filter.filter(this.authFilter);
                            return filter;
                        })
                        .uri("lb://companies-crud")
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .filters(filter -> filter.filter(this.authFilter))
                        .uri("lb://report-ms")
                )
                .route(route -> route
                        .path("/companies-crud-fallback/company/**")
                        //Vid 115
                        .filters(filter -> filter.filter(this.authFilter))
                        .uri("lb://companies-crud-fallback")
                )
                .route(route -> route
                        .path("/auth-server/auth/**")
                        //Vid 115
                        .uri("lb://auth-server")
                )
                .build();
    }


}


