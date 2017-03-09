package com.tilindra.demo.messaging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

import com.tilindra.demo.domain.Employee;

@Service
public class Producer {

	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate;
	@Autowired
	private MappingJackson2MessageConverter mappingJackson2MessageConverter;

	public void sendToRabbitmq(Employee emp, String extraContent) {

		this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);

		Map<String, Object> header = new HashMap<>();
		header.put("extraContent", extraContent);

		this.rabbitMessagingTemplate.convertAndSend("exchange", "queue.message", emp, header);
	}

	public void sendListToRabbitmq(List<Employee> empList, String extraContent) {

		this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);

		Map<String, Object> header = new HashMap<>();
		header.put("extraContent", extraContent);

		this.rabbitMessagingTemplate.convertAndSend("exchange", "queue.messages", empList, header);
	}

}
