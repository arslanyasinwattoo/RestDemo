package com.project.mein.controller;

import java.io.IOException;
import java.util.Arrays;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	public ResponseEntity<String> hello(
			@PathVariable("username") String username) {
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
		try {
			user = mapper.readValue(result.getBody(), User.class);
			user.setUsername(username);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.error("jsonmapping exception in api/import", "arslan");
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = restTemplate.exchange(uriRepo, HttpMethod.GET, entity,
				String.class);
		logger.debug(result.getBody());

		return result;
	}
}
