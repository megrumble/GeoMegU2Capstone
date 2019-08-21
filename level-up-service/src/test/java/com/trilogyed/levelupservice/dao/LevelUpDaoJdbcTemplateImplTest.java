package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.dto.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LevelUpDaoJdbcTemplateImplTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {
        levelUpDao.getAllMembers().stream()
                .forEach(mem -> levelUpDao.deleteMember(mem.getLevelUpId()));
    }

    @Test
    public void createGetMember() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(30);
        member.setMemberDate(LocalDate.now());
        member = levelUpDao.createMember(member);

        Member member2 = levelUpDao.getMember(member.getLevelUpId());

        assertEquals(member, member2);

        member2 = levelUpDao.getMember(6);

        assertNull(member2);
    }

    @Test
    public void getAllMembers() {
        List<Member> members1 = new ArrayList<>();
        List<Member> members3 = levelUpDao.getAllMembers();

        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(30);
        member.setMemberDate(LocalDate.now());
        member = levelUpDao.createMember(member);
        members1.add(member);

        Member member2 = new Member();
        member2.setCustomerId(6);
        member2.setPoints(50);
        member2.setMemberDate(LocalDate.now());
        member2 = levelUpDao.createMember(member2);
        members1.add(member2);

        List<Member> members2 = levelUpDao.getAllMembers();

        assertEquals(members1, members2);
        assertTrue(members3.isEmpty());

    }

    @Test
    public void getPointsByCustId() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(30);
        member.setMemberDate(LocalDate.now());
        member = levelUpDao.createMember(member);

        int points = levelUpDao.getPointsByCustId(member.getCustomerId());

        assertEquals(30, points);

        points = levelUpDao.getPointsByCustId(6);

        assertEquals(0, points);
    }

    @Test
    public void updateMember() {
        Member member = new Member();
        member.setCustomerId(6);
        member.setPoints(50);
        member.setMemberDate(LocalDate.now());
        member = levelUpDao.createMember(member);

        member.setPoints(60);

        levelUpDao.updateMember(member.getLevelUpId(), member);

        Member member1 = levelUpDao.getMember(member.getLevelUpId());

        assertEquals(member, member1);

    }

    @Test
    public void deleteMember() {
        Member member = new Member();
        member.setCustomerId(6);
        member.setPoints(50);
        member.setMemberDate(LocalDate.now());
        member = levelUpDao.createMember(member);

        levelUpDao.deleteMember(member.getLevelUpId());

        Member member1 = levelUpDao.getMember(member.getLevelUpId());

        assertNull(member1);
    }
}