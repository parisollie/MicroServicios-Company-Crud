package com.pjff.gateway.filters;

import com.pjff.gateway.dtos.TokenDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// V-113,paso 3.41
@Component
public class AuthFilter implements GatewayFilter {

    // Iyectamos
    private final WebClient webClient;

    // escribimos la url de la validacion
    private static final String AUTH_VALIDATE_URI = "http://localhost:3030/auth-server/auth/jwt";
    // private static final String AUTH_VALIDATE_URI =
    // "http://ms-auth:3030/auth-server/auth/jwt";
    private static final String ACCESS_TOKEN_HEADER_NAME = "accessToken";

    public AuthFilter() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Paso 3.42 ,para obtener el request, necesito que el header sea de
        // autorizacion
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange);
        }

        final var tokenHeader = exchange
                .getRequest()
                .getHeaders()
                .get(HttpHeaders.AUTHORIZATION).get(0);

        // paso 3.44,para cortar el token con el espacio en blanco
        final var chunks = tokenHeader.split(" ");

        // V-113,paso 3.45 en caso de que el token venga mal
        // V-115, debe ser diferente
        if (chunks.length != 2 || !chunks[0].equals("Bearer")) {
            return this.onError(exchange);
        }
        final var token = chunks[1];

        // Paso 3.46, En caso de que todo vaya correcto.
        return this.webClient
                .post()
                .uri(AUTH_VALIDATE_URI)
                .header(ACCESS_TOKEN_HEADER_NAME, token)
                .retrieve()
                // paso 3.48
                .bodyToMono(TokenDto.class)
                .map(response -> exchange)
                .flatMap(chain::filter);
    }

    // Paso 3.43, cuando haya un error
    private Mono<Void> onError(ServerWebExchange exchange) {
        final var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        return response.setComplete();
    }
}
