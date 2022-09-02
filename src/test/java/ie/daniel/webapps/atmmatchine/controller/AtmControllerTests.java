package ie.daniel.webapps.atmmatchine.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.daniel.webapps.atmmachine.controller.AtmController;
import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;
import ie.daniel.webapps.atmmachine.service.AtmService;

@ExtendWith(MockitoExtension.class)
class AtmControllerTests {

	
    @InjectMocks 
    private AtmController atmController;

    @Mock
	private AtmService atmService;

    private static AtmAvailableNotesDto availableNotes;
    

    @BeforeAll
    static void setUp() { 	
    	availableNotes = new AtmAvailableNotesDto(new ArrayList<>(), 1500);
    }

	@Test
	void checkAtmAccountBalanceApiTest() throws Exception {
		when(atmService.getAtmMachineBalance()).thenReturn(availableNotes);
		AtmAvailableNotesDto notes = atmController.checkAccountBalance();
		assertNotNull(notes);
	}

}
