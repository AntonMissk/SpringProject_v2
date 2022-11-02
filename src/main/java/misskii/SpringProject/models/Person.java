package misskii.SpringProject.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;

    @Column(name = "person_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters")
    private String name;
    @Column(name = "person_age")
    @Min(value = 0, message = "Age should be greater then 0")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    public Person(){}

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;

        this.age = age;
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

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

}
