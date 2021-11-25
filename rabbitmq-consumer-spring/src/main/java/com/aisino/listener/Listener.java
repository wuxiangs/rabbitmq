package com.aisino.listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.ChannelN;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author wuxiang
 * @date 2021/11/25 9:39 上午
 *  Consumer ACK机制：
 *     1.设置手动签收 acknowledge="manual"
 *     2.让监听器类实现 ChannelAwareMessageListener接口
 *     3.如果消息成功处理,则调用channel的basicAck()签收
 *       如果消息失败处理,则调用channel的basicNack()拒签,broker重新发送给consumer
 *
 *
 *
 */
@Component
public class Listener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //接受消息
            System.out.println(new String(message.getBody()));
            //处理消息
            System.out.println("消息处理成功");
            //签收
            channel.basicAck(deliveryTag,true);
        }catch (Exception e){
            //第三个参数 requeue：重回队列,如果设置为true,消息重新回到queue,broker重新发送消息给消费端
            channel.basicNack(deliveryTag,true,true);
        }
    }
}
