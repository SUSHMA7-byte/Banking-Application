package com.example.Banking.service.impl;

import com.example.Banking.entity.Account;
import com.example.Banking.Mapper.AccountMapper;
import com.example.Banking.dto.AccountDto;
import com.example.Banking.repository.AccountRepository;
import com.example.Banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Does Not Exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Does Not Exist"));


        double total = account.getBalance()+amount;

        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
            Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Does Not Exist"));


            double total = account.getBalance()- amount;

            account.setBalance(total);
            Account savedAccount = accountRepository.save(account);
            return AccountMapper.mapToAccountDto(savedAccount);
        }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Does Not Exist"));
        accountRepository.deleteById(id);
    }
}
