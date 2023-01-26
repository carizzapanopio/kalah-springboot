package com.izza.kalahapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.izza.kalahapplication.model.Game;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

@Configuration
public class SubscriptionConfig {

    @Bean
    public Sinks.Many<Game> gameSink() {
        return Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    }

    @Bean
    public Flux<Game> gameFlux(Sinks.Many<Game> gameSink) {
        return gameSink.asFlux();
    }
    

}