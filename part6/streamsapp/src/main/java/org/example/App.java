package org.example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> entityStream = builder.stream("entity");

        KTable<String, String> countries = builder.table("country");

        entityStream
                .mapValues(Entity::fromString)
                .map((key, value) -> KeyValue.pair(value.code, value.asString()))
                .leftJoin(countries, (entityString, country) -> new EnrichedEntity(entityString, country).asString())
                .filter((key, value) -> Arrays.asList("HU", "PL", "RU").contains(key))
                .to("enriched");

        KafkaStreams streams = new KafkaStreams(builder.build(), config);

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
