package ru.alexsem.springcourse.restapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alexsem.springcourse.restapp.dto.PersonDTO;
import ru.alexsem.springcourse.restapp.models.Person;
import ru.alexsem.springcourse.restapp.services.PeopleService;
import ru.alexsem.springcourse.restapp.util.PersonErrorResponse;
import ru.alexsem.springcourse.restapp.util.PersonNotCreatedException;
import ru.alexsem.springcourse.restapp.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Аннотация указывает, что в этом методе мы больше не
 * возвращаем представление, а возвращаем данные
 * (строка будет сконвертирована в JSON и её можно получить на клиенте)
 *
 * @ResponseBody - отдаёт Java объекты клиенту и конвертирует их в JSON с помощью Jackson
 * @RequestBody - принимает JSON от клиента (например Postman) и конвертирует его в Java объекты с помощью Jackson
 *
 * DTO ВСЕГДА ИСПОЛЬЗУЕТСЯ НА УРОВНЕ КОНТРОЛЛЕРА. Если раньше принимали модель от клиента,
 * то теперь DTO:
 * 1) Для запросов принимается в методе контроллера и
 * конвертируется в объект модели.
 * 2) Для ответов конвертируется из объекта модели и
 * отправляется клиенту
 */
@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/people")
public class PeopleController {
    
    private final PeopleService peopleService;
    
//    Создаём Bean в конфиг файле и внедряем с помощью Spring:
    private final ModelMapper modelMapper;
    
    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }
    /**
     * Отправляем клиенту список PersonDTO
     *
     * @param
     * @return
     */
    @GetMapping()
    public List<PersonDTO> getPeople() {
        return peopleService.findAll().stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList()); // Jackson конвертирует эти объекты в JSON
    }
    
    /**
     * Отправляем клиенту PersonDTO
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id)); // Jackson конвертирует в JSON
    }
    
    /**
     * Метод для создания нового человека
     *
     * Метод принимает DTO и конвертирует в модель.
     *
     * ResponseEntity<HttpStatus> - http-ответ
     *
     * @RequestBody - принимает JSON от клиента и конвертирует его в Java объекты с помощью Jackson
     * @Valid - Проверяет данные, которые приходят от клиента в формате JSON на соответствие
     * условиям (аннотациям) в классе Person. Если появляется ошибка, то её кладём в bindingResult.
     * (Проверку осуществляет Hibernate Validator)
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
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
//        конвертируем DTO в модель и сохраняем в БД
        peopleService.save(convertToPerson(personDTO));
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
    
    /**
     * Принимает DTO и возвращает модель
     *
     * Импортируем библиотеку для "маппинга" между DTO и моделью вместо того, чтобы делать вручную.
     * Создаём Bean в конфиг файле и внедряем с помощью Spring
     * @param personDTO
     * @return
     */
    private Person convertToPerson(PersonDTO personDTO) {
        //        Person person = new Person();
//        person.setName(personDTO.getName());
//        person.setAge(personDTO.getAge());
//        person.setEmail(personDTO.getEmail());

//        Метод, в котором доп данные назначаются на самом сервере - прописываем в сервисе
//        enrichPerson(person);
    
        Person person = modelMapper.map(personDTO, Person.class);
        return person;
    }
    
    /**
     * Принимает модель и возвращает DTO
     * @param person
     * @return
     */
    private PersonDTO convertToPersonDTO(Person person) {
        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        return personDTO;
    }
    
    
}