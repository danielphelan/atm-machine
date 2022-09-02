package ie.daniel.webapps.atmmachine.exceptions;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(Integer accountId) {

        super(String.format("Account with Id %d not found", accountId));
    }
}
