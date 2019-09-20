package kali.web_crawlers.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kali.web_crawlers.repository.UniversityRepository;
import kali.web_crawlers.topuniversities_com.Scrapper;
import kali.web_crawlers.topuniversities_com.University;

@RestController
@RequestMapping("")
public class ScraperController {
	
	@Autowired
	UniversityRepository universityRepository;
	
	@RequestMapping("/universityData")
	public String downloadUniversityData() throws IOException{
		for(int i=0;i<10000;i++){
			universityRepository.persist(new University());
		}
		return "donw";
	}
}
