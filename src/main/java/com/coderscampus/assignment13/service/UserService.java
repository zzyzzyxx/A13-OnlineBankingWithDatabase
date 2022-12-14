package com.coderscampus.assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.AddressRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private AddressRepository addressRepository;

    public List<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }


    public User findExactlyOneUserByUsername(String username) {
        List<User> users = userRepo.findExactlyOneUserByUsername(username);
        if (users.size() > 0)
            return users.get(0);
        else
            return new User();
    }

    public User findByUserIdAndAccount(Long userId) {
        return userRepo.findByUserIdAndAccounts(userId);
    }

    public Set<User> findAll() {
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    public User findById(Long userId) {
        return userRepo.findByUserId(userId);
    }

    public User saveUser(User user) {
        if (user.getUserId() == null) {
            Account newAccount = new Account();
            newAccount.getUsers().add(user);
            user.getAccounts().add(newAccount);
            newAccount.setAccountName("Account #" + user.getAccounts().size());
            accountRepo.save(newAccount);
        }
        return userRepo.save(user);
    }

    public void delete(Long userId) {
        userRepo.deleteById(userId);
    }
}