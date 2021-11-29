package com.aisino.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author wuxiang
 * @date 2021/11/29 2:26 下午
 */
@Component
public class OrderListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("接收消息："+message.getBody());
        //处理业务逻辑
        System.out.println("根据订单id查询其状态");
        System.out.println("判断其状态是够支付成功");
        //手动签收
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
    }
}
