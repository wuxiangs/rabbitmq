package com.aisino.rabbitmq;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wuxiang
 * @date 2021/9/7 3:10 下午
 */
public class Consumer_PubSub2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建工厂连接
        ConnectionFactory connectionFactory=new ConnectionFactory();
        //2.设置参数
        //默认值localhost
        connectionFactory.setHost("192.168.1.19");
        //默认值 /
        connectionFactory.setVirtualHost("/itcast");
        //默认值 guest
        connectionFactory.setUsername("guest");
        //默认值 guest
        connectionFactory.setPassword("guest");
        //3.创建连接 connection
        Connection connection = connectionFactory.newConnection();
        //4.创建Channel
        Channel channel = connection.createChannel();
        //5.创建队列 Queue
        //6.接受消息
        String queue1Name="test_fanout_queue1";
        String queue2Name="test_fanout_queue2";
        Consumer consumer=new DefaultConsumer(channel){
            /**
             * 回调方法，当收到信息后，自动执行该方法
             * @param consumerTag 标识
             * @param envelope 获取一些信息,交换机,路由key...
             * @param properties 配置信息
             * @param body 真是的数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body:"+new String(body));
                System.out.println("将信息保存到数据库......");
            }
        };
        /**
         * String queue, boolean autoAck, Consumer callback
         *
         * queue:队列名称
         * autoAck:是否自动确认
         * callback:回调函数
         */
        channel.basicConsume(queue2Name,true,consumer);
    }
}
