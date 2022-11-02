package misskii.SpringProject.services;

import misskii.SpringProject.models.Book;
import misskii.SpringProject.models.Person;
import misskii.SpringProject.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
       this.peopleRepository = peopleRepository;
    }

    public List<Person> findAllPeople(){
         return peopleRepository.findAll();
    }
    
    public Person findByID(int id){
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        return optionalPerson.orElse(null);
    }
    @Transactional
    public void addNewPerson(Person person){
         peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson){
         updatedPerson.setId(id);
         peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void deletePerson(int id){
         peopleRepository.deleteById(id);
    }

    public List<Book> personHasBook(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBookList());
            person.get().getBookList().forEach(book -> {long diffInMillies = Math.abs(book.getTaken_at().getTime() - new Date().getTime());
                book.setOverdueBook(diffInMillies > 10000);
            });

        }
        else Collections.emptyList();
        return person.get().getBookList();
    }


}
