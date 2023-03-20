package ru.alexsem.springcourse.restapp.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * В классе описываем те поля, которые
 * будут приходить от клиента и которые мы будем
 * ему отправлять.
 * Класс не связан с базой данных (он не Entity), поэтому убираем аннотации @Column.
 *
 * Модель - бизнес-логика
 * DTO - объект для передачи данных
 *
 * DTO ВСЕГДА ИСПОЛЬЗУЕТСЯ НА УРОВНЕ КОНТРОЛЛЕРА
 * Jackson использует геттеры и сеттеры, когда он преобразовывает JSON
 * в объект и наоборот
 */
public class PersonDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;
    
    //    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Min(value = 0, message = "Age should be grater than 0")
    private int age;
    
    @Email
    @NotEmpty(message = "Name should not be empty")
    private String email;
    
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
}
