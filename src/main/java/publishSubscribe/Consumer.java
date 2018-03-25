package publishSubscribe;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author：YangJx
 * @Description：
 * @DateTime：2018/3/21 12:53
 */
public class Consumer {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            //1.创建连接工厂
            ConnectionFactory connectorFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnectionFactory.DEFAULT_USER,
                    ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                    ActiveMQConnectionFactory.DEFAULT_BROKER_URL
            );
            //2.创建连接
            connection = connectorFactory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建回话
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //5.创建目标
            Destination destination = session.createTopic("myTopic");
            //6.创建消费者
            MessageConsumer messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    if(message != null) {
                        try {
                            System.out.println(((TextMessage)message).getText());
                            message.acknowledge();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            /*while (true) {
                //7.接收消息
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                if (textMessage != null) {
                    textMessage.acknowledge();
                    System.err.println(textMessage.getText());
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            if (connection != null) {
                try {
                    //8.关闭连接
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}