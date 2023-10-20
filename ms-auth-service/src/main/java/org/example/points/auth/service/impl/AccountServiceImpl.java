package org.example.points.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.auth.entity.Account;
import org.example.points.auth.mapper.AccountMapper;
import org.example.points.auth.service.IAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public Account findByEmail(String email) {
        return accountMapper.findByEmail(email);
    }
}
