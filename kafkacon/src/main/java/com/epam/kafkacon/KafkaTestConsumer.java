//package com.epam.kafkacon;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import wiremock.com.google.common.collect.ImmutableMap;
//
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class KafkaTestConsumer {
//    private static final String AUTO_COMMIT_PREFERENCE = "true";
//    private static final String AUTO_OFFSET_RESET_PREFERENCE = "earliest";
//
//    private final Consumer<Integer, String> consumer;
//    private final String topic;
//
//    public KafkaTestConsumer(String broker, String topic) {
//        this(broker, UUID.randomUUID().toString(), topic);
//    }
//
//    private KafkaTestConsumer(String broker, String consumerGroup, String topic) {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                broker);
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                consumerGroup);
//        props.put(
//                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
//                AUTO_OFFSET_RESET_PREFERENCE);
//        this.consumer = new DefaultKafkaConsumerFactory<Integer, String>(props)
//            .createConsumer();
//        this.topic = topic;
//    }
//
//    @PostConstruct
//    private void setup() {
//        consumer.subscribe(Collections.singleton(topic));
//        // For some reason this is a mandatory step otherwise messages wont appear on the topic while polling it
//        getAllRecords();
//    }
//
//    @PreDestroy
//    private void teardown() {
//        consumer.close();;
//    }
//
//    public List<String> getAllRecords() {
//        return getAllRecords(0L);
//    }
//
//    public List<String> getAllRecords(long timeout) {
//        return StreamSupport
//            .stream(KafkaTestUtils.getRecords(consumer, timeout).spliterator(), false)
//            .map(record -> record.value())
//            .collect(Collectors.toList());
//    }
//
//    public <T> List<T> getAllRecords(Class<T> type, long timeout) {
//        return getAllRecords(timeout)
//            .stream()
//            .map(value -> parse(value, type))
//            .collect(Collectors.toList());
//    }
//
//    public Optional<String> getLastRecord(long timeout) {
//        return reverse(lastN(1, getAllRecords(timeout))).stream().findFirst();
//    }
//
//    public <T> Optional<T> getLastRecord(Class<T> type, long timeout) {
//        return reverse(lastN(1, getAllRecords(type, timeout))).stream().findFirst();
//    }
//
//    public List<String> getLastNRecordsInReversedOrder(int numberOfRecords, long timeout) {
//        return reverse(lastN(numberOfRecords, getAllRecords(timeout)));
//    }
//
//    public <T> List<T> getLastNRecordsInReversedOrder(int numberOfRecords, Class<T> type, long timeout) {
//        return reverse(lastN(numberOfRecords, getAllRecords(type, timeout)));
//    }
//
//    private <T> List<T> lastN(int numberOfRecords, List<T> original) {
//        return original
//            .stream()
//            .skip(Math.max(0, original.size() - numberOfRecords))
//            .collect(Collectors.toList());
//    }
//
//    private <T> List<T> reverse(List<T> original) {
//        List<T> reversedList = new ArrayList<T>(original);
//        Collections.reverse(reversedList);
//        return reversedList;
//    }
//
//    @SneakyThrows
//    private <T> T parse(String data, Class<T> type) {
//        return new ObjectMapper().readValue(data, type);
//    }
//}
