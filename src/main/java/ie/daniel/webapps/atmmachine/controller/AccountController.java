package ie.daniel.webapps.atmmachine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	private static Logger log = LoggerFactory.getLogger(AccountController.class);

	@PostMapping("/check-balance")
	public CheckBalanceDto checkAccountBalance(@RequestBody CheckBalanceDto checkBalanceRequest) {
		log.info("Checking Balance:" + checkBalanceRequest);
		checkBalanceRequest.setAccountBalance(Double.valueOf(1000));
		checkBalanceRequest.setAccountOverdraft(Double.valueOf(100));
		return checkBalanceRequest;
	}
	
	@PostMapping(value="/withdraw")
	public WithdrawalDto withdrawFromAccount(@RequestBody WithdrawalDto withdrawalRequest) {
		log.info("Withdrawing" + withdrawalRequest);
		return withdrawalRequest;
	}

}
