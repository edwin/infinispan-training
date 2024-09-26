package com.redhat.infinispan.training.model;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

/**
 * <pre>
 *  com.redhat.infinispan.training.model.Account
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 26 Sep 2024 20:16
 */
@Indexed
public class Account {

    private String accountNumber;

    private String accountName;

    public Account() {
    }

    public Account(String accountNumber, String accountName) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
    }

    @ProtoField(number = 1)
    @Basic(projectable = true, sortable = true)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @ProtoField(number = 2)
    @Basic(projectable = true, sortable = true)
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
