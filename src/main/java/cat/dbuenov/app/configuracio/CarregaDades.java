package cat.dbuenov.app.configuracio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cat.dbuenov.app.bean.*;
import cat.dbuenov.app.dades.BaseDeDades;


@Configuration
public class CarregaDades {
	
	@Bean
	CommandLineRunner iniciaBaseDatos (BaseDeDades baseDeDades) {

		return args ->{
			
			baseDeDades.add(new Empleat("Dani",new Feina("Tecnic")));
			baseDeDades.add(new Empleat("Joan",new Feina("comercial")));
			baseDeDades.add(new Empleat("Carles",new Feina("Tecnic")));
			baseDeDades.add(new Empleat("Enric",new Feina("Tecnic")));
			baseDeDades.add(new Empleat("Pep",new Feina("Comercial")));
			baseDeDades.add(new Empleat("Marc",new Feina("Gerent")));
			baseDeDades.add(new Empleat("Pau",new Feina("comercial")));
			baseDeDades.add(new Empleat("Maria",new Feina("Mestre")));
					
		};
	}	
}
