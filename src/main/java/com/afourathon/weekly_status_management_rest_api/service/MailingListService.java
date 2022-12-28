package com.afourathon.weekly_status_management_rest_api.service;

import java.util.Optional;

import com.afourathon.weekly_status_management_rest_api.data.entity.MailingList;

public interface MailingListService {
	
	public boolean checkIfEmailExistsById(Long mailId);
	
	public Optional<MailingList> findEmailById(Long mailId);
	
}
