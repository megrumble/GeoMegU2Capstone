package com.trilogyed.levelupservice.controller;

import com.trilogyed.levelupservice.dto.Member;
import com.trilogyed.levelupservice.exception.NotFoundException;
import com.trilogyed.levelupservice.service.LevelUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LevelUpController {

    @Autowired
    LevelUpService service;

    public LevelUpController(LevelUpService service) {
        this.service = service;
    }

    @RequestMapping(value = "/levelup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Member createMember(@Valid @RequestBody Member member) {
        member = service.addMember(member);
        return member;
    }

    @RequestMapping(value = "/levelup/member/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Member getMember(@PathVariable("id") int id) {
        Member member = service.findMember(id);
        if (member == null) {
            throw new NotFoundException("No member found for id " + id);
        }
        return member;
    }

    @RequestMapping(value = "/levelup", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Member> getAllMembers() {
        List<Member> memberList = service.findAllMembers();
        if (memberList.isEmpty()) {
            throw new NotFoundException("No members found.");
        }
        return memberList;
    }

    @RequestMapping(value = "/levelup/points/{custId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public int getPointsByCustId(@PathVariable("custId") int id) {
        return service.getPointsByCustId(id);
    }

    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMember(@PathVariable("id") int id,@Valid @RequestBody Member member) {
        if (member.getLevelUpId() != id) {
            throw new IllegalArgumentException("ID in path variable must match member ID");
        }
        service.updateMember(id, member);
    }

    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("id") int id) {
        service.deleteMember(id);
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Member getMemberByCustomerId(@PathVariable("id") int id) {
        Member member = service.findMemberByCustomerId(id);
        if (member == null) {
            throw new NotFoundException("No member found for customer id " + id);
        }
        return member;
    }

    @RequestMapping(value = "/levelup/addpoints", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addPointsToMember(@RequestBody Member member){
        System.out.println(member.toString() + " in level up service");
        if (member == null){
//            throw new IllegalArgumentException("Member cannot be null in order to add points");
            System.out.println("member is null");
        }
        service.addPointsToMember(member);
    }
}
