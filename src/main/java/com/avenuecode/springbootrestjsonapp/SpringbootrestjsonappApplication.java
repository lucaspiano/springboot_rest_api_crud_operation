package com.avenuecode.springbootrestjsonapp;

import com.avenuecode.springbootrestjsonapp.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SpringbootrestjsonappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootrestjsonappApplication.class, args);
	}
}