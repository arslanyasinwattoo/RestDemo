package com.project.mein.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mein.dao.GenericDao;
import com.project.mein.entity.Languages;
import com.project.mein.entity.Repository;
import com.project.mein.entity.User;

@Service
public class userService {
	@Autowired
	private GenericDao genericDao;

	// Curd functions for user
	public void addUser(User user) throws Exception {
		genericDao.insertOrUpdate(user);
	}

	public List<User> getAllUser() throws Exception {
		// TODO Auto-generated method stub
		return genericDao.findAll(User.class);
	}

	// curd function for repository
	public void addRepository(Repository repository) throws Exception {
		genericDao.insertOrUpdate(repository);
	}

	public List<Repository> getAllRepository() throws Exception {
		// TODO Auto-generated method stub
		return genericDao.findAll(Languages.class);
	}

	// curd functions for lanuages
	public void addLanguages(Languages languages) throws Exception {
		genericDao.insertOrUpdate(languages);
	}

	public List<Languages> getAllLanguages() throws Exception {
		// TODO Auto-generated method stub
		return genericDao.findAll(Languages.class);
	}
}
