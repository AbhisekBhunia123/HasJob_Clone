package com.job.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.job.entities.Job;

public interface JobsRepo extends JpaRepository<Job, Integer> {

	Job findFirstByOrganizationIdOrderById(int organizationId);
}
