package ru.alexsem.springcourse.restapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.restapp.models.Person;
import ru.alexsem.springcourse.restapp.repositories.PeopleRepository;
import ru.alexsem.springcourse.restapp.util.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service (Business Layer)
 * <p>
 * Любая работа с данными осуществляется в сервисном слое.
 * В сервисе должна быть бизнес-логика(здесь её нет). Например,
 * вызываются методы из разных репозиториев (внедрены несколько репозиториев)
 * и данные обрабатываются.
 * (транзакции создаются в сервисе)
 * <p>
 * В сервисе можно внедрять и DAO, и репозитории -
 * см PeopleService из (SpringHibernateApp)
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
    
    /**
     * В репозитории мы указали, что Id имеет тип Integer,
     * поэтому можем использовать метод findById(id)
     *
     * @param id
     * @return
     */
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }
    
    /**
     * В репозитории есть соглашение, что для добавления
     * и обновления (update) сущности используется один и тот же метод save(person) -
     * см SpringHibernateApp и ProjectBoot
     */
    @Transactional
    public void save(Person person) {
        enrichPerson(person);
        peopleRepository.save(person);
    }
    
    @Transactional
    public void update(int id, Person updatedPerson) {
        boolean exists = peopleRepository.existsById(id);
        if (!exists) {
            throw new PersonNotFoundException();
        }
//        Person person = peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        updatedPerson.setId(id);
        enrichPerson(updatedPerson);
//        Обновит значения у существующего человека (по id найдёт его):
        peopleRepository.save(updatedPerson);
    }
    
    @Transactional
    public void delete(int id) {
        boolean exists = peopleRepository.existsById(id);
        if (!exists) {
            throw new PersonNotFoundException();
        }
        peopleRepository.deleteById(id);
    }
    
    /**
     * Метод, в котором доп данные назначаются на самом сервере
     *
     * @param person
     */
    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
//        в данном простом случае используем просто Admin
//       можно прописать логику, кто создал (Spring Security)
        person.setCreatedWho("Admin");
    }
}
