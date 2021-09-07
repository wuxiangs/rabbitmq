package com.aisino.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wuxiang
 * @date 2021/9/7 3:59 下午
 */
public class Producer_Routing {

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
        //5.创建交换机
        /**
         * String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments
         *
         * exchange:交换机名称
         * type:交换机的类型
         *     DIRECT("direct"),定向
         *     FANOUT("fanout"),广播（扇形）发送消息到与交换机绑定的每一个队列
         *     TOPIC("topic"),通配符方式
         *     HEADERS("headers");参数匹配
         * durable:是否持久化
         * autoDelete:是否自动删除
         * internal:内部使用,一般false
         * arguments:参数
         */
        String exchangeName="test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);
        //6.创建队列
        String queue1Name="test_direct_queue1";
        String queue2Name="test_direct_queue2";
        channel.queueDeclare(queue1Name,true,false,false,null);
        channel.queueDeclare(queue2Name,true,false,false,null);
        //7.绑定队列与交换机
        /**
         * String queue, String exchange, String routingKey
         *
         * queue:队列名称
         * exchange:交换机名称
         * routingKey:路由器,绑定规则,如果交换机的类型为fanout,则routingKey设置为空
         *
         */
        //队列一的绑定 error
        channel.queueBind(queue1Name,exchangeName,"error");
        //队列二的绑定 info error warning
        channel.queueBind(queue2Name,exchangeName,"info");
        channel.queueBind(queue2Name,exchangeName,"error");
        channel.queueBind(queue2Name,exchangeName,"warning");
        //8.发送消息
        String body="日志信息:张三调用了findAll方法....日志级别：warning....";
        channel.basicPublish(exchangeName,"warning",null,body.getBytes());
        //9.释放资源
        channel.close();
        connection.close();
    }
}
