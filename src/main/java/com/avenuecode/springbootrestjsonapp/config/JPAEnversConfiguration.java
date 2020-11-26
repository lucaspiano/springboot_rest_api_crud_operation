package com.avenuecode.springbootrestjsonapp.config;

import com.avenuecode.springbootrestjsonapp.repository.BookRepository;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import com.avenuecode.springbootrestjsonapp.service.impl.BookServiceImpl;
import org.glassfish.jersey.servlet.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableJpaAuditing
@ComponentScan("com.avenuecode.springbootrestjsonapp.config")
@EnableJpaRepositories(basePackageClasses= {BookRepository.class})
public class JPAEnversConfiguration implements WebMvcConfigurer {

    @Autowired
    BookRepository bookRepository;

    @Bean
    BookService bookService() {
        return new BookServiceImpl(bookRepository);
    }
}
