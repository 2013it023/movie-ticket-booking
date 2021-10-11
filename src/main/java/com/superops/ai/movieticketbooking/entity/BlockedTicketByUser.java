package com.superops.ai.movieticketbooking.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash(value = "blockedTicketByUser")
public class BlockedTicketByUser {

	@Id
	@Indexed
	private String userId;

	private List<String> userSelectedSeatList;
}
