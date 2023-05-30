package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Book;
import com.example.demo.repositories.IBookRepository;

@Service
public class BookService {
	//Agregando el book al repositorio como una dependencia
    private final IBookRepository bookRepository;
    
    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    //Devolviendo todos los libros.
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }
    //Creando un libro.
    public Book createBook(Book b) {
        return bookRepository.save(b);
    }
    //Obteniendo la información de un libro
    public Book findBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            return null;
        }
    }
    
    //modificar la información de un libro
    public Book updateBook(Long id,String title,String description, String language,Integer numberOfPages) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()) {
        	Book bookToUpdate = optionalBook.get();
			bookToUpdate.setTitle(title);
			bookToUpdate.setDescription(description);
			bookToUpdate.setLanguage(language);
			bookToUpdate.setNumberOfPages(numberOfPages);
			bookRepository.save(bookToUpdate);
            return bookToUpdate;
        } else {
            return null;
        }
    }
	// sobrecarga metodo
	public Book updateBook(Long id, String title, String desc, String lang) {
		Book optionalBook = bookRepository.findById(id).orElse(null);
		if (optionalBook != null) {
			optionalBook.setTitle(title);
			optionalBook.setDescription(desc);
			optionalBook.setLanguage(lang);
			bookRepository.save(optionalBook);
			return optionalBook;
		} else {
			return optionalBook;
		}
	}
	
	public Book updateBook(Book book) {
		Book actualizarLibro = findBook(book.getId());
		actualizarLibro.setTitle(book.getTitle());
		actualizarLibro.setLanguage(book.getLanguage());
		actualizarLibro.setDescription(book.getDescription());
		actualizarLibro.setNumberOfPages(book.getNumberOfPages());
		bookRepository.save(actualizarLibro);

		return actualizarLibro;
	
	}
    
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}
}
