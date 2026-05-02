package com.Distributed.intelligence_service.repository;

import com.Distributed.intelligence_service.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {
    Optional<ChatEvent> findBySagaId(String sagaId);
}
