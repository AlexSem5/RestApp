package ru.alexsem.springcourse.restapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexsem.springcourse.restapp.models.Person;

/**
 * Указываем класс Person и тип id (Integer).
 * Тип Id используется в методах репозитория.
 *
 *
 *  Репозиторий - для стандартных операций с данными (CRUD, например) -
 *  он более высокоуровневый. Работает с сущностями Entity.
 *  DAO - для более сложных манипуляций с данными и БД, где нужно
 *  вручную писать SQL/HQL, нестандартные запросы.
 *
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
