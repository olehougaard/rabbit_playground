package dk.via;

import java.util.Random;

public class Constants {
    public static final String QUEUE_NAME = "hello";
    public static final String FANOUT_EXCHANGE = "logs";
    public static final String DIRECT_EXCHANGE = "directlogs";
    public static final String TOPIC_EXCHANGE = "topiclogs";
    public static final String HOST = "localhost";
    public static final String CHARSET_NAME = "UTF-8";

    public static final String SEVERITY_ERROR = "error";
    public static final String SEVERITY_WARNING = "warning";
    public static final String SEVERITY_INFO = "info";
    public static final String[] SEVERITIES = { SEVERITY_INFO, SEVERITY_WARNING, SEVERITY_ERROR };

    private static Random random = new Random();

    public static String nextElement(String[] elements) {
        return elements[random.nextInt(elements.length)];
    }
}
