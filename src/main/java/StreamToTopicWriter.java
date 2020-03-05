import MeetupResponseModel.MeetupRSVP;
import org.apache.kafka.clients.producer.*;

import java.net.*;
import java.io.*;
import java.util.Properties;

public class StreamToTopicWriter {

    private static final String TOPIC_NAME = "MeetupRSVPTopic";
    private static KafkaProducer<String, String> kafkaProducer = null;
    private final String KAFKA_CLUSTER_ENV_VAR_NAME = "KAFKA_CLUSTER";

    public StreamToTopicWriter() {

        Properties kafkaProps = new Properties();

        String defaultClusterValue = "localhost:9092";
        String kafkaCluster = System.getProperty(KAFKA_CLUSTER_ENV_VAR_NAME, defaultClusterValue);

        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCluster);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put(ProducerConfig.ACKS_CONFIG, "0");

        this.kafkaProducer = new KafkaProducer<>(kafkaProps);

    }

    public static void main(String[] args) {

        try {
            URL streamSource = new URL("http://stream.meetup.com/2/rsvps");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(streamSource.openStream()));

            String inputLine;
            MeetupRSVP rsvp;
            int counter = 0;
            StreamToTopicWriter streamToTopicWriter = new StreamToTopicWriter();

            while ((inputLine = in.readLine()) != null) {
                //rsvp = MeetupFeedJsonParser.ParseJson(inputLine);
                //System.out.println(rsvp);
                streamToTopicWriter.WriteToKafkaTopic("MeetupRSVP_" + counter, inputLine);
                counter++;
            }

            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteToKafkaTopic(String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, key, value);

        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata rm, Exception excptn) {
                if (excptn != null) {
                    System.out.println("Error sending message with key " + key + excptn.getMessage());
                } else {
                    System.out.println("Partition for key-value {0}::{1} is {2}" + key + value + rm.partition());
                }

            }
        });

    }
}
