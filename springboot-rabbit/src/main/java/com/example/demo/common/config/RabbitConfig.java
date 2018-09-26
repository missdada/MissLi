package com.example.demo.common.config;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitConfig {
	private static final String FANOUT = "fanout";

	private static final String QUEUE = "foo";

	@Bean
	public Queue queue() {
		return new Queue("hello88",true);
	}

	@Bean
    public Queue AMessage() throws IOException, TimeoutException {
		return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() throws IOException, TimeoutException {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() throws IOException, TimeoutException {
        return new Queue("fanout.C");
    }
    
    @Bean
    public Queue queue1() {
        return new Queue("hello.queue1", true); // true表示持久化该队列
    }
    
    @Bean
    public Queue queue2() {
        return new Queue("hello.queue2", true);
    }
    
    //topic：路由交换器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }
    
    //fanout：广播模式或订阅交换器
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }
    
    //绑定
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("key.1");
    }
    
    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("key.#");
    }
    
    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) throws IOException, TimeoutException {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) throws IOException, TimeoutException {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) throws IOException, TimeoutException {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }
}
