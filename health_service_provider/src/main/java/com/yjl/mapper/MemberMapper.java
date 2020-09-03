package com.yjl.mapper;

import com.yjl.pojo.Member;

/**
 * @author yjl
 * @create 2020-08-07-10:03
 **/
public interface MemberMapper {
    /**
     * 根据手机号查询会员
     *
     * @param telephone
     * @return
     */
    public Member findByTelephone(String telephone);

    /**
     * 新增会员
     *
     * @param member
     */
    public void add(Member member);

    /**
     * 根据日期统计会员数，统计指定日期之前的会员数
     *
     * @param date
     * @return
     */
    public Integer findMemberCountBeforeDate(String date);

    /**
     * 根据日期统计会员数，统计指定日期之后的会员数
     *
     * @param date
     * @return
     */
    public Integer findMemberCountAfterDate(String date);

    /**
     * 根据日期统计会员数
     *
     * @param today
     * @return
     */
    public Integer findMemberCountByDate(String today);

    /**
     * 总会员数
     *
     * @return
     */
    public Integer findMemberTotalCount();


}
