package com.adobe.demo;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
public class FLuxController {
	
	@GetMapping()
	public Flux<Integer> publishInteger() {
		return Flux.just(10, 20, 30, 40).delayElements(Duration.ofSeconds(1)).log();
	}
	
	@GetMapping(value="/stream", produces = "application/stream+json")
	public Flux<Integer> publishIntegerStream() {
		return Flux.just(10, 20, 30, 40).delayElements(Duration.ofSeconds(1)).log();
	}
	
	@GetMapping("/mono")
	public Mono<String> mono() {
		return Mono.just("Hello World");
	}
}
