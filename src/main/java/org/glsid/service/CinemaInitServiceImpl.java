package org.glsid.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.glsid.dao.CategorieRepository;
import org.glsid.dao.CinemaRepository;
import org.glsid.dao.FilmRepository;
import org.glsid.dao.PlaceRepository;
import org.glsid.dao.ProjectionRepository;
import org.glsid.dao.SalleRepository;
import org.glsid.dao.SeanceRepository;
import org.glsid.dao.TicketRepository;
import org.glsid.dao.VilleRepository;
import org.glsid.modele.Categorie;
import org.glsid.modele.Cinema;
import org.glsid.modele.Film;
import org.glsid.modele.Place;
import org.glsid.modele.Projection;
import org.glsid.modele.Salle;
import org.glsid.modele.Seance;
import org.glsid.modele.Ticket;
import org.glsid.modele.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {

	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public void initVilles() {
		Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(v->{
			Ville ville=new Ville();
			ville.setId(null);
			ville.setName(v);
			villeRepository.save(ville);
		});	
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v->{
			Stream.of("JAWHARA","MeGaRaMa","IMAX","FOUNOUN","CHAHRAZA","DAOUALIZ").forEach(f->{
				Cinema cinema=new Cinema();
				cinema.setName(f);
				cinema.setVille(v);
				cinema.setNb_salle(3+new Random().nextInt(10));
				cinemaRepository.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema->{
			for(int i=0;i<cinema.getNb_salle();i++) {
				Salle salle=new Salle();
				salle.setName("Salle "+(i+1));
				salle.setCinema(cinema);
				salle.setNb_place(20+new Random().nextInt(20));
				salleRepository.save(salle);
			}
		});	
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNb_place();i++) {
				Place place =new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat=new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
			Seance seance=new Seance();
			try {
				seance.setHeureDepart(dateFormat.parse(s));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			seanceRepository.save(seance);
		});	
	}

	@Override
	public void initCategories() {
		Stream.of("Action","Drama","adventure","Fiction","Horreur").forEach(cat->{
			Categorie categorie=new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initFilms() {
		double[] duree=new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories=categorieRepository.findAll();
		Stream.of("Iron Man","Blair Witch","Bad Boys 3","Bloodshot").forEach(filmName->{
			Film film=new Film();
			film.setTitre(filmName);
			film.setDuree(duree[new Random().nextInt(duree.length)]);
			film.setPhoto(filmName.replace(" ", "_")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
	}

	@Override
	public void initProjections() {
		double[] prices=new double[] {30,40,50,60,80,100};
		List<Film> films=filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index=new Random().nextInt(films.size());
					Film film=films.get(index);
					seanceRepository.findAll().forEach(seance->{
						Projection projection=new Projection();
						projection.setDateProjection(new Date());
						projection.setPrix(prices[new Random().nextInt(prices.length)]);
						projection.setFilm(film);
						projection.setSalle(salle);
						projection.setSeance(seance);
						projectionRepository.save(projection);
					});
				});
			});
		});
		
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(p->{
			p.getSalle().getPlaces().forEach(place->{
				Ticket ticket=new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(p.getPrix());
				ticket.setReserve(false);
				ticket.setProjection(p);
				ticketRepository.save(ticket);
			});
		});
		
	}

}
