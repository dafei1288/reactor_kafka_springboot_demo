package wang.datahub.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import wang.datahub.handler.UserHandler;
import wang.datahub.handler.WarehouseHandler;

//@Configuration
@Deprecated
public class WarehouseRoutes {
//    @Bean
//    @Autowired
//    public RouterFunction<ServerResponse> routersFunction(WarehouseHandler warehouseHandler){
//        return RouterFunctions
//                .route(RequestPredicates.GET("/api/warehouse"),warehouseHandler::addWarehouse);
//    }
}
