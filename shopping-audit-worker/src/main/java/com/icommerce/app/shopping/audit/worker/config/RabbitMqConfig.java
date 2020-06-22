package com.icommerce.app.shopping.audit.worker.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executor;

@RefreshScope
@Configuration
@EnableRabbit
public class RabbitMqConfig {

    private static final String INPUT_QUEUE = "app.audit.worker.%s.queue";
    public static final String INPUT_ROUTING_KEY = "%s.mq.worker";

    public static final String TEXT_AUDIT = "audit";
    public static final String TEXT_RETRY_AUDIT = "retry.audit";


    @Value("${app.mq.audit.topic.exchangeName}")
    private String topicExchangeName;

    @Value("${app.mq.audit.retry.exchangeName}")
    private String retryTopicExchangeName;

    @Qualifier("mqaskExecutor")
    private Executor mqaskExecutor;

    @Value("${app.mq.prefetchCount}")
    private int prefetchCount;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setTaskExecutor(mqaskExecutor);
        factory.setPrefetchCount(prefetchCount);
        return factory;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public TopicExchange retryTopicExchange() {
        return new TopicExchange(retryTopicExchangeName);
    }

    @Bean
    public Queue auditLogsQueue() {
        return QueueBuilder.durable(String.format(INPUT_QUEUE, TEXT_AUDIT)).build();
    }

    @Bean
    Binding bindingAuditLogsQueue(Queue auditLogsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(auditLogsQueue).to(topicExchange).with(String.format(INPUT_ROUTING_KEY, TEXT_AUDIT));
    }

    @Bean
    public Queue retryAuditLogsQueue() {
        return QueueBuilder.durable(String.format(INPUT_QUEUE, TEXT_RETRY_AUDIT)).build();
    }

    @Bean
    Binding bindingRetryAuditLogsQueue(Queue retryAuditLogsQueue, TopicExchange retryTopicExchange) {
        return BindingBuilder.bind(retryAuditLogsQueue).to(retryTopicExchange).with(String.format(INPUT_ROUTING_KEY, TEXT_RETRY_AUDIT));
    }

    @Bean(name = "rabbitMqTemplate")
    @Primary
    public AmqpTemplate rabbitMqTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}