package com.aisino.producerspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxiang
 * @date 2021/9/10 10:15 上午
 */
@Configuration
public class RabbitmqConfig {

    public static final String EXCHANGE_NAME="boot_topic_exchange";

    public static final String QUEUE_NAME="boot_topic_queue";

    /**
     * 交换机
     * @return
     */
    @Bean(name = "bootExchange")
    public Exchange bootExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 队列
     * @return
     */
    @Bean(name="bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    //队列和交换机的绑定关系 binding
    /**
     * 知道哪个队列
     * 知道哪个交换
     * routing key
     */

    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue,@Qualifier("bootExchange") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }


}
