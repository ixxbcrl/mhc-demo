package com.example.mhcdemo.application.query;

import com.example.mhcdemo.repository.AccountRepository;
import com.example.mhcdemo.repository.RoleRepository;
import com.example.mhcdemo.repository.entity.Account;
import com.example.mhcdemo.repository.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
@Transactional
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public AccountService(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account accountUser = accountRepository.findAccountByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found.", username)));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(getAuthorities(accountUser));
        return new User(accountUser.getUsername(), accountUser.getPassword(), authorities);
    }

    public Account loadUserByUsernameApp(String username) throws UsernameNotFoundException {
        return accountRepository.findAccountByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }

    private Collection<GrantedAuthority> getAuthorities(Account accountUser) {
        Set<Role> userRoles = accountUser.getUserRoles();
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role userRole : userRoles){
            grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getCode().toUpperCase()));
        }

        return grantedAuthorities;
    }
}
