package com.example.demo;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(Sink.class)
public class KafkaReceiver {

	public static String _Vote;
	private final Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

	@StreamListener(Sink.INPUT_1)
	private void receive(String vote) {
		KafkaReceiver._Vote=vote;
		logger.info("receive message : " + vote);
	}	
}