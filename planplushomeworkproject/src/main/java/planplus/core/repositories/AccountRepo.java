package planplus.core.repositories;

import planplus.core.models.entities.Account;
import planplus.core.services.util.AccountList;

import java.util.List;

/**
 * Created by Syed Asif Iqbal
 */
public interface AccountRepo {
    public List<Account> findAllAccounts();
    public Account findAccount(Long id);
    public Account findAccountByName(String name);
    public Account createAccount(Account data);
}
