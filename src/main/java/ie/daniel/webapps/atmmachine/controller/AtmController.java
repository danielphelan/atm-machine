package ie.daniel.webapps.atmmachine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;
import ie.daniel.webapps.atmmachine.service.AtmService;

@RestController
@RequestMapping("/api/atm")
public class AtmController {

	private static Logger log = LoggerFactory.getLogger(AtmController.class);

	@Autowired
	AtmService atmService;
	
	@GetMapping("/check-available-notes")
	public AtmAvailableNotesDto checkAccountBalance() {
		log.info("Checking available notes in ATM");
		return atmService.getAtmMachineBalance();
	}


}
