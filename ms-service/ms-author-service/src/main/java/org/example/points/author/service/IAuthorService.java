package org.example.points.author.service;

import org.example.points.author.AuthorCreate;
import org.example.points.author.AuthorInfo;
import org.example.points.author.AuthorUpdate;
import org.example.points.author.entity.Author;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 作者表 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
public interface IAuthorService extends IService<Author> {

    Integer create(AuthorCreate authorCreate);

    AuthorInfo findByAccountId(Integer accountId);

    AuthorInfo updateAuthor(AuthorUpdate authorUpdate);
}
