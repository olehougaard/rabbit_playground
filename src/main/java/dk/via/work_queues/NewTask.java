package dk.via.work_queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static dk.via.Constants.*;

public class NewTask {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = String.join(" ", args);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(CHARSET_NAME));
            System.out.println(String.format("Send \"%s\" to \"%s\"", message, QUEUE_NAME));
        }
    }
}
