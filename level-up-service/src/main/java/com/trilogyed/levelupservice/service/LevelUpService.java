package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LevelUpService {

    @Autowired
    LevelUpDao levelUpDao;

    public LevelUpService(LevelUpDao levelUpDao) {
        this.levelUpDao = levelUpDao;
    }

    public Member addMember(Member member){

        return levelUpDao.createMember(member);

    }

    public Member findMember(int id){

        return levelUpDao.getMember(id);

    }

    public List<Member> findAllMembers() {

        return levelUpDao.getAllMembers();

    }

    public int getPointsByCustId(int id) {

        return levelUpDao.getPointsByCustId(id);

    }

    public void updateMember(int id, Member member) {

        levelUpDao.updateMember(id, member);

    }

    public void deleteMember(int id) {

        levelUpDao.deleteMember(id);

    }
}
