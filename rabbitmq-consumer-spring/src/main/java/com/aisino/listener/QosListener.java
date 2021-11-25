package com.aisino.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author wuxiang
 * @date 2021/11/25 10:30 上午
 *
 * consumer 限流机制
 *   1.确保ack机制为手动确认的
 *   2.listener-container中配置属性
 *     prefetch=1 表示消费端每次从mq拉去一条消息来消费,直到手动确认消费完毕后,才会拉取下一条数据
 */
@Component
public class QosListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //接收消息
        System.out.println(new String(message.getBody()));
        //处理业务逻辑

        //手动签收
        channel.basicAck(deliveryTag,true);
    }
}
