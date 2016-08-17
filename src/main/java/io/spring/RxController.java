package io.spring;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.MediaType;
import org.springframework.http.codec.SseEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@EnableBinding(Sink.class)
public class RxController {

	private final Flux<Long> events;

	public RxController() {
		this.events = Flux.intervalMillis(1000);
	}

	@GetMapping("/events")
	public Flux<SseEvent> events() {
		return events.map(e -> new SseEvent(e, MediaType.APPLICATION_JSON));
	}

	@StreamListener(Sink.INPUT)
	public void sink(Flux<?> messages) {
		messages.subscribe(System.out::println);
	}

}
