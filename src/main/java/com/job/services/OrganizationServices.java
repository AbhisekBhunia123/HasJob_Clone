package com.job.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.job.Daos.OrganizationDao;
import com.job.Repositories.OrganizationRepo;
import com.job.entities.Organization;

@Service
public class OrganizationServices {

	@Autowired
	OrganizationRepo organizationRepo;
	
	public Organization getOrganizationById(int id) {
		return organizationRepo.findById(id).get();
	}
	
	public boolean deleteOrganization(int id) {
		organizationRepo.deleteById(id);
		return true;
	}
	
	public boolean updateOrganization(String name,String url,MultipartFile logo,int id) {
		try {
			byte[] logoImage = logo.getBytes();
			Organization organization = organizationRepo.findById(id).get();
			organization.setName(name);
			organization.setUrl(url);
			if(!logo.isEmpty()) organization.setLogo(logoImage);
			organizationRepo.save(organization);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
