package com.redhat.infinispan.training.controller;

import com.redhat.infinispan.training.helper.CacheHelper;
import com.redhat.infinispan.training.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *  com.redhat.infinispan.training.controller.IndexController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Sep 2024 8:57
 */
@RestController
public class IndexController {

    private CacheHelper cacheHelper;

    @Autowired
    public IndexController(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
    }

    @GetMapping(path = "/generate")
    public String generate() {
        cacheHelper.generate();
        return "good";
    }

    @GetMapping(path = "/accounts")
    public List<Account> getAccounts() {
        return cacheHelper.query();
    }

}
