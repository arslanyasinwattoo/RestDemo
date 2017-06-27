@org.hibernate.annotations.NamedQueries({

		@org.hibernate.annotations.NamedQuery(name = "getUserByUsername", query = "SELECT u FROM User u "
				+ "WHERE u.username= ?"),
		@org.hibernate.annotations.NamedQuery(name = "getRepositoryByUrl", query = "SELECT r FROM Repository r where r.url=?"),
		@org.hibernate.annotations.NamedQuery(name = "getLanguageByRepoId", query = "SELECT l FROM Languages l  WHERE l.repository.repository_Id = ?"), })
package com.project.mein.dao.query;

