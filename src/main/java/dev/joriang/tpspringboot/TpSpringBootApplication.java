package dev.joriang.tpspringboot;

import dev.joriang.tpspringboot.entities.Roles;
import dev.joriang.tpspringboot.entities.User;
import dev.joriang.tpspringboot.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TpSpringBootApplication {


    public static void main(String[] args) {
        SpringApplication.run(TpSpringBootApplication.class, args);
    }

    @GetMapping("/bonjour")
    public String bonjour() {
        return "Hello World !";
    }

}
