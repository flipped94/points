package org.example.points.auth.mapper;

import org.example.points.auth.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
public interface AccountMapper extends BaseMapper<Account> {

    Account findByEmail(String email);
}
