package kali.web_crawlers.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kali.web_crawlers.topuniversities_com.University;

@Component
@Transactional
public class UniversityRepositoryImpl implements UniversityRepository{

	@Autowired
	SessionFactory sessionFactory;
	
	public void persist(University university) {
		sessionFactory.openSession().save(university);
		sessionFactory.openSession().beginTransaction().commit();
	
	}

}
     