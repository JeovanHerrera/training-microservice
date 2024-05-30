package com.jeovan.trainingmicroservice.configuration;

import com.jeovan.trainingmicroservice.dtos.TrainerMonthReportDTO;
import com.jeovan.trainingmicroservice.dtos.TrainerMonthReportRequestDTO;
import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;
import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms
public class ActiveMQConfig {

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory factory, MessageConverter messageConverter){

        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();

        jmsListenerContainerFactory.setConnectionFactory(factory);
        jmsListenerContainerFactory.setConcurrency("5-10");
        jmsListenerContainerFactory.setMessageConverter(messageConverter);
        return jmsListenerContainerFactory;
    }

    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("_type", TrainingDetailsDto.class);
        typeIdMappings.put("_monthReport", TrainerMonthReportDTO.class);
        typeIdMappings.put("_monthReportRequest", TrainerMonthReportRequestDTO.class);
        messageConverter.setTargetType(MessageType.TEXT);
        messageConverter.setTypeIdPropertyName("_type");
        messageConverter.setTypeIdMappings(typeIdMappings);
        return messageConverter;
    }
}
