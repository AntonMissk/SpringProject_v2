package misskii.SpringProject.controllers;


import misskii.SpringProject.models.Book;
import misskii.SpringProject.models.Person;
import misskii.SpringProject.services.BooksService;
import misskii.SpringProject.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
@Controller
@RequestMapping("/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(required = false, name = "page")Integer page,
                        @RequestParam(required = false, name = "books_per_page")Integer books_per_page,
                        @RequestParam(required = false, name = "sort_by_year")boolean sort){

        if(page != null && books_per_page != null){
            model.addAttribute("books", booksService.findAllBooksWithPagination(page, books_per_page, sort));
        }else {
            model.addAttribute("books", booksService.findAll(sort));
        }

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findBookByID(id));
        Optional<Person> bookOwner = booksService.getBookOwner(id);
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people",peopleService.findAllPeople());
        return "books/show";
    }



    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book")@Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "books/new";
        booksService.createNewBook(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findBookByID(id));
        return "books/edit";
    }

    @PatchMapping ("/{id}")
    public String update(@ModelAttribute("book")Book book,
                         BindingResult bindingResult, @PathVariable("id")int id){
        if(bindingResult.hasErrors()) return "books/edit";
        booksService.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        booksService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")int id){
        booksService.releaseBook(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person")Person person, @PathVariable("id") int id){
        booksService.assignBook(id, person);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("query")String name){
        model.addAttribute("books",booksService.findBookByName(name));
        return "/books/search";
    }
}
