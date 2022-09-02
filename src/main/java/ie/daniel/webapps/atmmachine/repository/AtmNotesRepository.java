package ie.daniel.webapps.atmmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ie.daniel.webapps.atmmachine.model.AtmNotes;

@Repository
public interface AtmNotesRepository extends JpaRepository<AtmNotes, Integer> {
	
	@Query("SELECT SUM(m.note * m.count) FROM AtmNotes m")
	Integer getTotalAtmBalance();
}
