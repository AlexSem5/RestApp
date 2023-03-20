package ru.alexsem.springcourse.restapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexsem.springcourse.restapp.models.Person;

import java.util.List;
import java.util.Optional;

/**
 *
 * Data access layer
 *
 * Указываем класс Person и тип id (Integer).
 * Тип Id используется в методах репозитория.
 *
 *
 *  Репозиторий - для стандартных операций с данными (CRUD, например) -
 *  он более высокоуровневый. Работает с сущностями Entity.
 *  DAO - для более сложных манипуляций с данными и БД, где нужно
 *  вручную писать SQL/HQL, нестандартные запросы.
 *
 *  Ссылка на документацию по Spring Data JPA:
 *  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
 *
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

//    Может возвращать и List<Person> тоже (см документацию)
//    После By пишем имя поля
    Optional<Person> findByEmail(String email);
}
