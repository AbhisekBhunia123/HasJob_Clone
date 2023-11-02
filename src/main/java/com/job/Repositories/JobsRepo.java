package com.job.Repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.job.entities.Job;

public interface JobsRepo extends JpaRepository<Job, Integer> {

	Job findFirstByOrganizationIdOrderById(int organizationId);
	
	public List<Job> findByLocation(String location);
	
	@Query("SELECT j FROM Job j " +
				"LEFT JOIN j.organization o " +
		       "WHERE ((:type is null OR j.type IN :type) " +
		       "AND (:category is null OR j.category IN :category)"
		       + "AND (:location is null OR j.location IN :location)"
		       + "AND (:payType is null OR j.payType IN :payType)"
		       + "AND (:payRange is null OR j.payRange <= :payRange ))"
		       +" AND ((:searchText is null OR (LOWER(j.location) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.type) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.headline) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.description) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(o.name) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.category) LIKE CONCAT('%', LOWER(:searchText), '%'))))")
		List<Job> filterJob(@Param("type") String[] type,
		                               @Param("category") String[] category,
		                               @Param("location") String[] location,
		                               @Param("payType") String payType,
		                               @Param("searchText") String searchText,
		                               @Param("payRange") long payRange
		                              );
	
	@Query("SELECT j FROM Job j " +
				"LEFT JOIN j.organization o " +
		       "WHERE ((:type is null OR j.type IN :type) " +
		       "AND (:category is null OR j.category IN :category)"
		       + "AND (:location is null OR j.location IN :location)"
		       + "AND (:payType is null OR j.payType IN :payType))"
		       +" AND ((:searchText is null OR (LOWER(j.location) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.type) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.headline) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.description) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(o.name) LIKE CONCAT('%', LOWER(:searchText), '%')"
		       + "OR LOWER(j.category) LIKE CONCAT('%', LOWER(:searchText), '%'))))")
	List<Job> filterJobWithOutPay(@Param("type") String[] type,
            @Param("category") String[] category,
            @Param("location") String[] location,
            @Param("payType") String payType,
            @Param("searchText") String searchText
           );
}
