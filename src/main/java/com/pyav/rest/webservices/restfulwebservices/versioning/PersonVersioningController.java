package com.pyav.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	@GetMapping("v1/person")
	public PersonV1 personV1() {
		return new PersonV1("Anand pyav");
	}

	@GetMapping("v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Anand", "pyav"));
	}
	
	@GetMapping(value="/person/param", params="version=1")
	public PersonV1 paramV1() {
		return new PersonV1("Anand pyav");
	}

	@GetMapping(value="/person/param", params = "version=2")
	public PersonV2 paramV2() {
		return new PersonV2(new Name("Anand", "pyav"));
	}
}