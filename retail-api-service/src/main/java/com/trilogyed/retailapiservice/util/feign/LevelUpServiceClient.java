package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.models.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/levelUp")
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

    @GetMapping("/customer/{id}")
    public Member getMemberByCustomerId(@PathVariable("id") int id);

    @PutMapping("/addpoints")
    public void addPointsToMember(@RequestBody Member member);

}
