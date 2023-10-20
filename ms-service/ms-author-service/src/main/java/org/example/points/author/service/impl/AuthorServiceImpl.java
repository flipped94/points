package org.example.points.author.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.example.points.author.AuthorCreate;
import org.example.points.author.AuthorInfo;
import org.example.points.author.AuthorUpdate;
import org.example.points.author.entity.Author;
import org.example.points.author.feignclient.ColumnClient;
import org.example.points.author.mapper.AuthorMapper;
import org.example.points.author.service.IAuthorService;
import org.example.points.column.ColumnInfo;
import org.example.points.common.vo.CommonResponse;
import org.example.points.filter.AccessContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 作者表 服务实现类
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
@Service("authorService")
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {

    @Resource
    private AuthorMapper authorMapper;

    @Resource
    private ColumnClient columnClient;

    @Override
    public Integer create(AuthorCreate authorCreate) {
        Author author = new Author();
        author.setNickName(authorCreate.getNickName());
        author.setAccountId(authorCreate.getAccountId());
        author.setEmail(authorCreate.getEmail());
        author.setCreatedTime(LocalDateTime.now());
        return authorMapper.insert(author);
    }

    @Override
    public AuthorInfo findByAccountId(Integer accountId) {
        Author author = authorMapper.findByAccountId(accountId);
        if (Objects.isNull(author)) {
            return null;
        }
        final AuthorInfo authorInfo = author.toAuthorInfo();
        final CommonResponse<ColumnInfo> columnResponse = columnClient.findByAuthorId(accountId);
        if (Objects.nonNull(columnResponse.getData())) {
            authorInfo.setColumn(columnResponse.getData().get_id());
        }
        return authorInfo;
    }

    @Override
    public AuthorInfo updateAuthor(AuthorUpdate authorUpdate) {
        final Integer authorId = AccessContext.getLoginUserInfo().getId();
        final Author author = authorMapper.findByAccountId(authorId);
        if (StringUtils.isNotBlank(authorUpdate.getAvatar().getUrl())) {
            author.setAvatar(authorUpdate.getAvatar().getUrl());
        }
        author.setDescription(authorUpdate.getDescription());
        author.setNickName(authorUpdate.getNickName());
        updateById(author);
        return author.toAuthorInfo();
    }
}
