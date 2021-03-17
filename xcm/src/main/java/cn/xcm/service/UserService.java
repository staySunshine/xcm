package cn.xcm.service;

import cn.xcm.mapper.BalanceMapper;
import cn.xcm.mapper.UserMapper;
import cn.xcm.model.Balance;
import cn.xcm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO
 *
 * @author 大航
 * @version 1.0
 * @date 2021/1/15 16:11
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BalanceMapper balanceMapper;
    public User findByOpenId(String openid) {
        return userMapper.selectByOpenId(openid);
    }

    public void save(User user) {
        userMapper.insert(user);
    }

    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    public int addBalance(Balance balance) {
        Date date = new Date();
        balance.setDate(date);
        return balanceMapper.insert(balance);
    }

    public BigDecimal getIncome() {
        return balanceMapper.getIncome();
    }

    public BigDecimal getOutcome() {
        return balanceMapper.getOutcome();
    }
}
