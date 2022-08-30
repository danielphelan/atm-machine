package ie.daniel.webapps.atmmachine.model;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Account {

    @Id
    private Integer accountId;
    
    private Integer accountPin;

	private Double accountBalance;
    
    private Double accountOverdraft;

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

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountPin=" + accountPin + ", accountBalance=" + accountBalance
				+ ", accountOverdraft=" + accountOverdraft + "]";
	}
	
	
    
    
}
