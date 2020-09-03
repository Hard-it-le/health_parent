package com.yjl.service;

import com.yjl.pojo.Member;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-08-14:37
 **/
public interface MemberService {
    /**
     * 根据手机号查询会员
     *
     * @param telephone
     * @return
     */
    public Member findByTelephone(String telephone);

    /**
     * 根据添加会员信息
     *
     * @param member
     */
    public void add(Member member);

    /**
     * 根据月份查询会员数量
     * @param months
     * @return
     */
    public List<Integer> findMemberCountByMonths(List<String> months);
}
