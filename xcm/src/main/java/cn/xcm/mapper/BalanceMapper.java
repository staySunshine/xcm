package cn.xcm.mapper;

import cn.xcm.model.Balance;

import java.math.BigDecimal;

public interface BalanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Balance record);

    int insertSelective(Balance record);

    Balance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Balance record);

    int updateByPrimaryKey(Balance record);

    BigDecimal getIncome();

    BigDecimal getOutcome();
}