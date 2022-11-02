package misskii.SpringProject.services;


import misskii.SpringProject.models.Book;
import misskii.SpringProject.models.Person;
import misskii.SpringProject.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findAllBooksWithPagination(int page, int booksPerPage, boolean sortByYear){
        if(sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findBookByID(int id ){
        Optional<Book> optionalBook = booksRepository.findById(id);
        return optionalBook.orElse(null);
    }
    @Transactional
    public void createNewBook(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void updateBook(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int id ){
        booksRepository.deleteById(id);
    }

    public Optional<Person> getBookOwner(int id){
        Optional<Book> book  = booksRepository.findById(id);
        return Optional.ofNullable(book.get().getOwner());
    }

    @Transactional
    public void assignBook(int id, Person person){
        Optional<Book> optionalBook = booksRepository.findById(id);
        optionalBook.ifPresent(book -> {book.setTaken_at(new Date());
        book.setOwner(person);});
    }

    @Transactional
    public void releaseBook(int id){
        Optional<Book> optionalBook = booksRepository.findById(id);
        optionalBook.ifPresent(book -> {book.setTaken_at(null);
        book.setOwner(null);});
    }

    public List<Book> findBookByName(String name){
        return booksRepository.findByNameStartingWith(name);
    }


}
