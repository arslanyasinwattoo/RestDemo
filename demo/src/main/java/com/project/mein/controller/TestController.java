package com.project.mein.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mein.entity.Languages;
import com.project.mein.entity.Repository;
import com.project.mein.entity.User;
import com.project.mein.service.userService;

@Controller
public class TestController {

	@Autowired
	private userService userService;
	private static final Logger logger = LoggerFactory
			.getLogger(TestController.class);

	@RequestMapping(value = "/api/import/{username}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<Object> hello(
			@PathVariable("username") String username) throws Exception {
		logger.info("calling:/api/hello");
		logger.debug("welcome() is executed, value {}", "arslan");

		logger.error("This is Error message", new Exception("Testing"));

		// return new ResponseEntity<String>("testing 123", HttpStatus.OK);
		final String uri = "https://api.github.com/users/" + username;
		final String uriRepo = "https://api.github.com/users/" + username
				+ "/repos";
		// for sending and receiving json requests
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);
		ResponseEntity<String> result = restTemplate.exchange(uri,
				HttpMethod.GET, entity, String.class);
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		List<Repository> repository = new ArrayList<>();
		// List<Languages> languages = new ArrayList<>();
		// List<Repository> repository = new ArrayList<Repository>();

		user = mapper.readValue(result.getBody(), User.class);
		user.setUsername(username);
		User user2 = userService.getUsersByName(user);
		if (user2.getUserId() != null) {
			user.setUserId(user2.getUserId());
			System.out.println("user id testing advance" + user.getUserId());
		}
		userService.addUser(user);
		user = userService.getUsersByName(user);
		result = restTemplate.exchange(uriRepo, HttpMethod.GET, entity,
				String.class);
		repository = mapper.readValue(result.getBody(),
				new TypeReference<List<Repository>>() {
				});
		for (Repository repository2 : repository) {
			repository2.setUser(user);
			System.out.println(repository2.getName());
			String uriLang = "https://api.github.com/repos/"
					+ repository2.getUser().getUsername() + "/"
					+ repository2.getName() + "/languages";
			result = restTemplate.exchange(uriLang, HttpMethod.GET, entity,
					String.class);
			Repository repository3 = userService
					.getRepositoryByUrl(repository2);
			if (repository3.getRepository_Id() != null) {
				repository2.setRepository_Id(repository3.getRepository_Id());
			}
			String description = repository2.getDescription();

			if (description != null) {
				description = description.length() > 250 ? description
						.substring(0, 249) : description;
			}
			repository2.setDescription(description);
			userService.addRepository(repository2);
			repository2 = userService.getRepositoryByUrl(repository2);
			System.out.println(repository2.getUrl());
			JsonFactory factory = new JsonFactory();

			mapper = new ObjectMapper(factory);
			JsonNode rootNode = mapper.readTree(result.getBody());

			Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode
					.fields();
			while (fieldsIterator.hasNext()) {
				Languages languages2 = new Languages();
				Languages languages = new Languages();
				languages2.setRepository(repository2);
				if (repository2.getRepository_Id() != null) {

					languages.setRepository(repository2);
					languages = userService.getLanguageByRepoId(languages);

				}
				Map.Entry<String, JsonNode> field = fieldsIterator.next();
				System.out.println("Key: " + field.getKey() + "\tValue:"
						+ field.getValue());
				languages2.setName(field.getKey());
				languages2.setNumber(field.getValue().asInt());
				if (languages.getLanguagesId() != null
						&& languages.getName().equals(languages2.getName())) {
					languages2.setLanguagesId(languages.getLanguagesId());
				}

				userService.addLanguages(languages2);
			}
		}

		logger.debug(result.getBody());
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
