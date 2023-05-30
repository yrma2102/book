package com.example.demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.models.Book;
import com.example.demo.services.BookService;

import jakarta.validation.Valid;


@Controller
public class BooksControllers {
	private final BookService bookService;
    
    public BooksControllers(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/")
	public String raiz() {
		return "redirect:/books";
	}
    
    @RequestMapping("/books")
    public String index(Model model) {
        List<Book> books = bookService.allBooks();
        model.addAttribute("libros", books);
        return "index.jsp";
    }
    @GetMapping("/books/new")
	public String nuevoLibro(@ModelAttribute("book") Book book) {
		return "nuevo.jsp";
	}

	@PostMapping("/books/new")
	public String crearLibro(@Valid @ModelAttribute("book") Book book, BindingResult resultado) {
		if (resultado.hasErrors()) {
	        bookService.createBook(book);
			return "nuevo.jsp";
		} else {

			bookService.createBook(book);
			return "redirect:/books";

		}
	}

	@GetMapping("/books/{bookId}")
	public String mostrarLibro(Model viewModel, @PathVariable("bookId") Long bookId) {
		viewModel.addAttribute("book", bookService.findBook(bookId));
		return "mostrar.jsp";
	}

	@GetMapping("/books/edit/{bookId}")
	public String formualrioEdicion(@ModelAttribute("book") Book libro, 
			@PathVariable("bookId") Long bookId,
			Model viewModel) {
		Book unlibro = bookService.findBook(bookId);
		viewModel.addAttribute("unlibro", unlibro);
		return "editar.jsp";
	}

	@PutMapping("/books/edit/{bookId}")
	public String editarLibro(@ModelAttribute("book") Book libro, 
			@PathVariable("bookId") Long bookId, Model viewModel,
			BindingResult resultado) {
		if (resultado.hasErrors()) {
			return "editar.jsp";
		} else {
			bookService.updateBook(libro);
			return "redirect:/books";

		}
		
	}
	
	@RequestMapping(value="/books/delete/{bookId}", method=RequestMethod.DELETE)
	public String eliminarLibro(@PathVariable("bookId") Long bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/books";
	}

}
