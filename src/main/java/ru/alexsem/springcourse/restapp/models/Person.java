package ru.alexsem.springcourse.restapp.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Аннотацией @Entity помечаем класс, который связан с бд.
 * Класс с @Entity должен иметь пустой конструктор и поле с аннотацией @Id
 * <p>
 * Требования к Entity:
 * <p>
 * Все классы должны иметь ID для простой идентификации наших объектов в БД и в Hibernate.
 * Это поле класса соединяется с первичным ключём (primary key) таблицы БД.
 * Все классы должны иметь конструктор по умолчанию (пустой).
 * Все поля  – классов должны иметь модификатор доступа private,
 * иметь набор getter-ов и setter-ов в стиле JavaBean.
 * классы не должны содержать бизнес-логику.
 */

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Column(name = "name")
    private String name;
    
    //    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "age")
    @Min(value = 0, message = "Age should be grater than 0")
    private int age;
    
    @Column(name = "email")
    @Email
    @NotEmpty(message = "Name should not be empty")
    private String email;
    
    // Конструктор по умолчанию нужен для Spring
    public Person() {
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", email='" + email + '\'' +
               '}';
    }
}