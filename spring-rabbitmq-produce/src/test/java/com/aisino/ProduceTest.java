package com.aisino;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wuxiang
 * @date 2021/9/8 10:25 上午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProduceTest {

    //注入rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testHelloWorld(){
        //发送消息
        rabbitTemplate.convertAndSend("spring_queue","hello world spring");
    }
    /**
     * 发送fanout信息
     */
    @Test
    public void testFanOut(){
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","spring fanout......");
    }

    /**
     * 发送topic信息
     */
    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("spring_topic_exchange","wx.he.ha","spring topic......");
    }
}
