package ie.daniel.webapps.atmmachine.service;

import java.util.Map;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;

public interface AtmService {

	AtmAvailableNotesDto getAtmMachineBalance();

	void reduceAtmNotesBalance(Map<Integer, Integer> notesUsed);
	
}
