package com.redhat.infinispan.training.repository;

import com.redhat.infinispan.training.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *  com.redhat.infinispan.training.repository.AccountRepository
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 02 Oct 2024 9:15
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
}
