package ie.daniel.webapps.atmmachine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ie.daniel.webapps.atmmachine.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final String FAILED_STATUS = "FAILED";
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponseDto> failedToProcessRequest(Exception ex) {
		ErrorResponseDto error = new ErrorResponseDto(FAILED_STATUS, "Sorry we could not process your request right now. Please try again later");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAccountNotFoundException(AccountNotFoundException ex) {
		ErrorResponseDto error = new ErrorResponseDto(FAILED_STATUS, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(InvalidAccountPinException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectPinException(InvalidAccountPinException ex) {
		ErrorResponseDto error = new ErrorResponseDto(FAILED_STATUS, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(WithdrawalRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleAtmMissingFundsException(WithdrawalRequestException ex) {
		ErrorResponseDto error = new ErrorResponseDto(FAILED_STATUS, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InvalidWithdrawalAmountException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRequestAmount(InvalidWithdrawalAmountException ex) {
		ErrorResponseDto error = new ErrorResponseDto(FAILED_STATUS, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
}