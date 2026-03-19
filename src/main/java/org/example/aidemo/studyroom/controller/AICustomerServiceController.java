package org.example.aidemo.studyroom.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.aidemo.ailangchain.bean.ChatForm;
import org.example.aidemo.common.util.ThreadLocalUtil;
import org.example.aidemo.studyroom.assistant.AICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
//@RequestMapping("/api/aiservice")
@RequestMapping("/aiservice")
public class AICustomerServiceController {
    @Autowired
    private AICustomerService AICustomerService;

    @Operation(summary = "对话")
    //@PostMapping("/chat")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> xiaozhi(@RequestBody ChatForm chatForm){
        //todo 用户id待解决


        Map<String, Object> claims = new HashMap<>();
        claims.put("id", Math.toIntExact(chatForm.getMemoryId()));
        ThreadLocalUtil.set(claims);

        if (chatForm.getMessage().trim()!=null)
            try {
                return AICustomerService.chat(chatForm.getMemoryId(), chatForm.getMessage());
            } finally {
                ThreadLocalUtil.remove();
            }
//        return AICustomerService.chat(chatForm.getMemoryId(),chatForm.getMessage());

        else
            return Flux.just("输入内容无效");
    }
}
