package com.pyav.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	Map<Integer, List<String>> userIdPostIdsMap = new HashMap<Integer, List<String>>();
	Map<String, String> postIdPostMap = new HashMap<String, String>();

	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return service.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public EntityModel<User> getOneUser(@PathVariable Integer id) {
		User user = service.findOne(id);
		if (null == user) {
			throw new UserNotFoundException("id - " + id + " not found");
		}

		// HATEOAS - Hypermedia As The Engine Of Application State
		// Resource<User> resource = new Resource<User> (user);
		EntityModel<User> resource = EntityModel.of(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));

		return resource;
	}

	@PostMapping("/users")
	// @CrossOrigin(origins = "*")
	// @Bean(name="entityManagerFactory")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		System.out.println("Anand POST /users");
		User savedUser = service.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		User user = service.deleteById(id);
		if (null == user) {
			throw new UserNotFoundException("id - " + id);
		}
	}

	@GetMapping("/users/{id}/posts")
	public List<String> getPosts(@PathVariable Integer id) {
		if (service.findOne(id) == null) {
			throw new UserNotFoundException("id - " + id + " not found");
		}

		List<String> postIds = userIdPostIdsMap.get(id);
		List<String> posts = new ArrayList<String>();
		for (String i : postIds) {
			posts.add(postIdPostMap.get(i));
		}

		return posts;
	}

	@PostMapping("/users/{id}/posts")
	public void addPost(@PathVariable Integer id, @RequestBody String post) {
		if (service.findOne(id) == null) {
			throw new UserNotFoundException("id - " + id);
		}

		String uuid = UUID.randomUUID().toString();
		postIdPostMap.put(uuid, post);
		List<String> posts = userIdPostIdsMap.get(id);
		posts.add(uuid);
		userIdPostIdsMap.put(id, posts);
	}

	@GetMapping("/users/{id}/posts/{post_id}")
	public String getPost(@PathVariable Integer id, @PathVariable Integer postId) {
		if (service.findOne(id) == null) {
			throw new UserNotFoundException("id - " + id + " not found");
		}
		List<String> postsForUser = userIdPostIdsMap.get(id);
		if (!postsForUser.contains(String.valueOf(postId))) {
			throw new PostNotFoundException("Post id - " + postId + " not found");
		}

		if (!postIdPostMap.containsKey(String.valueOf(postId))) {
			throw new PostNotFoundException("Post id - " + postId + " not found");
		}

		return postIdPostMap.get(String.valueOf(postId));
	}

}
