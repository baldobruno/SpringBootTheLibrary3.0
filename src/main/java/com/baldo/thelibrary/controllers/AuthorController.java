package com.baldo.thelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baldo.thelibrary.domain.Author;
import com.baldo.thelibrary.services.AuthorService;

@RequestMapping("/author")
@Controller
public class AuthorController {
	private AuthorService authorService;

	@Autowired
	public void setAuthorService(AuthorService authorService) {
		this.authorService = authorService;
	}

	@RequestMapping({ "/list", "/" })
	public String listAuthors(Model model) {
		model.addAttribute("authors", authorService.listAll());
		return "author/list";
	}

	@RequestMapping("/show/{id}")
	public String showAuthor(@PathVariable Integer id, Model model) {
		model.addAttribute("author", authorService.getById(id));
		return "author/show";
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("author", authorService.getById(id));
		return "author/authorform";
	}

	@RequestMapping("/new")
	public String newAuthor(Model model) {
		model.addAttribute("author", new Author());
		return "author/authorform";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String saveOrUpdate(Author author) {
		Author newAuthor = authorService.saveOrUpdate(author);
		return "redirect:show/" + newAuthor.getId();
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		authorService.delete(id);
		return "redirect:/author/list";
	}
}
