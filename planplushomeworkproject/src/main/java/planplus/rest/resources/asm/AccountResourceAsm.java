package planplus.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import planplus.core.models.entities.Account;
import planplus.rest.mvc.AccountController;
import planplus.rest.resources.AccountResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by Syed Asif Iqbal
 */
public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {
    public AccountResourceAsm() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource res = new AccountResource();
        res.setFirstname(account.getFirstname());
        res.setLastname(account.getLastname());
        res.setRid(account.getId());
        res.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
       
        return res;
    }
}
