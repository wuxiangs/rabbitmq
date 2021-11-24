package com.aisino.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wuxiang
 * @date 2021/9/7 2:39 下午
 */
public class Producer_WorkQueues {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建工厂连接
        ConnectionFactory connectionFactory=new ConnectionFactory();
        //2.设置参数
        //默认值localhost
        connectionFactory.setHost("192.168.1.16");
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
        /**
         * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
         *
         * 1.queue:队列名称
         * 2.durable:是否持久化。mq重启之后数据还在
         * 3.exclusive:
         *     是否独占：只能有一个消费者监听这队列
         *     当connection关闭时，是否删除队列
         * 4.autoDelete:是否自动删除,当没有consumer时,他会自动删除
         * 5.arguments:参数
         */
        //如果没有一个名字叫hello_world的队列,则会创建,如果有则不会创建
        channel.queueDeclare("work_queues",true,false,false,null);
        for (int i=0;i<10;i++){
            String body=i+":hello rabbitmq~~~";
            channel.basicPublish("","work_queues",null, body.getBytes());
        }

        //释放资源
        channel.close();
        connection.close();

    }
}
