package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class LevelUpDaoJdbcTemplateImpl implements LevelUpDao{
    private String INSERT_NEW_MEMBER =
            "insert into level_up (customer_id, points, member_date) " +
                    "values (?, ?, ?)";
    private String SELECT_MEMBER_BY_ID =
            "select * from level_up where level_up_id = ?";
    private String SELECT_ALL_MEMBERS =
            "select * from level_up";
    private String SELECT_POINTS_BY_CUST_ID =
            "select points from level_up where customer_id = ?";
    private String UPDATE_MEMBER =
            "update level_up set customer_id = ? , points = ? , member_date = ? where level_up_id = ?";
    private String DELETE_MEMBER =
            "delete from level_up where level_up_id = ?";
    private String SELECT_MEMBER_BY_CUSTOMER_ID =
            "select * from level_up where customer_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LevelUpDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Member createMember(Member member) {
        jdbcTemplate.update(INSERT_NEW_MEMBER,
                            member.getCustomerId(),
                            member.getPoints(),
                            member.getMemberDate());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        member.setLevelUpId(id);

        return member;
    }

    @Override
    public Member getMember(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_MEMBER_BY_ID,
                    this::mapRowToMember,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Member> getAllMembers() {
        try {
            return jdbcTemplate.query(
                    SELECT_ALL_MEMBERS,
                    this::mapRowToMember
            );
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public int getPointsByCustId(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_POINTS_BY_CUST_ID,
                    this::mapToInt,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Member getMemberByCustomerId(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_MEMBER_BY_CUSTOMER_ID,
                    this::mapRowToMember,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateMember(int id, Member member) {
        jdbcTemplate.update(UPDATE_MEMBER,
                            member.getCustomerId(),
                            member.getPoints(),
                            member.getMemberDate(),
                            member.getLevelUpId());
    }

    @Override
    @Transactional
    public void deleteMember(int id) {
        jdbcTemplate.update(DELETE_MEMBER,
                            id
        );

    }

    private Member mapRowToMember(ResultSet rs, int rowNumber)throws SQLException {
        Member member = new Member();
        member.setLevelUpId(rs.getInt("level_up_id"));
        member.setCustomerId(rs.getInt("customer_id"));
        member.setPoints(rs.getInt("points"));
        member.setMemberDate(rs.getDate("member_date").toLocalDate());

        return member;
    }

    private int mapToInt(ResultSet rs, int rowNumber) throws SQLException {
        int points = rs.getInt("points");

        return points;
    }

    @Override
    public void addPointsToMember(Member member) {
        int pointsToAdd = member.getPoints();

        Member member1 = jdbcTemplate.queryForObject(
                SELECT_MEMBER_BY_CUSTOMER_ID,
                this::mapRowToMember,
                member.getCustomerId()
        );

        member1.setPoints(member.getPoints() + member1.getPoints());

        updateMember(member1.getLevelUpId(), member1);
    }
}
