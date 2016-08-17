package io.spring;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.MediaType;
import org.springframework.http.codec.SseEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class RxController {

	private Flux<String> messages;

	@StreamListener
	public void sink(@Input(Sink.INPUT) Flux<String> messages) {
		this.messages = messages;
	}

	@GetMapping("/events")
	public Flux<SseEvent> events() {
		return messages.map(m -> new SseEvent(m, MediaType.APPLICATION_JSON));
	}
}
