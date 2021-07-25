package wang.datahub.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;
import wang.datahub.dto.Warehouse;

import java.util.Random;

@Service
public class WarehouseService {

    public static final String[] WAREHOUSE_NAME = new String[]{"天津仓库","北京仓库","上海仓库","广州仓库","深圳仓库"};
    public static final String[] WAREHOUSE_LEVEL = new String[]{"A级","B级","C级","D级","E级"};


    public Warehouse mock(long id) {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setName(WAREHOUSE_NAME[random.nextInt(WAREHOUSE_NAME.length)]);
        warehouse.setLabel(WAREHOUSE_LEVEL[random.nextInt(WAREHOUSE_LEVEL.length)]);
        warehouse.setLon(random.nextDouble()+"");
        warehouse.setLat(random.nextDouble()+"");
        return warehouse;
    }


    @Autowired
    private ReactiveKafkaProducerTemplate template;

    public static final String WAREHOUSE_TOPIC = "warehouse";

    public Mono<Boolean> add(Warehouse warehouse) {
        Mono<SenderResult<Void>> resultMono = template.send(WAREHOUSE_TOPIC, warehouse.getId(), warehouse);
        return resultMono.flatMap(rs -> {
            if(rs.exception() != null) {
                System.out.println("send kafka error" + rs.exception());
                return Mono.just(false);
            }
            return Mono.just(true);
        });
    }


}
