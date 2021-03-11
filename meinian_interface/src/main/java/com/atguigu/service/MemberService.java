package com.atguigu.service;

import com.atguigu.pojo.Member;

import java.util.List;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/6
 */
public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void save(Member member);

    List<Integer> findMemberCountByMonth(List<String> months);
}
