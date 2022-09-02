package ie.daniel.webapps.atmmachine.model;


import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Account {

    @Id
    private Integer accountId;
    
    private Integer accountPin;

	private BigDecimal accountBalance;
    
    private BigDecimal accountOverdraft;

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
	
	public BigDecimal getMaximumAvailableFunds() {
		return this.accountOverdraft.add(this.accountBalance).setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountPin=" + accountPin + ", accountBalance=" + accountBalance
				+ ", accountOverdraft=" + accountOverdraft + "]";
	}
	
	
    
    
}
