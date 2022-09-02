package ie.daniel.webapps.atmmatchine.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;
import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;
import ie.daniel.webapps.atmmachine.exceptions.AccountNotFoundException;
import ie.daniel.webapps.atmmachine.exceptions.InvalidAccountPinException;
import ie.daniel.webapps.atmmachine.exceptions.InvalidWithdrawalAmountException;
import ie.daniel.webapps.atmmachine.exceptions.WithdrawalRequestException;
import ie.daniel.webapps.atmmachine.mappers.AtmDtoMapper;
import ie.daniel.webapps.atmmachine.model.Account;
import ie.daniel.webapps.atmmachine.model.AtmNotes;
import ie.daniel.webapps.atmmachine.repository.AccountRepository;
import ie.daniel.webapps.atmmachine.service.AtmService;
import ie.daniel.webapps.atmmachine.service.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks 
    private AccountServiceImpl accountService;
    
	@Mock
	private AccountRepository accountsRepo;

	@Mock
	private AtmService atmService;
	
	@Mock
	private AtmDtoMapper atmDtoMapper;
	
	private static final Integer ACCOUNT_ID = 1234567;
	private static final Integer ACCOUNT_PIN = 1234;
	private static final BigDecimal ACCOUNT_BALANCE = BigDecimal.valueOf(800);
	private static final BigDecimal ACCOUNT_OVERDRAFT = BigDecimal.valueOf(200);
	
    private static Account userAccount;
    private static AtmAvailableNotesDto availableNotes;
	private static List<AtmNotes> atmNotes;
	private static AtmNotes fiftyNotes;
	private static AtmNotes twentyNotes;
	private static AtmNotes tenNotes;
	private static AtmNotes fiveNotes;
	
	@BeforeEach
	void setUp() {
    	userAccount = new Account();
    	userAccount.setAccountId(ACCOUNT_ID);
    	userAccount.setAccountPin(ACCOUNT_PIN);
    	userAccount.setAccountBalance(ACCOUNT_BALANCE);
    	userAccount.setAccountOverdraft(ACCOUNT_OVERDRAFT);
    	
		fiftyNotes = new AtmNotes(50);
		fiftyNotes.setCount(20);
		
		twentyNotes = new AtmNotes(20);
		twentyNotes.setCount(25);
		
		tenNotes = new AtmNotes(10);
		tenNotes.setCount(10);

		fiveNotes = new AtmNotes(5);
		fiveNotes.setCount(10);
		
		atmNotes = new ArrayList<AtmNotes>();
		atmNotes.add(fiftyNotes);
		atmNotes.add(twentyNotes);
		atmNotes.add(tenNotes);
		atmNotes.add(fiveNotes);
		
    	availableNotes = new AtmAvailableNotesDto(atmNotes, 1500);
	}
   
	@Test
	void getUserAccountTest() throws Exception {
		when(accountsRepo.findById(ACCOUNT_ID)).thenReturn(Optional.of(userAccount));

		Account accountReturned = accountService.getUserAccount(ACCOUNT_ID, ACCOUNT_PIN);
		assertNotNull(accountReturned);
	}

	@Test
	void getUserAccountTestWithAccountNotFoundException() throws Exception {
		when(accountsRepo.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

		Throwable exception = assertThrows(AccountNotFoundException.class, () -> accountService.getUserAccount(ACCOUNT_ID, ACCOUNT_PIN)); 
								
		assertEquals(exception.getMessage(), "Account with Id 1234567 not found");
	}
	
	@Test
	void getUserAccountTestWithInvalidAccountPinException() throws Exception {
		userAccount.setAccountPin(9999);
		when(accountsRepo.findById(ACCOUNT_ID)).thenReturn(Optional.of(userAccount));

		Throwable exception = assertThrows(InvalidAccountPinException.class, () -> accountService.getUserAccount(ACCOUNT_ID, ACCOUNT_PIN)); 
								
		assertEquals(exception.getMessage(), "Invalid pin for account with Id 1234567 entered");
	}
	
	@Test
	void getUserAccountBalanceTest() throws Exception {
		when(atmDtoMapper.mapAccountToCheckBalance(userAccount)).thenReturn(new CheckBalanceDto());

		CheckBalanceDto checkBalanceDto = accountService.getUserAccountBalance(userAccount);
		assertNotNull(checkBalanceDto);
	}

	@Test
	void withdrawFundsFromUserAccountTestWithdrawalRequestException_OverAtmBalance() throws Exception {
		when(atmService.getAtmMachineBalance()).thenReturn(availableNotes);

		Throwable exception = assertThrows(WithdrawalRequestException.class, () -> accountService.withdrawFundsFromUserAccount(userAccount, 2000)); 
		
		assertEquals(exception.getMessage(), "Requested amount is too high. Maximum of 1500 can be withdrawn");
	}
	
	@Test
	void withdrawFundsFromUserAccountTestWithdrawalRequestException_OverUserBalance() throws Exception {
		when(atmService.getAtmMachineBalance()).thenReturn(availableNotes);

		Throwable exception = assertThrows(WithdrawalRequestException.class, () -> accountService.withdrawFundsFromUserAccount(userAccount, 1500)); 
		
		assertEquals(exception.getMessage(), "Requested amount is too high. Maximum of 1000 can be withdrawn");
	}
	
	@Test
	void withdrawFundsFromUserAccountTestWithdrawalRequestException_InvalidWithdrawalAmount() throws Exception {
		when(atmService.getAtmMachineBalance()).thenReturn(availableNotes);

		Throwable exception = assertThrows(InvalidWithdrawalAmountException.class, () -> accountService.withdrawFundsFromUserAccount(userAccount, 452)); 
		
		assertEquals(exception.getMessage(), "Amount requested cannot be completed. Amount needs to be dividable by 5");
	}
	
	@Test
	void withdrawFundsFromUserAccountTestJustBalance() throws Exception {	
		when(atmService.getAtmMachineBalance()).thenReturn(availableNotes);
		when(accountsRepo.save(any(Account.class))).thenReturn(userAccount);
		doNothing().when(atmService).reduceAtmNotesBalance(anyMap());
		when(atmDtoMapper.mapWithdrawalRequestToDto(any(Account.class), anyInt(), anyMap())).thenReturn(new WithdrawalDto());
		
		WithdrawalDto withdrawalDto = accountService.withdrawFundsFromUserAccount(userAccount, 280);
		assertNotNull(withdrawalDto);
		verify(accountsRepo, times(1)).save(any(Account.class));
		verify(atmService, times(1)).reduceAtmNotesBalance(anyMap());
	}
	
	@Test
	void withdrawFundsFromUserAccountTestBalanceAndOverdraft() throws Exception {	
		when(atmService.getAtmMachineBalance()).thenReturn(availableNotes);
		when(accountsRepo.save(any(Account.class))).thenReturn(userAccount);
		doNothing().when(atmService).reduceAtmNotesBalance(anyMap());
		when(atmDtoMapper.mapWithdrawalRequestToDto(any(Account.class), anyInt(), anyMap())).thenReturn(new WithdrawalDto());
		
		WithdrawalDto withdrawalDto = accountService.withdrawFundsFromUserAccount(userAccount, 960);
		assertNotNull(withdrawalDto);
		verify(accountsRepo, times(1)).save(any(Account.class));
		verify(atmService, times(1)).reduceAtmNotesBalance(anyMap());
	}
	
	
}
