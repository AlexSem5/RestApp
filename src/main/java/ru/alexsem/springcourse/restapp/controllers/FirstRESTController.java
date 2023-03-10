package ru.alexsem.springcourse.restapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller

@RestController //@Controller + @ResponseBody над каждым методом
@RequestMapping("/api")
public class FirstRESTController {
    
    //    Аннотация указывает, что в этом методе мы больше не
//    возвращаем представление, а возвращаем данные
//    (строка будет сконвертирована в JSON и её можно получить на клиенте)
//    @ResponseBody
    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world";
    }
}
