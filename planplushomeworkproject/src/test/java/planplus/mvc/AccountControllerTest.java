package planplus.mvc;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import planplus.core.models.entities.Account;
import planplus.core.services.AccountService;
import planplus.core.services.exceptions.AccountDoesNotExistException;
import planplus.core.services.exceptions.AccountExistsException;
import planplus.core.services.util.AccountList;
import planplus.rest.mvc.AccountController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Chris on 6/28/14.
 */
public class AccountControllerTest {
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    private MockMvc mockMvc;

    private ArgumentCaptor<Account> accountCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        accountCaptor = ArgumentCaptor.forClass(Account.class);
    }


    @Test
    public void createAccountNonExistingUsername() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setLastname("Iqbal");
        createdAccount.setFirstname("Asif");

        when(service.createAccount(any(Account.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/rest/accounts")
                .content("{\"firstname\":\"Asif\",\"lastname\":\"Iqbal\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", endsWith("/rest/accounts/1")))
                .andExpect(jsonPath("$.firstname", is(createdAccount.getFirstname())))
                .andExpect(status().isCreated());

        verify(service).createAccount(accountCaptor.capture());

        String lastname = accountCaptor.getValue().getLastname();
        assertEquals("Iqbal", lastname);
    }

    @Test
    public void createAccountExistingUsername() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setLastname("Iqbal");
        createdAccount.setFirstname("Asif");

        when(service.createAccount(any(Account.class))).thenThrow(new AccountExistsException());

        mockMvc.perform(post("/rest/accounts")
                .content("{\"firstname\":\"Asif\",\"lastname\":\"Iqbal\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getExistingAccount() throws Exception {
        Account foundAccount = new Account();
        foundAccount.setId(1L);
        foundAccount.setLastname("Iqbal");
        foundAccount.setFirstname("Asif");

        when(service.findAccount(1L)).thenReturn(foundAccount);

        mockMvc.perform(get("/rest/accounts/1"))
                .andDo(print())
                .andExpect(jsonPath("$.lastname", is(foundAccount.getLastname())))
                .andExpect(jsonPath("$.firstname", is(foundAccount.getFirstname())))
                
                        .andExpect(status().isOk());
    }

    @Test
    public void getNonExistingAccount() throws Exception {
        when(service.findAccount(1L)).thenReturn(null);

        mockMvc.perform(get("/rest/accounts/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAllAccounts() throws Exception {
        List<Account> accounts = new ArrayList<Account>();

        Account accountA = new Account();
        accountA.setId(1L);
        accountA.setLastname("Plan");
        accountA.setFirstname("Plus");
        accounts.add(accountA);

        Account accountB = new Account();
        accountB.setId(1L);
        accountB.setLastname("Iqbal");
        accountB.setFirstname("Asif");
        accounts.add(accountB);

        AccountList accountList = new AccountList(accounts);

        when(service.findAllAccounts()).thenReturn(accountList);

        mockMvc.perform(get("/rest/accounts"))
                
                .andExpect(status().isOk());
    }


    @Test
    public void findAccountsByName() throws Exception {
        List<Account> accounts = new ArrayList<Account>();

        Account accountA = new Account();
        accountA.setId(1L);
        accountA.setLastname("Plan");
        accountA.setFirstname("PLus");
        accounts.add(accountA);

        Account accountB = new Account();
        accountB.setId(1L);
        accountB.setLastname("Iqbal");
        accountB.setFirstname("Asif");
        accounts.add(accountB);

        AccountList accountList = new AccountList(accounts);

        when(service.findAllAccounts()).thenReturn(accountList);

        mockMvc.perform(get("/rest/accounts").param("name", "accountA"))
                .andExpect(jsonPath("$.accounts[*].name",
                        everyItem(endsWith("accountA"))))
                .andExpect(status().isOk());
    }
}
