package org.glsid.restcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.glsid.dao.FilmRepository;
import org.glsid.dao.TicketRepository;
import org.glsid.modele.Film;
import org.glsid.modele.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.Data;

@RestController
@CrossOrigin("*")
@RequestMapping("/film")
public class FilmRestController {
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	
	@GetMapping({"/",""})
	public List<Film> All(){
		return filmRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Film film(@PathVariable Long id) {
		return filmRepository.findById(id).get();
	}
	
	@PostMapping("/")
	public Film save(@RequestBody Film film) {
		return filmRepository.save(film);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		filmRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public Film update(@RequestBody Film film,@PathVariable Long id) {
		 film.setId(id);
		 return filmRepository.save(film);
	}
	
	@GetMapping(path="/imageFilm/{id}", produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable(name="id") Long id) throws IOException {
		Film film=filmRepository.findById(id).get();
		String photo=film.getPhoto();
		File file=new File(System.getProperty("user.home")+"/eclipse-workspace/CinemaProjet/src/main/resources/static/images/"+photo);
		Path path=Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	
	@PostMapping(path="/payerTickets")
	@Transactional
	public List<Ticket> TicketsPayer(@RequestBody TicketForm ticketForm) {
		List<Ticket> tickets=new ArrayList<>();
		ticketForm.getTickets().forEach(id->{
			Ticket ticket= ticketRepository.findById(id).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setCodePayement(ticketForm.getCodePayement());
			ticket.setReserve(true);
			ticketRepository.save(ticket);
			tickets.add(ticket);
		});
		
		return tickets;
	}
}
	
	@Data
	class TicketForm{
		private String nomClient;
		private int codePayement;
		private List<Long> tickets;
	}
	
