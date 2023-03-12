package ru.alexsem.springcourse.restapp.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alexsem.springcourse.restapp.dto.PersonDTO;
import ru.alexsem.springcourse.restapp.models.Person;
import ru.alexsem.springcourse.restapp.repositories.PeopleRepository;
import ru.alexsem.springcourse.restapp.services.PeopleService;

/**
 * Используем валидатор в контроллере в методе create.
 *
 * Нужно подумать, куда внедрить Modelmapper,
 * чтобы исключить дублирование
 */

@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;
    
    @Autowired
    public PersonValidator(PeopleRepository peopleRepository, ModelMapper modelMapper) {
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
    }
    
    
    /**
     * Нужно дать понять Spring, для какой сущности этот валидатор
     * предназначен
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return PersonDTO.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
//        Первая проверка: Посмотреть, есть ли человек с таким же email в БД
        PersonDTO personDTO = (PersonDTO) target;
        Person person = modelMapper.map(personDTO, Person.class);
        if (peopleRepository.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }
//        Вторая проверка: Проверяем, что у человека имя начинается с заглавной буквы
//        Если не с заглавной, то добвляем ошибку
        if (!Character.isUpperCase(person.getName()
                                         .codePointAt(0))) {
            errors.rejectValue(
                    "name", "", "Name should start with a capital letter");
        }
    }
}