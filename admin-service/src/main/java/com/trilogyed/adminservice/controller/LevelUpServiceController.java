package com.trilogyed.adminservice.controller;

import com.trilogyed.adminservice.exception.NotFoundException;
import com.trilogyed.adminservice.model.Member;
import com.trilogyed.adminservice.service.AdminServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
public class LevelUpServiceController {

    @Autowired
    AdminServiceLayer service;

    public LevelUpServiceController(AdminServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/levelUp/levelup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Member createMember(@Valid @RequestBody Member member, Principal principal) {
        member = service.addMember(member);
        return member;
    }

    @RequestMapping(value = "/levelUp/levelup/member/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Member getMember(@PathVariable("id") int id, Principal principal) {
        Member member = service.findMember(id);
        if (member == null) {
            throw new NotFoundException("No member found for id " + id);
        }
        return member;
    }

    @RequestMapping(value = "/levelUp/levelup", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Member> getAllMembers(Principal principal) {
        List<Member> memberList = service.findAllMembers();
        if (memberList.isEmpty()) {
            throw new NotFoundException("No members found.");
        }
        return memberList;
    }

    @RequestMapping(value = "/levelUp/levelup/points/{custId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public int getPointsByCustId(@PathVariable("custId") int id, Principal principal) {
        return service.findPointsByCustomerId(id);
    }

    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMember(@PathVariable("id") int id,@Valid @RequestBody Member member, Principal principal) {
        if (member.getLevelUpId() != id) {
            throw new IllegalArgumentException("ID in path variable must match member ID");
        }
        service.updateMember(id, member);
    }

    @RequestMapping(value = "/levelUp/levelup/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("id") int id, Principal principal) {
        service.deleteMember(id);
    }
}
