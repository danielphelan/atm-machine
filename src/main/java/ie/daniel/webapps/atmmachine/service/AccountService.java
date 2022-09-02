package ie.daniel.webapps.atmmachine.service;

import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;
import ie.daniel.webapps.atmmachine.model.Account;

public interface AccountService {

	Account getUserAccount(final Integer userAccountId, final Integer userPin);

	CheckBalanceDto getUserAccountBalance(final Account userAccount);

	WithdrawalDto withdrawFundsFromUserAccount(final Account userAccount, final Integer withdrawalAmount);
	
}
