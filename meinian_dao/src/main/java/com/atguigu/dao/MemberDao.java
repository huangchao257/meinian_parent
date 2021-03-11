package com.atguigu.dao;

import com.atguigu.pojo.Member;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/5
 */
public interface MemberDao {
    Member findMemberByTelephone(String telephone);

    void save(Member member);

    Integer findMemberCountByMonth(String month);

    Integer findMemberCountBeforeDate(String date);

    int getTodayNewMember(String date);

    int getTotalMember();

    int getThisWeekAndMonthNewMember(String date);

}
