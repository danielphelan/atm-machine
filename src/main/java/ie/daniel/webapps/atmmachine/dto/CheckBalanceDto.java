package ie.daniel.webapps.atmmachine.dto;

public class CheckBalanceDto extends AccountDetailsDto {

	private Double accountBalance;

	private Double accountOverdraft;

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Double getAccountOverdraft() {
		return accountOverdraft;
	}

	public void setAccountOverdraft(Double accountOverdraft) {
		this.accountOverdraft = accountOverdraft;
	}

}
