package com.aisino.producerspringboot;

import com.aisino.producerspringboot.config.RabbitmqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerSpringbootApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_NAME,"boot.wx","boot mq hello");
    }

}
