package ie.daniel.webapps.atmmachine.dto;


public class AccountDetailsDto {

    private Integer accountId;
    
    private Integer accountPin;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getAccountPin() {
		return accountPin;
	}

	public void setAccountPin(Integer accountPin) {
		this.accountPin = accountPin;
	}    
    
}
