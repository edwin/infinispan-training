package com.redhat.infinispan.training.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * <pre>
 *  com.redhat.infinispan.training.model.Account
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Oct 2024 9:14
 */
@RedisHash("Account")
public class Account implements Serializable {

    @Id
    private String accountNumber;

    private String accountName;

    public Account() {
    }

    public Account(String accountNumber, String accountName) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                '}';
    }
}