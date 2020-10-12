package com.ebook.shippingservice.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import com.ebook.api.message.ShippingMessage;

@Configuration
@EnableJms
public class JMSConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;

	@Bean
	public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		activeMQConnectionFactory.setTrustAllPackages(true);
		return activeMQConnectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(receiverActiveMQConnectionFactory());
		factory.setMessageConverter(messageConverter());
		return factory;
	}
	
	@Bean // Serialize message content to json using TextMessage
	  public MessageConverter messageConverter() {
	    return new JsonMessageConverter(ShippingMessage.class);
	  }
	
	@Bean
	public JmsTemplate jmsTemplate() {
	    JmsTemplate template = new JmsTemplate();
	    template.setConnectionFactory(receiverActiveMQConnectionFactory());
	    template.setMessageConverter(messageConverter());
	    template.setPubSubDomain(true);
	    return template;
	}

}
