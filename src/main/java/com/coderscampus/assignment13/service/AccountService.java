package com.coderscampus.assignment13.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;

    public Account findByAccountId(Long accountId) {
        Optional<Account> optAccount = accountRepository.findById(accountId);
        if (optAccount.isPresent()) {
            return optAccount.get();
        } else {
            throw new RuntimeException("Did not find ID: " + accountId);
        }
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }


    public Account createAccount(User user) {
        Account account = new Account();
        user.getAccounts().add(account);
        account.setAccountName("Account #" + user.getAccounts().size());
        account.getUsers().add(user);
        userService.saveUser(user);
        return saveAccount(account);
    }
}
