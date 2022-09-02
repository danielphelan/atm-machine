package ie.daniel.webapps.atmmatchine.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;
import ie.daniel.webapps.atmmachine.mappers.AtmDtoMapper;
import ie.daniel.webapps.atmmachine.model.Account;

@ExtendWith(MockitoExtension.class)
class AtmDtoMapperTests {

	
    @InjectMocks 
    private AtmDtoMapper atmDtoMapper;

    private static Account userAccount;

    @BeforeAll
    static void setUp() { 	
    	userAccount = new Account();
    	userAccount.setAccountId(123445);
    	userAccount.setAccountPin(1234);
    	userAccount.setAccountBalance(BigDecimal.valueOf(500));
    	userAccount.setAccountOverdraft(BigDecimal.valueOf(200));
    	
    }

	@Test
	void mapAccountToCheckBalanceTest() throws Exception {
		CheckBalanceDto checkBalanceDto = atmDtoMapper.mapAccountToCheckBalance(userAccount);
		
		assertEquals(userAccount.getAccountId(), checkBalanceDto.getAccountId());
		assertEquals(userAccount.getAccountBalance(), checkBalanceDto.getAccountBalance());
		assertEquals(userAccount.getAccountOverdraft(), checkBalanceDto.getAccountOverdraft());
		assertEquals(userAccount.getMaximumAvailableFunds(), checkBalanceDto.getMaximumWithdrawal());
	}

	@Test
	void mapWithdrawalRequestToDtoTest() throws Exception {
		Map<Integer, Integer> notesUsed = new HashMap<>();
		notesUsed.put(50, 8);
		Integer withdrawalRequested = 400;
		
		WithdrawalDto withdrawalDto = atmDtoMapper.mapWithdrawalRequestToDto(userAccount, withdrawalRequested, notesUsed);
		
		assertEquals(notesUsed, withdrawalDto.getWithdrawalNotes());
		assertEquals(userAccount.getAccountId(), withdrawalDto.getAccountId());
		assertEquals(withdrawalRequested, withdrawalDto.getWithdrawalAmountRequested());
		assertEquals(userAccount.getMaximumAvailableFunds(), withdrawalDto.getRemainingWithdrawalBalance());
		assertEquals("SUCCESSFUL", withdrawalDto.getWithdrawalStatus());
	}
	

}
