package com.job.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entities.Application;

public interface ApplicationRepo extends JpaRepository<Application, Integer>{

}
