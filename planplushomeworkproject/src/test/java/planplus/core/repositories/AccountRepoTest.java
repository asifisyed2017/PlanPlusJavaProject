package planplus.core.repositories;

import planplus.core.repositories.AccountRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import planplus.core.models.entities.Account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Chris on 7/9/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/business-config.xml")
public class AccountRepoTest {

    @Autowired
    private AccountRepo repo;

    private Account account;

    @Before
    @Transactional
    @Rollback(false)
    public void setup()
    {
        account = new Account();
        account.setFirstname("Asif");
        account.setLastname("Iqbal");
        repo.createAccount(account);
    }

    @Test
    @Transactional
    public void testFind()
    {
        Account account = repo.findAccount(this.account.getId());
        assertNotNull(account);
        assertEquals(account.getFirstname(), "Asif");
        assertEquals(account.getLastname(), "Iqbal");
    }
}
