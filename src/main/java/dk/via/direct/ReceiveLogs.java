package dk.via.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

import static dk.via.Constants.*;

public class ReceiveLogs {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Scanner input = new Scanner(System.in)){
            String queueName = channel.queueDeclare().getQueue();
            for(String severity: SEVERITIES) {
                if (Math.random() < .4) {
                    channel.queueBind(queueName, DIRECT_EXCHANGE, severity);
                    System.out.println(String.format("Waiting for %ss", severity));
                }
            }
            channel.basicConsume(queueName, true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), CHARSET_NAME);
                System.out.println(String.format("Received %s \"%s\" on \"%s\"", delivery.getEnvelope().getRoutingKey(), message, QUEUE_NAME));
            }, consumerTag -> {
            });
            System.out.println("Press ENTER to exit");
            input.nextLine();
        }
    }
}