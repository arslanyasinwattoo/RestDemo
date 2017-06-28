package com.project.mein.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.mein.entity.Languages;
import com.project.mein.entity.Repository;
import com.project.mein.entity.User;
import com.project.mein.service.userService;

@Controller
public class ShowController {
	@Autowired
	private userService userService;
	private static final Logger logger = LoggerFactory
			.getLogger(ShowController.class);

	@RequestMapping(value = "/api/show/users", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<User> showUsers() throws Exception {
		logger.info("calling:/api/hello");
		logger.debug("welcome() is executed, value {}", "arslan");

		logger.error("This is Error message", new Exception("Testing"));
		System.out.println("here i am");
		List<User> list = userService.getAllUser();

		return list;
	}

	@RequestMapping(value = "/api/show/{id}/repos", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<Repository> showUserRepos(@PathVariable("id") Integer id)
			throws Exception {

		List<Repository> list = userService.getRepositoryByUserId(id);

		return list;
	}

	@RequestMapping(value = "/api/show/{id}/lang", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<Languages> showReposLang(@PathVariable("id") Integer id)
			throws Exception {

		List<Languages> list = userService.getLanguageByRepoId(id);
		double total = 0;
		for (Languages languages : list) {
			total = total + languages.getNumber();
			System.out.println(total);
		}
		for (Languages languages : list) {
			// System.out.println(languages.getNumber());
			// System.out.println(total);
			String sum = new DecimalFormat("##.####").format(languages
					.getNumber() / total * 100);
			languages.setNumber(Double.parseDouble(sum));
		}

		return list;
	}

	@RequestMapping(value = "/api/show/{username}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<Languages> showUserLangList(
			@PathVariable("username") String username) throws Exception {

		List<Languages> list = userService.getLanguageByUsername(username);
		double total = 0;
		for (Languages languages : list) {
			total = total + languages.getNumber();
			System.out.println(total);
		}
		for (Languages languages : list) {
			// System.out.println(languages.getNumber());
			// System.out.println(total);
			String sum = new DecimalFormat("##.####").format(languages
					.getNumber() / total * 100);
			languages.setNumber(Double.parseDouble(sum));
		}

		return list;
	}

}
