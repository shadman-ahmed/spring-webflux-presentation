package com.shadmanahmed.springwebfluxpresentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
public class WelcomeController {
    @GetMapping("hello")
    public String sayHello() {
        log.info("non-reactive hello");
        return "Hi";
    }

    @GetMapping("reactive-hello")
    public Mono<String> sayReactiveHello() {
        log.info("reactive hello");
        return Mono.defer(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Inside reactive chain");
                    return Mono.just("Reactive Hi");
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
