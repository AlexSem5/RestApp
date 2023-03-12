package ru.alexsem.springcourse.restapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//Содержит в себе другие аннотации, например
//@Configuration
//@ComponentScan("ru.alexsem.springcourse")
//итд
public class RestAppApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RestAppApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
