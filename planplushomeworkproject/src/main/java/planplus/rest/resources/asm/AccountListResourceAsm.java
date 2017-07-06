package planplus.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import planplus.core.services.util.AccountList;
import planplus.rest.mvc.AccountController;
import planplus.rest.resources.AccountListResource;
import planplus.rest.resources.AccountResource;

import java.util.List;

/**
 * Created by Syed Asif Iqbal
 */
public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountList, AccountListResource> {


    public AccountListResourceAsm() {
        super(AccountController.class, AccountListResource.class);
    }

    @Override
    public AccountListResource toResource(AccountList accountList) {
        List<AccountResource> resList = new AccountResourceAsm().toResources(accountList.getAccounts());
        AccountListResource finalRes = new AccountListResource();
        finalRes.setAccounts(resList);
        return finalRes;
    }
}
