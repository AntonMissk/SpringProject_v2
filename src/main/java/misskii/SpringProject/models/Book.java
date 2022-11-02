package misskii.SpringProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;
    @Column(name = "book_name")
    @NotEmpty(message = "Title should not be empty")
    @Size(min=2, max=30, message = "Title should be between 2 and 30 characters")
    private String name;
    @Column(name = "book_author")
    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters")
    private String author;
    @Column(name="year")
    private int year;
    @Column(name="taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taken_at;

    @Transient
    private boolean overdueBook;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Book(){}

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Person getOwner() {
        return owner;
    }
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year_of_publishing) {
        this.year = year_of_publishing;
    }

    public Date getTaken_at() {
        return taken_at;
    }

    public void setTaken_at(Date taken_at) {
        this.taken_at = taken_at;
    }

    public boolean isOverdueBook() {
        return overdueBook;
    }

    public void setOverdueBook(boolean overdueBook) {
        this.overdueBook = overdueBook;
    }
}
