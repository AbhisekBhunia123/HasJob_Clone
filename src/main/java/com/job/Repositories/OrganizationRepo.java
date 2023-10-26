package com.job.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entities.Organization;

public interface OrganizationRepo extends JpaRepository<Organization, Integer> {
	
	public Organization findByEmail(String email);

}
