package com.example.mhcdemo.application.config;

import com.example.mhcdemo.repository.AccountRepository;
import com.example.mhcdemo.repository.RoleRepository;
import com.example.mhcdemo.repository.entity.Account;
import com.example.mhcdemo.repository.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Configures our entities on application startup.
 */
@Component
public class ApplicationDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean started = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (started) {
            return;
        }

        //Role for hr1 user
        Role hrRole = roleRepository.findByCode("hr-admin");
        Account account = accountRepository.findAccountByUsername("hr1").get();
        account.setUserRoles(Collections.singleton(hrRole));
        accountRepository.save(account);

        //Role for vendor1 user
        Role vendorRole = roleRepository.findByCode("vendor-admin");
        Account v1Account = accountRepository.findAccountByUsername("v1").get();
        v1Account.setUserRoles(Collections.singleton(vendorRole));
        accountRepository.save(v1Account);

        //Role for vendor2 user
        Account v2Account = accountRepository.findAccountByUsername("v2").get();
        v2Account.setUserRoles(Collections.singleton(vendorRole));
        accountRepository.save(v2Account);

        started = true;
    }
}
