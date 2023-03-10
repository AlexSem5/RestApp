package ru.alexsem.springcourse.restapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexsem.springcourse.restapp.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
