import MeetupResponseModel.MeetupRSVP;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;


public class StreamProcessor {

    public static boolean applyContainsFilter(String value, String filterValue) {
        System.out.println("raw value : " + value);
        if (value.contains(filterValue)) {
            System.out.println("\tfiltered value : " + value);
            MeetupRSVP rsvp = MeetupFeedJsonParser.ParseJson(value);
            AWSObjectUploader.uploadObjectToBucket("dds-stream", rsvp.toString());
            return true;
        }
        System.out.println("\traw value : " + value);
        MeetupRSVP rsvp = MeetupFeedJsonParser.ParseJson(value);
        try {
            GCPObjectUploader.uploadObjectToBucket("dds-stream", rsvp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String inputTopic = "MeetupRSVPTopic";

        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(
                StreamsConfig.APPLICATION_ID_CONFIG, "MeetupRSVP");

        streamsConfiguration.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        streamsConfiguration.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        String bootstrapServers = "localhost:9092";
        streamsConfiguration.put(
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);


        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream(inputTopic);

        KStream<String, String> filteredValues = source.filter((k, v) -> {
            if (applyContainsFilter(v, "\"group_country\":\"us\""))
                return true;
            else return false;
        });

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.start();
    }
}
