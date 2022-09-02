package ie.daniel.webapps.atmmachine.exceptions;

public class InvalidWithdrawalAmountException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidWithdrawalAmountException(String message, Integer minimumNoteInAtm) {

        super(String.format(message, minimumNoteInAtm));
    }
}
