package ie.daniel.webapps.atmmachine.exceptions;

public class InvalidAccountPinException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidAccountPinException(Integer accountId) {

        super(String.format("Invalid pin for account with Id %d entered", accountId));
    }
}
