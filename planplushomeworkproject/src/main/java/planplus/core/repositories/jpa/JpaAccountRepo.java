package planplus.core.repositories.jpa;

import org.springframework.stereotype.Repository;
import planplus.core.models.entities.Account;
import planplus.core.repositories.AccountRepo;
import planplus.core.services.util.AccountList;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Syed Asif Iqbal
 */
@Repository
public class JpaAccountRepo implements AccountRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Account> findAllAccounts() {
        Query query = em.createQuery("SELECT a FROM Account a");
        return query.getResultList();
    }

    @Override
    public Account findAccount(Long id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account findAccountByName(String firstname) {
        Query query = em.createQuery("SELECT a FROM Account a WHERE a.firstname=?1");
        query.setParameter(1, firstname);
        List<Account> accounts = query.getResultList();
        if(accounts.size() == 0) {
            return null;
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account createAccount(Account data) {
        em.persist(data);
        return data;
    }
}
