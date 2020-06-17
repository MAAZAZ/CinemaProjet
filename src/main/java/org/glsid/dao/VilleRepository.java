package org.glsid.dao;

import org.glsid.modele.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface VilleRepository extends JpaRepository<Ville, Long> {
	
	
}

