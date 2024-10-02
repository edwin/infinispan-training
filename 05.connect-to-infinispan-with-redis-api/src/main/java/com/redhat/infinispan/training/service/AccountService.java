package com.redhat.infinispan.training.service;

import com.redhat.infinispan.training.model.Account;
import com.redhat.infinispan.training.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  com.redhat.infinispan.training.service.AccountService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Oct 2024 9:16
 */
@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);

        return accounts;
    }

}
