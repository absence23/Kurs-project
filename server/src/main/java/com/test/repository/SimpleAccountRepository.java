package com.test.repository;

import com.test.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface SimpleAccountRepository extends JpaRepository<Account, Long> {
    Account findOneByEmail(String email);
}
