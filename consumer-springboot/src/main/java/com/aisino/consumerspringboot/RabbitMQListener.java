package com.aisino.consumerspringboot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wuxiang
 * @date 2021/11/24 2:34 下午
 */
@Component
public class RabbitMQListener {

    @RabbitListener(queues = "boot_topic_queue")
    public void ListenerQueue(Message message){
        System.out.println(new String(message.getBody()));
    }
}

