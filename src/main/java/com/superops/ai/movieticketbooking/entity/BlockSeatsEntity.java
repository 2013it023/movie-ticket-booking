package com.superops.ai.movieticketbooking.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "block_seats")
@Entity
@Data
@NoArgsConstructor
public class BlockSeatsEntity {

	@Id
	public String id;

	public String userId;

	public String state;

	public String timeStamp;

}
