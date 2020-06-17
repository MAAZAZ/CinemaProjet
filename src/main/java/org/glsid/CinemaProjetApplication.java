package org.glsid;

import org.glsid.modele.Film;
import org.glsid.modele.Salle;
import org.glsid.modele.Ticket;
import org.glsid.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;



@SpringBootApplication
public class CinemaProjetApplication implements CommandLineRunner {
	
	//@Autowired
	//private ICinemaInitService cinemaInitService;
	@Autowired
	private RepositoryRestConfiguration restConfig;

	public static void main(String[] args) {
		SpringApplication.run(CinemaProjetApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		restConfig.exposeIdsFor(Film.class);
		restConfig.exposeIdsFor(Salle.class);
		restConfig.exposeIdsFor(Ticket.class);
		//cinemaInitService.initVilles();
		//cinemaInitService.initCinemas();
		//cinemaInitService.initSalles();
		//cinemaInitService.initPlaces();
		//cinemaInitService.initSeances();
		//cinemaInitService.initCategories();
		//cinemaInitService.initFilms();
		//cinemaInitService.initProjections();
		//cinemaInitService.initTickets();
	}

}
