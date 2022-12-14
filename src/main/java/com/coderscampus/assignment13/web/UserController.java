package com.coderscampus.assignment13.web;


import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.AddressService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
   
    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {
        model.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        Set<User> users = userService.findAll();
        model.put("users", users);
        if (users.size() == 1) {
            model.put("user", users.iterator().next());
        }
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        model.put("users", Arrays.asList(user));
        model.put("user", user);
        model.put("address", user.getAddress());
        model.put("accounts", user.getAccounts());
        return "users";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(User user) {
        user.getAddress().setUserId(user.getUserId());
        userService.saveUser(user);
        return "redirect:/users/" + user.getUserId();
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }

    @PostMapping("/users/{userId}/accounts")
    public String createAccount(@PathVariable Long userId) {
        User user = userService.findById(userId);
        Account account = accountService.createAccount(user);
        return "redirect:/users/" + user.getUserId() + "/accounts/" + account.getAccountId();
    }

    @GetMapping("/users/{userId}/accounts/{accountId}")
    public String getAccount(ModelMap modelMap, @PathVariable Long userId, @PathVariable Long accountId) {
        modelMap.put("user", userService.findById(userId));
        modelMap.put("account", accountService.findByAccountId(accountId));
        return "accounts";
    }

    @PostMapping("/users/{userId}/accounts/{accountId}")
    public String postAccount(Account account, User user) {
        accountService.saveAccount(account);
        return "redirect:/users/" + user.getUserId() + "/accounts/" + account.getAccountId();
    }
}