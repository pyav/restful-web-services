package com.pyav.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	Map<Integer, List<Integer>> userIdPostIdsMap = new HashMap<Integer, List<Integer>>();
	Map<Integer, String> postIdPostMap = new HashMap<Integer, String>();

	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return service.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public User getOneUser(@PathVariable Integer id) {
		User user = service.findOne(id);
		if (null == user) {
			throw new UserNotFoundException("id - " + id + " not found");
		}
		return user;
	}

	@PostMapping("/users")
	// @CrossOrigin(origins = "*")
	// @Bean(name="entityManagerFactory")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		System.out.println("Anand POST /users");
		User savedUser = service.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	// GET /users/{id}/posts
	// POST /users/{id}/posts
	// GET /users/{id}/posts/{post_id}

	@GetMapping("/users/{id}/posts")
	public List<String> getPosts(@PathVariable Integer id) {
		if (service.findOne(id) == null) {
			throw new UserNotFoundException("id - " + id + " not found");
		}

		List<Integer> postIds = userIdPostIdsMap.get(id);
		
	}

	@PostMapping("/users/{id}/posts")
	public void addPost(@PathVariable Integer id, @RequestBody String post) {
		if (service.findOne(id) == null) {
			throw new UserNotFoundException("id - " + id + " not found");
		}

		List<String> posts = userPostsMap.get(id);
		posts.add(post);
		userPostsMap.put(id, posts);
	}

	@GetMapping("/users/{id}/posts/{post_id}")
	public String getPost(@PathVariable Integer id, @PathVariable Integer postId) {
		if () {
			
		}
	}

}
