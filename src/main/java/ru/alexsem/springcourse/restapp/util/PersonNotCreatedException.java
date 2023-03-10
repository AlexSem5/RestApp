package ru.alexsem.springcourse.restapp.util;

public class PersonNotCreatedException extends RuntimeException  {
//    Будем выводить сообщение об исключении
    public PersonNotCreatedException(String message) {
        super(message);
    }
}
