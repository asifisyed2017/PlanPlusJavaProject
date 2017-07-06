package planplus.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import planplus.core.models.entities.Account;

import planplus.core.services.AccountService;
import planplus.core.services.exceptions.AccountDoesNotExistException;
import planplus.core.services.exceptions.AccountExistsException;

import planplus.core.services.util.AccountList;

import planplus.rest.exceptions.BadRequestException;
import planplus.rest.exceptions.ConflictException;
import planplus.rest.exceptions.ForbiddenException;
import planplus.rest.exceptions.NotFoundException;
import planplus.rest.resources.AccountListResource;
import planplus.rest.resources.AccountResource;

import planplus.rest.resources.asm.AccountListResourceAsm;
import planplus.rest.resources.asm.AccountResourceAsm;


import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Syed Asif Iqbal
 */
@Controller
@RequestMapping("/rest/accounts")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
//    /**
//	 * Simply selects the home view to render by returning its name.
//	 */
//	@RequestMapping(value="/", method=RequestMethod.GET)
//	public String home() {
//		return "view";
//	}

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountListResource> findAllAccounts(@RequestParam(value="firstname", required = false) String firstname, @RequestParam(value="lastname", required = false) String lastname) {
        AccountList list = null;
        if(firstname == null) {
            list = accountService.findAllAccounts();
        } else {
            Account account = accountService.findByAccountName(firstname);
            list = new AccountList(new ArrayList<Account>());
            if(account != null) {
                if(lastname != null) {
                    if(account.getFirstname().equals(lastname)) {
                        list = new AccountList(Arrays.asList(account));
                    }
                } else {
                    list = new AccountList(Arrays.asList(account));
                }
            }
        }
        AccountListResource res = new AccountListResourceAsm().toResource(list);
        return new ResponseEntity<AccountListResource>(res, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountResource> createAccount(
            @RequestBody AccountResource sentAccount
    ) {
        try {
            Account createdAccount = accountService.createAccount(sentAccount.toAccount());
            AccountResource res = new AccountResourceAsm().toResource(createdAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(res, headers, HttpStatus.CREATED);
        } catch(AccountExistsException exception) {
            throw new ConflictException(exception);
        }
    }

    @RequestMapping( value="/{accountId}",
                method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountResource> getAccount(
            @PathVariable Long accountId
    ) {
        Account account = accountService.findAccount(accountId);
        if(account != null)
        {
            AccountResource res = new AccountResourceAsm().toResource(account);
            return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
        }
    }
}
