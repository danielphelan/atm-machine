package ie.daniel.webapps.atmmachine.dto;


public class ErrorResponseDto {

    private String transactionStatus;
    
    private String reason;


	public ErrorResponseDto() {

	}
	
	public ErrorResponseDto(String transactionStatus, String reason) {
		this.transactionStatus = transactionStatus;
		this.reason = reason;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
    
    
}
