package rabbit.one;

import static rabbit.one.RabbitMqConfig.DIRECT_QUEUE;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.time.LocalTime;
import java.util.UUID;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


/**
 * @ClassName MQTest
 * @Description 消息队列测试
 * @Author lly
 * @Date 2019-05-13 10:50
 * @Version 1.0
 **/
@Component
@Slf4j
public class MQTest implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

  @Resource
  private RabbitTemplate rabbitTemplate;

  public MQTest(RabbitTemplate rabbitTemplate) {
    rabbitTemplate.setConfirmCallback(this);
    rabbitTemplate.setReturnCallback(this);
  }

  public void sendDirect() {
    rabbitTemplate.convertAndSend("", "test-queue", "test_queue" + LocalTime.now(),
        new CorrelationData(UUID.randomUUID().toString()));
    log.info("发送消息:{}", "test_queue" + LocalTime.now());
  }

  //如果不存在，自动创建队列
  @RabbitListener(queuesToDeclare = @Queue("test-queue"))
  public void receiverMq(String msg) {
    log.info("接收到队列消息:{}", msg);
  }


  public void sendFanout() {
    //回调id
    CorrelationData cId = new CorrelationData(UUID.randomUUID().toString());
//        rabbitTemplate.convertAndSend(RabbitMqConfig.FANOUT_EXCHANGE, "", "广播者模式测试",cId);
    Object object = rabbitTemplate
        .convertSendAndReceive(RabbitMqConfig.FANOUT_EXCHANGE, "", "广播者模式测试", cId);
    log.info("发送消息:{},object:{}", "广播者模式测试" + LocalTime.now(), object);
  }

  @RabbitListener(queues = RabbitMqConfig.FANOUT_QUEUE_ONE)
  public void receiverMqFanout(String msg, Channel channel, Message message) throws IOException {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
      log.info("接收到队列fanout_queue_one消息:{}", msg);
      channel.basicAck(deliveryTag, false);
    } catch (Exception e) {
      e.printStackTrace();
      //多次失败重新放回会导致队列堵塞或死循环问题 丢弃这条消息
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
      log.error("接收消息失败");
    }
  }

  @RabbitListener(queues = RabbitMqConfig.FANOUT_QUEUE_TWO)
  public void receiverMqFanoutTwo(String msg) {
    log.info("接收到队列fanout_queue_two消息:{}", msg);
  }


  //发送订阅者模式
  public void sendTopic() {
    CorrelationData cId = new CorrelationData(UUID.randomUUID().toString());
    CorrelationData cId01 = new CorrelationData(UUID.randomUUID().toString());
    log.info("订阅者模式->发送消息:routing_key_one");
    rabbitTemplate.convertSendAndReceive("topic_exchange", "routing_key_one",
        "routing_key_one" + LocalTime.now(), cId);
    log.info("订阅者模式->发送消息routing_key_two");
    rabbitTemplate.convertSendAndReceive("topic_exchange", "routing_key_two",
        "routing_key_two" + LocalTime.now(), cId01);
  }

  //如果不存在，自动创建队列和交换器并且绑定
  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = "topic_queue01", durable = "true"),
          exchange = @Exchange(value = "topic_exchange", type = ExchangeTypes.TOPIC),
          key = "routing_key_one")})
  public void receiverMqExchage(String msg, Channel channel, Message message) throws IOException {

    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
      log.info("接收到topic_routing_key_one消息:{}", msg);
      log.error("发生异常");
      int i = 1 / 0;
      //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
      channel.basicAck(deliveryTag, false);
    } catch (Exception e) {
      log.error("接收消息失败,重新放回队列");
      //requeu，为true，代表重新放入队列多次失败重新放回会导致队列堵塞或死循环问题，
      // 解决方案，剔除此消息，然后记录到db中去补偿
      //channel.basicNack(deliveryTag, false, true);
      //拒绝消息
      //channel.basicReject(deliveryTag, true);
    }
  }

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = "topic_queue02", durable = "true"),
          exchange = @Exchange(value = "topic_exchange", type = ExchangeTypes.TOPIC),
          key = "routing_key_two")})
  public void receiverMqExchageTwo(String msg) {
    log.info("接收到topic_routing_key_two消息:{}", msg);
  }

  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    if (ack) {
      log.info("消息投递成功,ID为: {}", correlationData.getId());
      return;
    }
    log.error("消息投递失败,ID为: {},错误信息: {}", correlationData.getId(), cause);
  }

  /**
   * @return
   * @Author lly
   * @Description 消息消费发生异常时返回
   * @Date 2019-05-14 16:22
   * @Param [message, replyCode, replyText, exchange, routingKey]
   **/
  @Override
  public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
      String routingKey) {
    log.info("消息发送失败id:{}", message.getMessageProperties().getCorrelationId());
    log.info("消息主体 message : {}", message);
    log.info("消息主体 message : {}", replyCode);
    log.info("描述：" + replyText);
    log.info("消息使用的交换器 exchange : {}", exchange);
    log.info("消息使用的路由键 routing : {}", routingKey);
  }
}

