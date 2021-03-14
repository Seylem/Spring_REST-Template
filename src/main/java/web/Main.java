package web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.model.User;

import java.util.List;

@Service
public class Main {
    private RestTemplate restTemplate = new RestTemplate();
    private Byte age = 5;
    private String URL = "http://91.241.64.178:7081/api/users";
    private HttpHeaders httpHeaders = new HttpHeaders();
    private StringBuilder str = new StringBuilder();

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {
        getAllUsers();
        addUser();
        //getAllUsers2();
        putUser();
        //getAllUsers2();
        deleteUser();
    }

    public ResponseEntity<String> getAllUsers() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
        httpHeaders.set("Cookie", responseEntity.getHeaders().get("Set-Cookie").get(0));
        System.out.println("All users: " + responseEntity.getBody());
        System.out.println("========");
        return responseEntity;
    }

    public ResponseEntity<String> getAllUsers2() {
        ResponseEntity<String> userResponseEntity = restTemplate.getForEntity(URL, String.class);
        System.out.println("All users: " + userResponseEntity.getBody());
        System.out.println("==========================================");
        return userResponseEntity;
    }

//    private ResponseEntity<String> addUser() {
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
//        httpHeaders.set("Cookie", responseEntity.getHeaders().get("Set-Cookie").get(0));
//        HttpEntity<User> requestPost = new HttpEntity<>(new User(3L, "James", "Brown", age), httpHeaders);
//        ResponseEntity<String> userResponseEntity = restTemplate
//                .exchange(URL, HttpMethod.POST, requestPost, String.class);
//        System.out.println("Code 1: " + userResponseEntity.getBody());
//        System.out.println("========");
//        str = str.append(userResponseEntity.getBody());
//        return userResponseEntity;
//    }
    private void addUser() {
//        ResponseEntity responseEntity = restTemplate.getForEntity(URL, String.class);
//        httpHeaders.set("Cookie", responseEntity.getHeaders().get("Set-Cookie").get(0));
        HttpEntity<User> requestPost = new HttpEntity<>(new User(3L, "James", "Brown", age), httpHeaders);
        ResponseEntity userResponseEntity = restTemplate
                .exchange(URL, HttpMethod.POST, requestPost, String.class);
        System.out.println("Code 1: " + userResponseEntity.getBody());
        System.out.println("========");
        str = str.append(userResponseEntity.getBody());
    }

    private ResponseEntity<String> putUser() {
        HttpEntity<User> requestPut = new HttpEntity<>(new User(3L, "Thomas", "Shelby", age), httpHeaders);
        ResponseEntity<String> userResponseEntity = restTemplate
                .exchange(URL, HttpMethod.PUT, requestPut, String.class);
        System.out.println("Code 2: " + userResponseEntity.getBody());
        System.out.println("========");
        str = str.append(userResponseEntity.getBody());
        return userResponseEntity;
    }

    private ResponseEntity deleteUser() {
        HttpEntity<User> requestDel = new HttpEntity<>(new User(3L), httpHeaders);
        ResponseEntity<String> userResponseEntity = restTemplate
                .exchange(URL + "/3", HttpMethod.DELETE, requestDel, String.class);
        System.out.println("Code 3: " + userResponseEntity.getBody());
        System.out.println("========");
        str = str.append(userResponseEntity.getBody());
        System.out.println("All code: " + str);
        return userResponseEntity;
    }
}
