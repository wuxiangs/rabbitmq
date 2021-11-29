package test;

import com.rabbitmq.client.ConfirmCallback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wuxiang
 * @date 2021/11/24 3:31 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rabbitmq-producer-spring.xml")
public class ConfirmTest {


    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 确认模式
     *  步骤：
     *    1.确认模式开启 confirm-type="CORRELATED"
     *    2.在rabbitTemplate定义ConfirmCallBack回调函数
     */
    @Test
    public void testConfirm(){

        //2.定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param b exchange 交换机是否收到了信息 true 成功 false 失败
             * @param s 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("confirm方法被执行了");
                if(b){
                    System.out.println("接收成功消息");
                }else{
                    System.out.println("接收失败消息"+s);
                    //做一些处理 让信息再次发送
                }
            }
        });

        // 3.发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm","confirm","message confirm");
    }

    /**
     * 回退模式： 当消息发送给exchange后，exchange路由到queue失败时 才会执行ReturnCallBack
     * 步骤：
     *          1.回退模式开启：publisher-returns="true"
     *          2.在rabbitTemplate定义ReturnCallBack回调函数
     *          3.设置Exchange处理消息的模式：
     *             3.1.如果消息没有路由到Queue,则丢弃消息（默认）
     *             3.2.如果消息没有路由到Queue,返回给消息发送方 ReturnCallBack
     */
    @Test
    public void testReturn(){

        //设置交换机处理失败消息的模式
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
//                Message message = returnedMessage.getMessage();  //消息对象
//                int replyCode = returnedMessage.getReplyCode();  //错误码
//                String replyText = returnedMessage.getReplyText(); //错误消息
//                String routingKey = returnedMessage.getRoutingKey(); //路由key
//                String exchange = returnedMessage.getExchange(); //交换机


                System.out.println("return执行了");
                //处理
            }
        });

        // 3.发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm","confirm","message confirm");
    }


    /**
     * 过期时间
     *   1.队列的统一过期
     *   2.消息过期
     *
     *   如果设置了消息的过期时间,也设置了队列的过期时间,它以时间短的为准
     *   队列过期后,会将队列中所有的消息移除
     *   消息过期后,只有消息在队列顶端,才会判断其是否过期(移除掉)
     */
    @Test
    public void testTtl(){
//        /**
//         * 队列统一过期
//         */
//        for (int i=0;i<10;i++){
//            rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.test","hello");
//        }

        /**
         * 消息后处理对象,设置一些消息的参数信息
         */
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置message的信息
                message.getMessageProperties().setExpiration("5000");
                //返回给消息
                return message;
            }
        };

        /**
             * 消息的单独过期
             */
        rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.test","hello",messagePostProcessor);

    }


    /**
     * 发送测试死信消息
     *   1.过期时间
     *   2.长度限制
     *   3.消息拒收
     */
    @Test
    public void testDlx(){
        //过期时间,死信消息
        //rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.wx", "吴祥哈哈哈");

        //长度限制,死信消息
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.wx", "吴祥哈哈哈"+i);
        }

    }

    /**
     * 延迟队列
     */
    @Test
    public void testDelayQueue(){
        rabbitTemplate.convertAndSend("order_exchange","order.message","订单信息：id=1,time:2021-10-10");
    }
}
