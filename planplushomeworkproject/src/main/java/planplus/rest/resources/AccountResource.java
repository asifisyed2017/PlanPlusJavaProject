package planplus.rest.resources;

import planplus.rest.resources.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import planplus.core.models.entities.Account;

/**
 * Created by Syed Asif Iqbal
 */
public class AccountResource extends ResourceSupport {
    private String firstname;

    private String lastname;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    

    public Account toAccount() {
        Account account = new Account();
        account.setFirstname(firstname);
        account.setLastname(lastname);
        return account;
    }
}
