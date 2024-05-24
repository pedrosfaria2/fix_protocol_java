package com.example.fix_application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.SocketInitiator;
import org.springframework.beans.factory.annotation.Qualifier;

@SpringBootApplication
public class FixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(@Qualifier("socketInitiator") SocketInitiator initiator) {
		return args -> {
			initiator.start();
			Runtime.getRuntime().addShutdownHook(new Thread(initiator::stop));
		};
	}
}
