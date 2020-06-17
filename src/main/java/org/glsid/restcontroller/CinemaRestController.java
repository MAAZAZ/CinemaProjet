package org.glsid.restcontroller;

import java.util.List;

import org.glsid.dao.CinemaRepository;
import org.glsid.modele.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/cinema")
public class CinemaRestController {
	@Autowired
	private CinemaRepository cinemaRepository;
	
	
	@GetMapping({"/",""})
	public List<Cinema> All(){
		return cinemaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Cinema cinema(@PathVariable Long id) {
		return cinemaRepository.findById(id).get();
	}
	
	@PostMapping("/")
	public Cinema save(@RequestBody Cinema cinema) {
		return cinemaRepository.save(cinema);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		 cinemaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public Cinema update(@RequestBody Cinema cinema,@PathVariable Long id) {
		 cinema.setId(id);
		 return cinemaRepository.save(cinema);
	}
	

}
