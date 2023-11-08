package org.example.points.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.article.entity.Column;
import org.example.points.article.mapper.ColumnsMapper;
import org.example.points.article.service.IColumnsService;
import org.example.points.column.ColumnCreate;
import org.example.points.column.ColumnInfo;
import org.example.points.column.ColumnReqV;
import org.example.points.column.ColumnUpdate;
import org.example.points.common.vo.PageResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 专栏 服务实现类
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
@Service("columnsService")
public class ColumnsServiceImpl extends ServiceImpl<ColumnsMapper, Column> implements IColumnsService {

    @Override
    public Integer create(ColumnCreate create) {
        Column column = new Column();
        column.setTitle(create.getTitle());
        column.setDescription(create.getDescription());
        column.setAuthorId(create.getAuthorId());
        column.setCreatedTime(LocalDateTime.now());
        return this.baseMapper.insert(column);
    }

    @Override
    public PageResult<ColumnInfo> columns(ColumnReqV columnReqV) {
        final Page<Column> columnPage = this.baseMapper.selectPage(new Page<>(columnReqV.getCurrentPage(), columnReqV.getPageSize()), null);
        final List<Column> records = columnPage.getRecords();
        final List<ColumnInfo> infos = Column.toInfo(records);
        return new PageResult<>(columnPage.getCurrent(), columnPage.getSize(), columnPage.getPages(), infos);
    }

    @Override
    public ColumnInfo findById(Integer id) {
        final Column column = getById(id);
        if (Objects.isNull(column)) {
            return null;
        }
        return Column.toInfo(column);
    }

    @Override
    public ColumnInfo findByAuthorId(Integer authorId) {
        QueryWrapper<Column> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", authorId);
        final Column column = this.baseMapper.selectOne(queryWrapper);
        return Column.toInfo(column);
    }

    @Override
    public ColumnInfo updateColumn(Integer columnId, ColumnUpdate update) {
        final Column column = getById(columnId);
        if (Objects.isNull(column)) {
            return null;
        }
        column.setAvatar(update.getAvatar().getUrl());
        column.setTitle(update.getTitle());
        column.setDescription(update.getDescription());
        column.setUpdatedTime(LocalDateTime.now());
        updateById(column);
        return Column.toInfo(column);
    }
}
