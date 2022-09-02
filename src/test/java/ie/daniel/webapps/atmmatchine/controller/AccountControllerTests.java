package ie.daniel.webapps.atmmatchine.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.daniel.webapps.atmmachine.controller.AccountController;
import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;
import ie.daniel.webapps.atmmachine.model.Account;
import ie.daniel.webapps.atmmachine.service.AccountService;

@ExtendWith(MockitoExtension.class)
class AccountControllerTests {

    @InjectMocks 
    private AccountController accountController;
    
	@Mock
	private AccountService accountService;

	private static final Integer ACCOUNT_ID = 1234567;
	private static final Integer ACCOUNT_PIN = 1234;
	private static final BigDecimal ACCOUNT_BALANCE = BigDecimal.valueOf(800);
	private static final BigDecimal ACCOUNT_OVERDRAFT = BigDecimal.valueOf(200);
	
    private static Account userAccount;
    private static CheckBalanceDto checkBalanceDto;
    private static WithdrawalDto withdrawalDto;
    
    @BeforeAll
    static void setUp() {
    	userAccount = new Account();
    	userAccount.setAccountId(ACCOUNT_ID);
    	userAccount.setAccountPin(ACCOUNT_PIN);
    	userAccount.setAccountBalance(ACCOUNT_BALANCE);
    	userAccount.setAccountOverdraft(ACCOUNT_OVERDRAFT);
    	
    	checkBalanceDto = new CheckBalanceDto();
    	checkBalanceDto.setAccountId(ACCOUNT_ID);
    	checkBalanceDto.setAccountBalance(ACCOUNT_BALANCE);
    	checkBalanceDto.setAccountOverdraft(ACCOUNT_OVERDRAFT);
    	
    	withdrawalDto = new WithdrawalDto();
    }
    
	@Test
	void checkAccountBalanceTest() throws Exception {
		when(accountService.getUserAccount(ACCOUNT_ID, ACCOUNT_PIN)).thenReturn(userAccount);
		when(accountService.getUserAccountBalance(userAccount)).thenReturn(checkBalanceDto);
		CheckBalanceDto userBalance = accountController.checkAccountBalance(ACCOUNT_ID, ACCOUNT_PIN);
		assertNotNull(userBalance);
		assertEquals(userBalance.getMaximumWithdrawal(), userAccount.getAccountBalance().add(userAccount.getAccountOverdraft()));
	}
	
	@Test
	void withdrawFromAccountTest() throws Exception {
		Integer withdrawalAmount = 400;
		when(accountService.getUserAccount(ACCOUNT_ID, ACCOUNT_PIN)).thenReturn(userAccount);
		when(accountService.withdrawFundsFromUserAccount(userAccount, withdrawalAmount)).thenReturn(withdrawalDto);
		WithdrawalDto withdrawalResult = accountController.withdrawFromAccount(ACCOUNT_ID, ACCOUNT_PIN, withdrawalAmount);
		assertNotNull(withdrawalResult);
	}

}
