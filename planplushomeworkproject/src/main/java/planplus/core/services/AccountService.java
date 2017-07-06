package planplus.core.services;

import planplus.core.models.entities.Account;
import planplus.core.services.util.AccountList;


import java.util.List;

/**
 * Created by Syed Asif Iqbal
 */
public interface AccountService {
    public Account findAccount(Long id);
    public Account createAccount(Account data);
    public AccountList findAllAccounts();
    public Account findByAccountName(String name);
}
