package unsa.information.security.certificates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class CertificatesApplication {
	public static void main(String[] args) {
		SpringApplication.run(CertificatesApplication.class, args);
	}
}
