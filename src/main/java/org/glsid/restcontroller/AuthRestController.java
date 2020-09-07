package org.glsid.restcontroller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
public class AuthRestController {
	
	@GetMapping({"/",""})
	public String login(){
		return "Bienvenue";
	}
}
