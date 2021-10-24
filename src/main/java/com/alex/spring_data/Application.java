package com.alex.spring_data;

import com.alex.spring_data.entity.Account;
import com.alex.spring_data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
//        jdbcTemplate.execute("INSERT INTO Account " +
//                "(id, name, email, bill)" +
//                "VALUES (1, 'Lori', 'lori@mail.ru', '2000')");
//
////        Map<String, Object> resultSet = jdbcTemplate.queryForMap("SELECT * FROM Account");
////        System.out.println(resultSet);
//        Account accountById = findAccountById(1L);
//        System.out.println(accountById);

        for (int i = 0; i < 10; i++) {
            Account account = new Account(null, "Alex#" + i, "alex@mail.ru", 2000 + i);
            accountRepository.save(account);
        }

        System.out.println(accountRepository.findAccountByName("Alex#7"));
        System.out.println(accountRepository.setNameFor(3L, "Wilka"));
        System.out.println(accountRepository.findAccountByName("Wilka"));
        System.out.println(accountRepository.findAccount("Alex#2", 2002));

    }

    private Account findAccountById(Long accId) {
        String query = "SELECT * FROM Account WHERE id=%s";
        Map<String, Object> resultSet = jdbcTemplate.queryForMap(String.format(query, accId));
        System.out.println("findAccountById START");
        System.out.println(query);
        System.out.println(resultSet);
        System.out.println("findAccountById END");
        Account account = new Account();
        account.setId(accId);
        account.setName((String) resultSet.get("name"));
        account.setEmail((String) resultSet.get("email"));
        account.setBill((Integer) resultSet.get("bill"));
        return account;
    }
}
