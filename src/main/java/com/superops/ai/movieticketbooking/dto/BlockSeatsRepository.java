package com.superops.ai.movieticketbooking.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superops.ai.movieticketbooking.entity.BlockSeatsEntity;

public interface BlockSeatsRepository extends JpaRepository<BlockSeatsEntity, String> {

}
