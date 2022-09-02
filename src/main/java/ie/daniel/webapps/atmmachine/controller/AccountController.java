package ie.daniel.webapps.atmmachine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;
import ie.daniel.webapps.atmmachine.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	private static Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/check-balance/{userAccountId}")
	public CheckBalanceDto checkAccountBalance(@PathVariable Integer userAccountId, @RequestParam Integer userPin) throws Exception {
		log.info("Checking Balance for user: " + userAccountId);
		return accountService.getUserAccountBalance(accountService.getUserAccount(userAccountId, userPin));
	}
	
	@PutMapping(value="/withdraw/{userAccountId}")
	public WithdrawalDto withdrawFromAccount(@PathVariable Integer userAccountId, @RequestParam Integer userPin, @RequestParam Integer withdrawalAmount) {
		log.info("Withdrawing " + withdrawalAmount + " from account " + userAccountId);
		return accountService.withdrawFundsFromUserAccount(accountService.getUserAccount(userAccountId, userPin), withdrawalAmount);
	}

	
}
