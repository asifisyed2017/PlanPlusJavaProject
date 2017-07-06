package planplus.rest.resources;

import planplus.rest.resources.*;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syed Asif Iqbal
 */
public class AccountListResource extends ResourceSupport {
    private List<AccountResource> accounts = new ArrayList<AccountResource>();

    public List<AccountResource> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResource> accounts) {
        this.accounts = accounts;
    }
}
