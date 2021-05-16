package com.pyav.rest.webservices.restfulwebservices.user;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<User>();
	private static int usersCount = 3;

	static {
		users.add(new User(1, "pyav", new Date()));
		users.add(new User(2, "just-another-user1", new Date()));
		users.add(new User(3, "anand", new Date()));
	}

	public List<User> findAll() {
		return users;
	}

	public User findOne(int id) {
		for (User user : users) {
			if (id == user.getId()) {
				return user;
			}
		}
		return null;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}

}
