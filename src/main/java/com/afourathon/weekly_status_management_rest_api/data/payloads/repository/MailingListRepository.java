package com.afourathon.weekly_status_management_rest_api.data.payloads.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afourathon.weekly_status_management_rest_api.data.entity.MailingList;

public interface MailingListRepository extends JpaRepository<MailingList, Long> {
	
	public boolean existsById(Long id);

	public Optional<MailingList> findById(Long id);

}
