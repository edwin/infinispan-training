package com.redhat.infinispan.training.model;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

/**
 * <pre>
 *  com.redhat.infinispan.training.model.Balance
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Sep 2024 13:53
 */
@Indexed
public class Balance {

    @KeywordField
    private String id;

    private Long totalBalance;

    public Balance() {

    }

    public Balance(String id, Long totalBalance) {
        this.id = id;
        this.totalBalance = totalBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Long totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id='" + id + '\'' +
                ", totalBalance=" + totalBalance +
                '}';
    }
}
