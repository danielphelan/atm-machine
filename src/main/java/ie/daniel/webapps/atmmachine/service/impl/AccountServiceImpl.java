package ie.daniel.webapps.atmmachine.service.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.daniel.webapps.atmmachine.dto.AtmAvailableNotesDto;
import ie.daniel.webapps.atmmachine.dto.CheckBalanceDto;
import ie.daniel.webapps.atmmachine.dto.WithdrawalDto;
import ie.daniel.webapps.atmmachine.exceptions.AccountNotFoundException;
import ie.daniel.webapps.atmmachine.exceptions.InvalidAccountPinException;
import ie.daniel.webapps.atmmachine.exceptions.InvalidWithdrawalAmountException;
import ie.daniel.webapps.atmmachine.exceptions.WithdrawalRequestException;
import ie.daniel.webapps.atmmachine.mappers.AtmDtoMapper;
import ie.daniel.webapps.atmmachine.model.Account;
import ie.daniel.webapps.atmmachine.model.AtmNotes;
import ie.daniel.webapps.atmmachine.repository.AccountRepository;
import ie.daniel.webapps.atmmachine.service.AccountService;
import ie.daniel.webapps.atmmachine.service.AtmService;

@Service
public class AccountServiceImpl implements AccountService {

	private static Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AtmService atmService;

	@Autowired
	private AtmDtoMapper atmDtoMapper;

	@Override
	public Account getUserAccount(final Integer userAccountId, final Integer userPin) {

		Account userAccount = accountRepository.findById(userAccountId)
				.orElseThrow(() -> new AccountNotFoundException(userAccountId));

		if (userAccount.getAccountPin().equals(userPin)) {
			return userAccount;
		} else {
			throw new InvalidAccountPinException(userAccountId);
		}

	}

	@Override
	public CheckBalanceDto getUserAccountBalance(final Account userAccount) {
		return atmDtoMapper.mapAccountToCheckBalance(userAccount);
	}

	@Override
	public WithdrawalDto withdrawFundsFromUserAccount(final Account userAccount, Integer withdrawalAmount) {
		AtmAvailableNotesDto atmFundsAvailable = atmService.getAtmMachineBalance();
		List<AtmNotes> sortedList = atmFundsAvailable.getAtmNotes().stream()
				.sorted(Comparator.comparingInt(AtmNotes::getNote).reversed()).collect(Collectors.toList());

		if (atmFundsAvailable.getAtmBalance() < withdrawalAmount) {
			throw new WithdrawalRequestException(atmFundsAvailable.getAtmBalance());
		} else if (BigDecimal.valueOf(withdrawalAmount).compareTo(userAccount.getMaximumAvailableFunds()) > 0) {
			throw new WithdrawalRequestException(userAccount.getMaximumAvailableFunds().intValue());
		} else if (withdrawalAmount
				% sortedList.stream().min(Comparator.comparing(AtmNotes::getNote)).get().getNote() != 0) {
			throw new InvalidWithdrawalAmountException(
					"Amount requested cannot be completed. Amount needs to be dividable by %d",
					sortedList.stream().min(Comparator.comparing(AtmNotes::getNote)).get().getNote());
		}
		
		Map<Integer, Integer> notesUsed = new HashMap<>();
		Integer remainingAmountToWithdraw = withdrawalAmount;
		for (AtmNotes atmNote : sortedList) {
			log.info("Withdrawal Amount Remaining:" + remainingAmountToWithdraw);
			log.info(atmNote.toString());
			if (remainingAmountToWithdraw == 0)
				break;

			
			if (atmNote.getNote() <= remainingAmountToWithdraw) {
				Integer notesCount = (remainingAmountToWithdraw / atmNote.getNote()) <= atmNote.getCount() ? remainingAmountToWithdraw / atmNote.getNote() : atmNote.getCount() ;
				notesUsed.put(atmNote.getNote(), notesCount);
				remainingAmountToWithdraw = remainingAmountToWithdraw - (atmNote.getNote() * notesCount);
			}
		}
		
		log.info(notesUsed.toString());
		BigDecimal monetaryWithdrawal = BigDecimal.valueOf(withdrawalAmount);
		if(userAccount.getAccountBalance().compareTo(monetaryWithdrawal) >= 0 ) {
			log.info("User account balance can cover the requested amount");
			userAccount.setAccountBalance(userAccount.getAccountBalance().subtract(monetaryWithdrawal));
		} else {
			log.info("Reducing Users account balance and the overdraft available");
			BigDecimal overdraftToBeUsed = monetaryWithdrawal.subtract(userAccount.getAccountBalance());
			userAccount.setAccountBalance(BigDecimal.ZERO);
			userAccount.setAccountOverdraft(userAccount.getAccountOverdraft().subtract(overdraftToBeUsed));			
		}
		
		accountRepository.save(userAccount);
		atmService.reduceAtmNotesBalance(notesUsed);
		
		return atmDtoMapper.mapWithdrawalRequestToDto(userAccount, withdrawalAmount, notesUsed);
	}

}
