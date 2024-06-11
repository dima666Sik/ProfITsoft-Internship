package ua.code.intership.proft.it.soft.springspaceinfohw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringSpaceInfoHw2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSpaceInfoHw2Application.class, args);
	}

}
