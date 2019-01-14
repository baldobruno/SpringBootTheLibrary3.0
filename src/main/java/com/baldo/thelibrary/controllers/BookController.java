package com.baldo.thelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baldo.thelibrary.domain.Book;
import com.baldo.thelibrary.services.BookService;

@RequestMapping("/book")
@Controller
public class BookController {

	private BookService bookService;

	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping({ "/list", "/" })
	public String listBooks(Model model) {
		model.addAttribute("books", bookService.listAll());
		return "book/list";
	}

	@RequestMapping("/show/{id}")
	public String showBook(@PathVariable Integer id, Model model) {
		model.addAttribute("book", bookService.getById(id));
		return "book/show";
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("book", bookService.getById(id));
		return "book/bookform";
	}

	@RequestMapping("/new")
	public String newBook(Model model) {
		model.addAttribute("book", new Book());
		return "book/bookform";
	}

    @RequestMapping(value = "/", method = RequestMethod.POST)
	public String saveOrUpdate(Book book) {
		Book newBook = bookService.saveOrUpdate(book);
		return "redirect:show/" + newBook.getId();
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		bookService.delete(id);
		return "redirect:/book/list";
	}
}
