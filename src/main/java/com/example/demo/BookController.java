package com.example.demo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.datafaker.Faker;

@RestController
@RequestMapping("/api/books")
public class BookController {
	private final Map<Long, Book> books = new ConcurrentHashMap<>();
	private final AtomicLong nextid = new AtomicLong(1);

	public BookController() {
		Faker faker = new Faker();
		for (int i = 0; i < 10; i++) {
			Long id = nextid.getAndIncrement();
			books.put(id, new Book(id, faker.book().title(), faker.book().author()));
		}
	}

	@GetMapping
	public List<Book> getAll() {
		return new ArrayList<>(books.values());
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody Book input) {
		Long id = nextid.getAndIncrement();
		Book saved = new Book(id, input.title(), input.author());
		books.put(id, saved);
		return ResponseEntity.ok("CCCCC");
	}
}
