package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.dto.Member;

import java.util.List;

public interface LevelUpDao {

    public Member createMember(Member member);

    public Member getMember(int id);

    public List<Member> getAllMembers();

    public int getPointsByCustId(int id);

    public void updateMember(int id, Member member);

    public void deleteMember(int id);

    public Member getMemberByCustomerId(int id);

    public void addPointsToMember(Member member);

}
