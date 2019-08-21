package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.dao.LevelUpDaoJdbcTemplateImpl;
import com.trilogyed.levelupservice.dto.Member;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LevelUpServiceTest {

    LevelUpDao levelUpDao;
    LevelUpService levelUpService;


    @Before
    public void setUp() throws Exception {
        setUpLevelUpDaoMock();

        levelUpService = new LevelUpService(levelUpDao);
    }

    @Test
    public void addFindMember() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.now());

        member = levelUpService.addMember(member);
        Member member2 = levelUpService.findMember(1);

        assertEquals(member, member2);

    }

    @Test
    public void findAllMembers() {
        List<Member> memberList = new ArrayList<>();

        Member member2 = new Member();
        member2.setLevelUpId(1);
        member2.setCustomerId(5);
        member2.setPoints(10);
        member2.setMemberDate(LocalDate.now());
        memberList.add(member2);

        Member member3 = new Member();
        member3.setLevelUpId(2);
        member3.setCustomerId(6);
        member3.setPoints(30);
        member3.setMemberDate(LocalDate.now());
        memberList.add(member3);

        List<Member> memberList2 = levelUpService.findAllMembers();

        assertEquals(memberList, memberList2);

    }

    @Test
    public void getPointsByCustId() {
        int points1 = 10;

        int points2 = levelUpService.getPointsByCustId(5);

        assertEquals(points1, points2);

    }

    @Test
    public void updateMember() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.now());
        member = levelUpService.addMember(member);

        member.setPoints(20);

        levelUpService.updateMember(member.getLevelUpId(), member);

        verify(levelUpDao).updateMember(any(Integer.class), any(Member.class));
    }

    @Test
    public void deleteMember() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.now());
        member = levelUpService.addMember(member);

        levelUpService.deleteMember(member.getLevelUpId());

        verify(levelUpDao).deleteMember(any(Integer.class));
    }

    public void setUpLevelUpDaoMock(){
        levelUpDao = mock(LevelUpDaoJdbcTemplateImpl.class);

        List<Member> memberList = new ArrayList<>();

        //add mock information here for tests
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.now());

        Member member2 = new Member();
        member2.setLevelUpId(1);
        member2.setCustomerId(5);
        member2.setPoints(10);
        member2.setMemberDate(LocalDate.now());
        memberList.add(member2);

        Member member3 = new Member();
        member3.setLevelUpId(2);
        member3.setCustomerId(6);
        member3.setPoints(30);
        member3.setMemberDate(LocalDate.now());
        memberList.add(member3);


        doReturn(member2).when(levelUpDao).createMember(member);
        doReturn(member2).when(levelUpDao).getMember(1);
        doReturn(memberList).when(levelUpDao).getAllMembers();
        doReturn(10).when(levelUpDao).getPointsByCustId(5);


    }
}