package com.job.Daos;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.job.Repositories.OrganizationRepo;
import com.job.entities.Organization;

@Repository
public class OrganizationDao {
	@Autowired
	OrganizationRepo organizationRepo;
	
	public boolean createOrganization(String name,String url,String email,String collaborators,MultipartFile logo) {
		boolean isCreated = false;
		try {
			
			byte[] logoImage = logo.getBytes();
			Organization organization = new Organization();
			organization.setName(name);
			organization.setUrl(url);
			organization.setEmail(email);
			organization.setCollaborators(collaborators);
			organization.setLogo(logoImage);
			organizationRepo.save(organization);
			isCreated = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isCreated;
	}
	
	public Organization findByEmail(String email) {
		Organization organization = organizationRepo.findByEmail(email);
		return organization;
	}
	
	public String getImageAsBase64(int id) {
	    byte[] imageData = organizationRepo.findById(id).get().getLogo();
	    if (imageData != null) {
	        return Base64.getEncoder().encodeToString(imageData);
	    }
	    return null;
	}
	
	public List<Organization> getAllOrganization(){
		List<Organization> organizations = organizationRepo.findAll();
		return organizations;
	}

}
