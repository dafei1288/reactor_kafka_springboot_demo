package wang.datahub.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wang.datahub.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserService {
    private Map<String, User> userMap =new ConcurrentHashMap<>();

    public void setUser(String userId,User user) {
        userMap.put(userId,user);
    }

    public Mono<User> findUserById(String userId){
        User user = userMap.getOrDefault(userId,new User("nick",18));
        return Mono.just(user);
    }

    public Flux<User> findUserList(){
//        List<User> userList = new ArrayList<>();
        Set<Map.Entry<String,User>> entries =userMap.entrySet();
//        entries.stream().forEach(entry->userList.add(entry.getValue()));
        List<User> userList = entries.stream().map(it->it.getValue()).collect(Collectors.toList());

        return   Flux.fromStream(()->userList.stream());
//         Flux.fromStream(()->IntStream.range(1,userMap.size()).mapToObj(i->{
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//            }
//            System.out.println("睡2s....");
//            return userMap.get(i+"");
//        }));
    }

}
