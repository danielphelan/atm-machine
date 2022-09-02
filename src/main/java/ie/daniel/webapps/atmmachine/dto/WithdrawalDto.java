package ie.daniel.webapps.atmmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class WithdrawalDto extends AccountDetailsDto{
    
    private Integer withdrawalAmountRequested;
    private Map<Integer, Integer> withdrawalNotes;
    private BigDecimal remainingWithdrawalBalance;
    private String withdrawalStatus;
    
	public Integer getWithdrawalAmountRequested() {
		return withdrawalAmountRequested;
	}
	public void setWithdrawalAmountRequested(Integer withdrawalAmountRequested) {
		this.withdrawalAmountRequested = withdrawalAmountRequested;
	}
	public BigDecimal getRemainingWithdrawalBalance() {
		return remainingWithdrawalBalance.setScale(2, RoundingMode.HALF_EVEN);
	}
	public void setRemainingWithdrawalBalance(BigDecimal remainingWithdrawalBalance) {
		this.remainingWithdrawalBalance = remainingWithdrawalBalance;
	}
	public String getWithdrawalStatus() {
		return withdrawalStatus;
	}
	public void setWithdrawalStatus(String withdrawalStatus) {
		this.withdrawalStatus = withdrawalStatus;
	}
	public Map<Integer, Integer> getWithdrawalNotes() {
		return withdrawalNotes;
	}
	public void setWithdrawalNotes(Map<Integer, Integer> withdrawalNotes) {
		this.withdrawalNotes = withdrawalNotes;
	}


    
    
    
    
}
