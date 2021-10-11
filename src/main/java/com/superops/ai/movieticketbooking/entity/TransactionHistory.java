package com.superops.ai.movieticketbooking.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TransactionHistory {

	@Id
	private String userId;
	
	
}
