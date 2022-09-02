package ie.daniel.webapps.atmmatchine.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;
import ie.daniel.webapps.atmmachine.model.AtmNotes;
import ie.daniel.webapps.atmmachine.repository.AtmNotesRepository;
import ie.daniel.webapps.atmmachine.service.impl.AtmServiceImpl;

@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {

    @InjectMocks 
    private AtmServiceImpl atmService;
    
	@Mock
	private AtmNotesRepository atmNotesRepo;

	private static List<AtmNotes> atmNotes;
	private static AtmNotes fiftyNotes;
	private static AtmNotes twentyNotes;
	
	@BeforeAll
	static void setUp() {
		fiftyNotes = new AtmNotes(50);
		fiftyNotes.setCount(10);
		
		twentyNotes = new AtmNotes(20);
		twentyNotes.setCount(5);
		
		atmNotes = new ArrayList<AtmNotes>();
		atmNotes.add(fiftyNotes);
		atmNotes.add(twentyNotes);
	}
   
	@Test
	void getAtmMachineBalanceTest() throws Exception {
		when(atmNotesRepo.findAll()).thenReturn(atmNotes);
		when(atmNotesRepo.getTotalAtmBalance()).thenReturn(600);

		AtmAvailableNotesDto availableNotes = atmService.getAtmMachineBalance();
		assertNotNull(availableNotes);
		assertEquals(availableNotes.getAtmNotes().size(), 2);
		assertEquals(availableNotes.getAtmBalance(), 600);
	}
	
	@Test
	void updateNotesAmountTest() throws Exception {
		when(atmNotesRepo.save(any(AtmNotes.class))).thenReturn(new AtmNotes());
		when(atmNotesRepo.findById(50)).thenReturn(Optional.of(fiftyNotes));
		when(atmNotesRepo.findById(20)).thenReturn(Optional.of(twentyNotes));

		Map<Integer, Integer> notesUsedMap = new HashMap<>();
		notesUsedMap.put(50, 5);
		notesUsedMap.put(20, 3);
		
		atmService.reduceAtmNotesBalance(notesUsedMap);
		
		verify(atmNotesRepo, times(2)).save(any(AtmNotes.class));
		verify(atmNotesRepo, times(2)).findById(any(Integer.class));
	}


}
