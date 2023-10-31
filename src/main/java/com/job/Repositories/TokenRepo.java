package com.job.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entities.Token;

public interface TokenRepo extends JpaRepository<Token, Integer>{

	public Token findByEmail(String email);
}
