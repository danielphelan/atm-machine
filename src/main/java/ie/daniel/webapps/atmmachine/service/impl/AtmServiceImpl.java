package ie.daniel.webapps.atmmachine.service.impl;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;
import ie.daniel.webapps.atmmachine.model.AtmNotes;
import ie.daniel.webapps.atmmachine.repository.AtmNotesRepository;
import ie.daniel.webapps.atmmachine.service.AtmService;

@Service
public class AtmServiceImpl implements AtmService{

	private static Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AtmNotesRepository atmNotesRepo;
	
	@Override
	public AtmAvailableNotesDto getAtmMachineBalance() {
		return new AtmAvailableNotesDto(atmNotesRepo.findAll(), atmNotesRepo.getTotalAtmBalance());
	}

	@Override
	public void reduceAtmNotesBalance(Map<Integer, Integer> notesUsedMap) {
		notesUsedMap.forEach((noteKey, totalNotesUsed) -> updateNotesAmount(atmNotesRepo.findById(noteKey), totalNotesUsed));
		
	}

	private void updateNotesAmount(Optional<AtmNotes> atmNoteToUpdate, Integer totalNotesUsed) {
		AtmNotes note = atmNoteToUpdate.get();
		note.setCount(note.getCount() - totalNotesUsed);
		
		AtmNotes updatedNote = atmNotesRepo.save(note);
		log.info("Remaining number of {} notes is {}", updatedNote.getNote(), updatedNote.getCount());
	}


}
