package basic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author：YangJx
 * @Description：
 * @DateTime：2018/3/21 12:53
 */
public class Producer {
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
            Destination destination = session.createQueue("myQueue");
            //6.创建生产者
            MessageProducer messageProducer = session.createProducer(destination);
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("我是消息00000");
            //8.发送消息
            messageProducer.send(textMessage);
            //9.提交事务
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    //10.关闭连接
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}