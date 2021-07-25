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

@Configuration
public class Routes {

    @Autowired
    UserHandler userHandler;

    @Autowired
    WarehouseHandler warehouseHandler;

    @Bean
    public RouterFunction<ServerResponse> routersFunction(){
        return RouterFunctions
                .route(RequestPredicates.GET("/api/users"),userHandler::getUserList)
                .and(RouterFunctions.route(RequestPredicates.GET("/api/user/{userId}"),userHandler::getUser))
                .andRoute(RequestPredicates.GET("/api/warehouse"),warehouseHandler::addWarehouse);
    }
}
