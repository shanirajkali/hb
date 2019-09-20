package kali.web_crawlers.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import kali.web_crawlers.topuniversities_com.University;

public interface UniversityRepository{
	
	public void persist(University university);
	
}
