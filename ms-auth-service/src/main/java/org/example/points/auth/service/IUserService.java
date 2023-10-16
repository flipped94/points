package org.example.points.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.auth.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
public interface IUserService extends IService<User> {


    User findByEmail(String email);
}
