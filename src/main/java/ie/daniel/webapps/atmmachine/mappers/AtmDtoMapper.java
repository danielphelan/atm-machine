package ie.daniel.webapps.atmmachine.mappers;

import java.util.Map;

import org.springframework.stereotype.Component;

import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;
import ie.daniel.webapps.atmmachine.model.Account;

@Component
public class AtmDtoMapper {
	
	public CheckBalanceDto mapAccountToCheckBalance(final Account userAccount) {
		CheckBalanceDto balanceDto = new CheckBalanceDto();
		balanceDto.setAccountId(userAccount.getAccountId());
		balanceDto.setAccountBalance(userAccount.getAccountBalance());
		balanceDto.setAccountOverdraft(userAccount.getAccountOverdraft());
		
		return balanceDto;
	}
	
	public WithdrawalDto mapWithdrawalRequestToDto(final Account userAccount, final Integer withdrawalAmount, final Map<Integer, Integer> notesUsed) {
		WithdrawalDto withdrawal = new WithdrawalDto();
		withdrawal.setWithdrawalNotes(notesUsed);
		withdrawal.setAccountId(userAccount.getAccountId());
		withdrawal.setWithdrawalAmountRequested(withdrawalAmount);
		withdrawal.setRemainingWithdrawalBalance(userAccount.getMaximumAvailableFunds());
		withdrawal.setWithdrawalStatus("SUCCESSFUL");
		return withdrawal;
	}
}
