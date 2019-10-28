package dk.via.hello_world;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import static dk.via.Constants.*;

import java.util.Scanner;

public class Receiver {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Scanner input = new Scanner(System.in)){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for messages");
            channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), CHARSET_NAME);
                System.out.println(String.format("Received message \"%s\" on \"%s\"", message, QUEUE_NAME));
            }, consumerTag -> {
            });
            System.out.println("Press ENTER to exit");
            input.nextLine();
        }
    }
}
