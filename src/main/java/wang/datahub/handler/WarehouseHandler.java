package wang.datahub.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wang.datahub.dto.User;
import wang.datahub.dto.Warehouse;
import wang.datahub.service.WarehouseService;

@Component
public class WarehouseHandler {
    @Autowired
    WarehouseService warehouseService;
    private long i = 1;
    public Mono<ServerResponse> addWarehouse(ServerRequest request) {
        //mock 数据
        Warehouse warehouse = warehouseService.mock(i++);
        Mono<Boolean> tag = warehouseService.add(warehouse);
        return ServerResponse.ok().body(tag,Boolean.class);
    }
}
