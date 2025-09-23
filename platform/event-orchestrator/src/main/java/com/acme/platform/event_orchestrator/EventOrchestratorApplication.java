package com.acme.platform.event_orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventOrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventOrchestratorApplication.class, args);
	}
}
