package ru.alexsem.springcourse.restapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.restapp.models.Person;
import ru.alexsem.springcourse.restapp.repositories.PeopleRepository;
import ru.alexsem.springcourse.restapp.util.PersonNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Любая работа с данными осуществляется в сервисном слое.
 * В сервисе должна быть бизнес-логика(здесь её нет). Например,
 * вызываются методы из разных репозиториев (внедрены несколько репозиториев)
 * и данные обрабатываются.
 * (транзакции создаются в сервисе)
 */

@Service
//Берёт на себя работу с транзакциями:
@Transactional(readOnly = true)
public class PeopleService {
    
    private final PeopleRepository peopleRepository;
    
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }
}
