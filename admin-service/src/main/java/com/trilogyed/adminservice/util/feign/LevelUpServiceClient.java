package com.trilogyed.adminservice.util.feign;

import com.trilogyed.adminservice.model.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/levelup")
@FeignClient(name = "level-up-service")
public interface LevelUpServiceClient {

    @PostMapping
    public Member createMember(@RequestBody @Valid Member member);

    @GetMapping("/member/{id}")
    public Member getMember(@PathVariable int id);

    @GetMapping
    public List<Member> getAllMembers();

    @GetMapping("/points/{custId}")
    public int getPointsByCustId(@PathVariable("custId") int id);

    @PutMapping("/{id}")
    public void updateMember(@PathVariable int id, @RequestBody @Valid Member member);

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable int id);
}
