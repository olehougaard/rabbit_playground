package dk.via.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

import static dk.via.Constants.*;

public class EmitLog {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(FANOUT_EXCHANGE, "fanout");
            String message = String.format("Hello World at %s!", new Date().toString());
            channel.basicPublish(FANOUT_EXCHANGE, "", null, message.getBytes(CHARSET_NAME));
            System.out.println(String.format("Send \"%s\" to \"%s\"", message, QUEUE_NAME));
        }
    }
}
