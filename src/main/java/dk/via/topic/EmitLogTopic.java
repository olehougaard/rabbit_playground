package dk.via.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;
import java.util.Random;

import static dk.via.Constants.*;

public class EmitLogTopic {
    // <job description>.<experience>.<favorite color>
    private final static String[] TOPICS = {
            "accountant.experienced.blue",
            "accountant.green.red",
            "developer.green.blue",
            "developer.experienced.green"
    };

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(TOPIC_EXCHANGE, "topic");
            String topic = nextElement(TOPICS);
            String message = String.format("New employee at %s!", new Date().toString());
            channel.basicPublish(TOPIC_EXCHANGE, topic, null, message.getBytes(CHARSET_NAME));
            System.out.println(String.format("Send \"%s\" as %s to \"%s\"", message, topic, QUEUE_NAME));
        }
    }
}
