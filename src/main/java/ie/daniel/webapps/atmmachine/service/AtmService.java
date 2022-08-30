package ie.daniel.webapps.atmmachine.service;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;

public interface AtmService {

	AtmAvailableNotesDto getAtmMachineBalance();
	
}
