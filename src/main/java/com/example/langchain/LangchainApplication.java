package com.example.langchain;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "langchain4j-spring")
public class LangchainApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(LangchainApplication.class, args);
	}

}
