package planplus.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planplus.core.models.entities.Account;

import planplus.core.repositories.AccountRepo;

import planplus.core.services.AccountService;
import planplus.core.services.exceptions.AccountDoesNotExistException;
import planplus.core.services.exceptions.AccountExistsException;
import planplus.core.services.util.AccountList;
import java.util.List;

/**
 * Created by Syed Asif Iqbal
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public Account findAccount(Long id) {
        return accountRepo.findAccount(id);
    }

    @Override
    public Account createAccount(Account data) {
        Account account = accountRepo.findAccountByName(data.getFirstname());
        if(account != null)
        {
            throw new AccountExistsException();
        }
        return accountRepo.createAccount(data);
    }

    

    @Override
    public AccountList findAllAccounts() {
        return new AccountList(accountRepo.findAllAccounts());
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findAccountByName(name);
    }
}
