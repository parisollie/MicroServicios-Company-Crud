package com.pjff.report_ms.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Vid 47,Paso 50, creamos el balanceador de cargas
@Slf4j
public class LoadBalancerConfiguration {

    @Bean
    //recibe el pamametro de beans de spring, donde se cargan  los beans
    public ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
        log.info("Configuring load balancer");
        return ServiceInstanceListSupplier
                .builder()
                // Si quiero que sea reactivo o no
                // .withBlockingDiscoveryClient()
                .withBlockingDiscoveryClient()
                .build(context);
    }
}
