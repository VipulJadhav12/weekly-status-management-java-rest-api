package com.afourathon.weekly_status_management_rest_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afourathon.weekly_status_management_rest_api.data.entity.MailingList;
import com.afourathon.weekly_status_management_rest_api.data.payloads.repository.MailingListRepository;

@Service
public class JpaMailingListService implements MailingListService {

	@Autowired
	MailingListRepository mailingListRepository;

	@Override
	public boolean checkIfEmailExistsById(Long mailId) {
		return mailingListRepository.existsById(mailId);
	}
	
	@Override
	public Optional<MailingList> findEmailById(Long mailId) {
		return mailingListRepository.findById(mailId);
	}

}
