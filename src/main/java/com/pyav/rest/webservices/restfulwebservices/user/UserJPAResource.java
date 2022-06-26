package com.pyav.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
public class UserJPAResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	Map<Integer, List<String>> userIdPostIdsMap = new HashMap<Integer, List<String>>();
	Map<String, String> postIdPostMap = new HashMap<String, String>();

	@GetMapping(path = "/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping(path = "/jpa/users/{id}")
	public EntityModel<User> getOneUser(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id - " + id + " not found");
		}

		// HATEOAS - Hypermedia As The Engine Of Application State
		//Resource<User> resource = new Resource<User> (user.get());
		EntityModel<User> resource = EntityModel.of(user.get());
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));

		return resource;
	}

	@PostMapping("/jpa/users")
	// @CrossOrigin(origins = "*")
	// @Bean(name="entityManagerFactory")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getPosts(@PathVariable int id) {
		/*if (service.findOne(id) == null) {
			throw new UserNotFoundException("id - " + id + " not found");
		}

		List<String> postIds = userIdPostIdsMap.get(id);
		List<String> posts = new ArrayList<String>();
		for (String i : postIds) {
			posts.add(postIdPostMap.get(i));
		}

		return posts;*/
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id: " + id);
		}
		return userOptional.get().getPosts();
	}

	@PostMapping("/jpa/users/{id}/posts")
	//public void addPost(@PathVariable Integer id, @RequestBody String post) {
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		/*if (service.findOne(id) == null) {
			throw new UserNotFoundException("id - " + id);
		}

		String uuid = UUID.randomUUID().toString();
		postIdPostMap.put(uuid, post);
		List<String> posts = userIdPostIdsMap.get(id);
		posts.add(uuid);
		userIdPostIdsMap.put(id, posts);*/
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id: " + id);
		}
		User user = userOptional.get();
		post.setUser(user);
		postRepository.save(post);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	/*@GetMapping("/jpa/users/{id}/posts/{post_id}")
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
	}*/

}
