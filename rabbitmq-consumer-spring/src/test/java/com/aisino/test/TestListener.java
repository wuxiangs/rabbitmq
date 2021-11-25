package com.aisino.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wuxiang
 * @date 2021/11/25 9:46 上午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rabbitmq-consumer-spring.xml")
public class TestListener {

    @Test
    public void test(){
        while (true){

        }
    }
}
