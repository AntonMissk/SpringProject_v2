package misskii.SpringProject.controllers;


import misskii.SpringProject.models.Book;
import misskii.SpringProject.models.Person;
import misskii.SpringProject.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAllPeople());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        List<Book> bookList = peopleService.personHasBook(id);
        model.addAttribute("person", peopleService.findByID(id));
        model.addAttribute("books", bookList);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person" , new Person());
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person")@Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "people/new";
        peopleService.addNewPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findByID(id));
        return "people/edit";
    }

    @PatchMapping ("/{id}")
    public String update(@ModelAttribute("person")@Valid Person person,
                         BindingResult bindingResult, @RequestParam()int id){
        if(bindingResult.hasErrors()) return "people/edit";
        peopleService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}
