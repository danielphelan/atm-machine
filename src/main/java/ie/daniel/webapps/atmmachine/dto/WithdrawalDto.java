package ie.daniel.webapps.atmmachine.dto;


public class WithdrawalDto extends AccountDetailsDto{
    
    private Double withdrawalAmount;

	public Double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(Double withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}
    
    
    
}
