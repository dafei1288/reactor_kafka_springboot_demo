package wang.datahub.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wang.datahub.dto.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
public class TestController {

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
        }
        return "index";
    }

    // 普通的SpringMVC方法
    @GetMapping("/1")
    private String get1() {
        System.out.println("get1 start");
        String result = createStr();
        System.out.println("get1 end.");
        return result;
    }

    // WebFlux(返回的是Mono)
    @GetMapping("/2")
    private Mono<String> get2() {
        System.out.println("get2 start");
        Mono<String> result = Mono.fromSupplier(() -> createStr());
        System.out.println("get2 end.");
        return result;
    }

    @GetMapping(value = "/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<User> flux() {
        //http://localhost:8080/api/user/2
        WebClient client = WebClient.create("http://localhost:8080/");


        Flux<User> users = client.get().uri("/api/users").retrieve()
                .bodyToFlux(User.class);


//        Flux<String> result = Flux
//                .fromStream(IntStream.range(1, 10).mapToObj(i -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                    }
//                    return "flux data--" + i;
//                }));
//        System.out.println(result.collectList());
        return users;
    }



    @GetMapping(value="/test")
    public void getTest(){
        WebClient client = WebClient.create("http://localhost:8080/");
        int count = 999999;
        long now = System.currentTimeMillis();
        for(int i = 0; i < count ; i++){
            Flux<String> str = client.get().uri("/1").retrieve().bodyToFlux(String.class);
        }
        System.out.println("SpringMVC spend : "+(System.currentTimeMillis() - now));


        now = System.currentTimeMillis();
        for(int i = 0; i < count ; i++){
            Flux<String> str = client.get().uri("/2").retrieve().bodyToFlux(String.class);
        }
        System.out.println("flux spend : "+(System.currentTimeMillis() - now));
    }

    @GetMapping(value="/test1")
    public void getTest1(){
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2000);


        for(int i = 0; i < 999999 ; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    WebClient client = WebClient.create("http://localhost:8080/");
                    Flux<String> str = client.get().uri("/1").retrieve().bodyToFlux(String.class);

    //                System.out.println("SID: " + Thread.currentThread().getId() );
                }
            });
        }

        executorService.shutdown();
        while (true) {//等待所有任务都执行结束
            if (executorService.isTerminated()) {//所有的子线程都结束了
                System.out.println("SpringMVC spend: "+(System.currentTimeMillis()-startTime)+" ms");
                break;
            }
        }

    }



    @GetMapping(value="/test2")
    public void getTest2(){
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2000);


        for(int i = 0; i < 999999 ; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    WebClient client = WebClient.create("http://localhost:8080/");
                    Flux<String> str = client.get().uri("/2").retrieve().bodyToFlux(String.class);
//                    System.out.println("SID: " + Thread.currentThread().getId() );
                }
            });
        }

        executorService.shutdown();
        while (true) {//等待所有任务都执行结束
            if (executorService.isTerminated()) {//所有的子线程都结束了
                System.out.println("flux  spent : "+(System.currentTimeMillis()-startTime) +" ms");
                break;
            }
        }

    }
}
