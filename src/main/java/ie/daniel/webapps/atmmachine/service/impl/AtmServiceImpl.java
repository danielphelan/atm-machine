package ie.daniel.webapps.atmmachine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;
import ie.daniel.webapps.atmmachine.repository.AtmNotesRepository;
import ie.daniel.webapps.atmmachine.service.AtmService;

@Service
public class AtmServiceImpl implements AtmService{

	@Autowired
	private AtmNotesRepository atmNotesRepo;
	
	@Override
	public AtmAvailableNotesDto getAtmMachineBalance() {
		return new AtmAvailableNotesDto(atmNotesRepo.findAll(), atmNotesRepo.getTotalAtmBalance());
	}

}
