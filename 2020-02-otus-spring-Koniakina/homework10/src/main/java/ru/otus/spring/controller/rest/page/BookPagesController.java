package ru.otus.spring.controller.rest.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.service.BookService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookPagesController {

    private final BookService bookService;

    @GetMapping({"/", "/book"})
    public String listPage() {
        return "index";
    }

    @GetMapping("/book/{id}")
    public String editPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("book", bookService.findBookById(id).get());
        return "edit";
    }

    @GetMapping("/book/add")
    public String createPage(Model model) {
        return "create";
    }
}