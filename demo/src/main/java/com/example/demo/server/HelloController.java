package com.example.demo.server;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String healthCheck() {
        return "health check OK !";
    }

    @GetMapping("/hello")
    public String hello() {
        User user = new User("twoosky");
        User newUser = userRepository.save(user);

        return "Id : " + newUser.getId() + " Name : " + newUser.getName();
    }
}
