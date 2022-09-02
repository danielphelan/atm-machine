package ie.daniel.webapps.atmmachine.exceptions;

public class WithdrawalRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WithdrawalRequestException(Integer balance) {
        super(String.format("Requested amount is too high. Maximum of %d can be withdrawn", balance));
    }
	
}
