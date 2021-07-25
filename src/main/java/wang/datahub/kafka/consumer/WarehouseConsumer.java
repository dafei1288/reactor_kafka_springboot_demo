package wang.datahub.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.ReceiverOptions;
import wang.datahub.dto.Warehouse;
import wang.datahub.service.WarehouseService;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class WarehouseConsumer {
    @Autowired
    private KafkaProperties properties;

    @PostConstruct
    public void consumer() {
        ReceiverOptions<Long, Warehouse> options = ReceiverOptions.create(properties.getConsumer().buildProperties());
        options = options.subscription(Collections.singleton(WarehouseService.WAREHOUSE_TOPIC));
        new ReactiveKafkaConsumerTemplate(options)
                .receiveAutoAck()
                .subscribe(record -> {
                    System.out.println("Warehouse Record:" + record);
                });
    }
}
