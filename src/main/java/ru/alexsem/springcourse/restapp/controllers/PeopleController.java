package ru.alexsem.springcourse.restapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alexsem.springcourse.restapp.models.Person;
import ru.alexsem.springcourse.restapp.services.PeopleService;
import ru.alexsem.springcourse.restapp.util.PersonErrorResponse;
import ru.alexsem.springcourse.restapp.util.PersonNotCreatedException;
import ru.alexsem.springcourse.restapp.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;

/**
 * Аннотация указывает, что в этом методе мы больше не
 * возвращаем представление, а возвращаем данные
 * (строка будет сконвертирована в JSON и её можно получить на клиенте)
 *
 * @ResponseBody - отдаёт Java объекты клиенту и конвертирует их в JSON с помощью Jackson
 * @RequestBody - принимает JSON от клиента и конвертирует его в Java объекты с помощью Jackson
 */
@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/people")
public class PeopleController {
    
    private final PeopleService peopleService;
    
    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    
    @GetMapping()
    public List<Person> getPeople() {
        return peopleService.findAll(); // Jackson конвертирует эти объекты в JSON
    }
    
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id); // Jackson конвертирует в JSON
    }
    
    /**
     * Метод для создания нового человека
     * ResponseEntity<HttpStatus> - http-ответ
     *
     * @RequestBody - принимает JSON от клиента и конвертирует его в Java объекты с помощью Jackson
     * @Valid - Проверяет данные, которые приходят от клиента в формате JSON на соответствие
     * условиям (аннотациям) в классе Person. Если появляется ошибка, то её кладём в bindingResult.
     * (Проверку осуществляет Hibernate Validator)
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMSG = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(fieldError -> errorMSG.append(fieldError.getField())
                                                 .append(" - ").append(fieldError.getDefaultMessage())
                                                 .append(";"));
//            В пакете util создаём новое исключение
            throw new PersonNotCreatedException(errorMSG.toString());
//            Ниже создаём метод для обработки данного исключения
        }
        peopleService.save(person);
//        Отправляем HTTP ответ клиенту с пустым телом и статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    /**
     * Метод ловит исключение из PeopleService (метод findOne())
     * и возвращает объект в виде json
     */
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException exception) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id was not found",
                System.currentTimeMillis()
        );
//        В http ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //NOT_FOUND - 404 статус
    }
    
    /**
     * Метод ловит исключение из @PostMapping public ResponseEntity<HttpStatus> create
     * и возвращает объект в виде json
     */
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException exception) {
        PersonErrorResponse response = new PersonErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
//        В http ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}