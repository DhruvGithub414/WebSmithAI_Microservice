package com.Distributed.account_service.repository;

//import com.webagent.projects.websmith.entity.Plan;
import com.Distributed.account_service.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByStripePriceId(String id);
}
