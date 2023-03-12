package ru.alexsem.springcourse.restapp.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Аннотацией @Entity помечаем класс, который связан с бд. Hibernate воспринимает этот класс
 * как СУЩНОСТЬ
 *
 * Класс с @Entity должен иметь пустой конструктор и поле с аннотацией @Id.
 * Класс с пустым конструктором соответствует требованиям спецификации JPA для Entity
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
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Name should not be empty")
    private String email;
    
    /**
     * три поля ниже берутся не от клиента, а назначаются сервером
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "created_who")
    private String createdWho;
    
    
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getCreatedWho() {
        return createdWho;
    }
    
    public void setCreatedWho(String createdWho) {
        this.createdWho = createdWho;
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