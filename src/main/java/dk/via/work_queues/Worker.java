package dk.via.work_queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

import static dk.via.Constants.*;

public class Worker {
    private static void doWork(String task) {
        task.chars()
                .filter(ch -> ch == '.')
                .forEach(ch -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print((char)ch);
        });
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Scanner input = new Scanner(System.in)){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for tasks");
            channel.basicConsume(QUEUE_NAME, false, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), CHARSET_NAME);
                System.out.println(String.format("Received message \"%s\" on \"%s\"", message, QUEUE_NAME));
                doWork(message);
                System.out.println("Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }, consumerTag -> {
            });
            System.out.println("Press ENTER to exit");
            input.nextLine();
        }
    }
}
