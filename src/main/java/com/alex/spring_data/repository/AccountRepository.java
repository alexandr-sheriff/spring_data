package com.alex.spring_data.repository;

import com.alex.spring_data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByName(String name);

    @Query(nativeQuery = true, value = "select * from Account where name = :name and bill = :bill")
    Account findAccount(@Param("name") String name, @Param("bill") Integer bill);

    @Modifying
    @Query(nativeQuery = true, value = "update Account set name = ?2 where id = ?1")
    int setNameFor(Long id, String name);
}
