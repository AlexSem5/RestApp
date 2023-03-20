package ru.alexsem.springcourse.restapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * By default @Autowired searches by type.
 * We can also use @Qualifier to search by name:
 *
 * @Component("some name")
 * @Scope("prototype")
 *
 * @Autowired
 * @Qualifier("some name")
 *
 *
 *
 */

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
