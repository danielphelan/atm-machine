package ie.daniel.webapps.atmmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckBalanceDto extends AccountDetailsDto {

	private BigDecimal accountBalance;

	private BigDecimal accountOverdraft;
	
	private BigDecimal maximumWithdrawal;

	public BigDecimal getAccountBalance() {
		return accountBalance.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BigDecimal getAccountOverdraft() {
		return accountOverdraft.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setAccountOverdraft(BigDecimal accountOverdraft) {
		this.accountOverdraft = accountOverdraft;
	}

	public BigDecimal getMaximumWithdrawal() {
		this.maximumWithdrawal = this.accountBalance.add(this.accountOverdraft);
		return this.maximumWithdrawal.setScale(2, RoundingMode.HALF_EVEN);
	}
	
}
