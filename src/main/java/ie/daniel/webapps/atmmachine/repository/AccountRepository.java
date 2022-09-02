package ie.daniel.webapps.atmmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ie.daniel.webapps.atmmachine.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Account findByAccountIdAndAccountPin(Integer accountId, Integer accountPin);
	
}
