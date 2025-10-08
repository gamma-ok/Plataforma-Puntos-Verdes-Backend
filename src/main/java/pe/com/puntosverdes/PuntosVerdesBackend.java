package pe.com.puntosverdes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PuntosVerdesBackend {

	public static void main(String[] args) {
		SpringApplication.run(PuntosVerdesBackend.class, args);
	}

}
