package com.Distributed.intelligence_service.dto.chat;


import com.Distributed.common_lib.enums.MessageRole;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,


        String content,

        MessageRole role,

        List<ChatEventResponse>events,

//    String toolCalls; // JSON Array of Tools Called

        Integer tokensUsed,

        Instant createdAt
){
}
