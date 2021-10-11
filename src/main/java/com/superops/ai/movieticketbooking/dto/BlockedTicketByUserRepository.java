package com.superops.ai.movieticketbooking.dto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedTicketByUserRepository
		extends CrudRepository<com.superops.ai.movieticketbooking.entity.BlockedTicketByUser, String> {

}
