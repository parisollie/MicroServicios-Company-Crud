package com.pjff.companies_crud.configs;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//V-91,paso 3.7
@Component
@Order(999)
@Slf4j
// Recibe mi contexto de observacion
public class LogObservationHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Observation.Context context) {
        // Esamos iniciando la aplicacion.
        log.info("LogObservationHandler::onStart: {}", context.getName());
    }

    @Override
    public void onError(Observation.Context context) {
        log.info("LogObservationHandler::onError: {}", context.getName());
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("LogObservationHandler::onStop: {}", context.getName());
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}
