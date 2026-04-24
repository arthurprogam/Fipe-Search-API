package br.com.exercicio.tabelaFipe;

import br.com.exercicio.tabelaFipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class TabelaFipeApplication implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		Thread.sleep(2000);

		Runtime.getRuntime().exec(
				"cmd /c start chrome http://localhost:8080/fipe-search.html"
		);

		Principal principal = new Principal();
		principal.exibeMenu();

	}

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeApplication.class, args);
	}

}
