package com.pyav.rest.webservices.restfulwebservices.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return service.findAll();
	}

	@GetMapping(path = "/user/{id}")
	public User getOneUser(@PathVariable Integer id) {
		return service.findOne(id);
	}

	@PostMapping("/users")
	public void createUser(@RequestBody User user) {
		service.save(user);
	}
}
