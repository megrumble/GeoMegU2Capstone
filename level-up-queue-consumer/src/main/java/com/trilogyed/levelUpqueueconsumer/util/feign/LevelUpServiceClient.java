package com.trilogyed.levelUpqueueconsumer.util.feign;

import com.trilogyed.levelUpqueueconsumer.util.messages.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/levelup/addpoints")
@FeignClient(name = "level-up-service")
public interface LevelUpServiceClient {

//    @PostMapping
//    public Member addMember(@RequestBody Member member);
//
//    @PutMapping("/{id}")
//    public void updateMember(@PathVariable int id, @RequestBody Member member);

    @PutMapping
    public void addPointsToMember(@RequestBody Member member);
}
