package com.Distributed.account_service.mapper;


import com.Distributed.account_service.dto.subscription.PlanResponse;
import com.Distributed.account_service.dto.subscription.SubscriptionResponse;
import com.Distributed.account_service.entity.Plan;
import com.Distributed.account_service.entity.Subscription;
import com.Distributed.common_lib.dto.PlanDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanDto toPlanResponse (Plan plan);
}
