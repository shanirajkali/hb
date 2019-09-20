package kali.web_crawlers.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kali.web_crawlers.topuniversities_com.University;


public interface UniversityService {

	
	public void save(University university);
}
