package com.pucrs.microsservicos;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String FANOUTEXCHANGENAME = "pagamentos.v1.payment-request";
    public static final String QUEUE_CADASTRAMENTO = "pagamentos.cadastramento";
    public static final String QUEUE_ASSINATURAS_VALIDAS = "pagamentos.assinaturasvalidas";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUTEXCHANGENAME);
    }

    @Bean
    public Queue cadastramentoQueue() {
        return new Queue(QUEUE_CADASTRAMENTO);
    }

    @Bean
    public Queue assinaturasValidasQueue() {
        return new Queue(QUEUE_ASSINATURAS_VALIDAS);
    }

    @Bean
    public Binding cadastramentoBinding(Queue cadastramentoQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(cadastramentoQueue).to(fanoutExchange);
    }

    @Bean
    public Binding assinaturasValidasBinding(Queue assinaturasValidasQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(assinaturasValidasQueue).to(fanoutExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory, MessageConverter messageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
