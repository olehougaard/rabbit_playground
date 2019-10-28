package dk.via.direct;

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
            channel.exchangeDeclare(DIRECT_EXCHANGE, "direct");
            String severity = (Math.random() < .333) ? SEVERITY_ERROR : (Math.random() < .5) ? SEVERITY_WARNING : SEVERITY_INFO;
            String message = String.format("Hello World at %s!", new Date().toString());
            channel.basicPublish(DIRECT_EXCHANGE, severity, null, message.getBytes(CHARSET_NAME));
            System.out.println(String.format("Send %s \"%s\" to \"%s\"", severity, message, QUEUE_NAME));
        }
    }
}
