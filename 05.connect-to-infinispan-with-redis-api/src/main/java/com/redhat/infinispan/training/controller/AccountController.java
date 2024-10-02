package com.redhat.infinispan.training.controller;

import com.redhat.infinispan.training.model.Account;
import com.redhat.infinispan.training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  com.redhat.infinispan.training.controller.AccountController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Oct 2024 9:28
 */
@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/accounts")
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity save(@RequestBody Account account) {
        accountService.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
