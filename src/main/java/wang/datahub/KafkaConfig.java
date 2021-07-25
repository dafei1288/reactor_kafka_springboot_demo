package wang.datahub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class KafkaConfig {
    @Autowired
    private KafkaProperties properties;

    @Bean
    public ReactiveKafkaProducerTemplate reactiveKafkaProducerTemplate() {
        SenderOptions options = SenderOptions.create(properties.getProducer().buildProperties());
        ReactiveKafkaProducerTemplate template = new ReactiveKafkaProducerTemplate(options);
        return template;
    }
}