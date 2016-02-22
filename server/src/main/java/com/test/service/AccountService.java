package com.test.service;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.test.account.Account;
import com.test.repository.SimpleAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountService implements UserDetailsService {
	
	@Autowired
	private SimpleAccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	public EntityManager entityManager;

	@PostConstruct
	protected void initialize() {}

	@Transactional
	public Object update(Object object)
	{
		return entityManager.merge(object);
	}

	@Transactional
	public Object persist(Object object)
	{
		entityManager.persist(object);
		return object;
	}


	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
    	accountRepository.save(account);
		return account;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findOneByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}

	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));
	}

	private User createUser(Account account) throws UsernameNotFoundException{
		try {
			return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
		}catch (Exception e){
			throw new UsernameNotFoundException("Such use is already exist");
		}
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
