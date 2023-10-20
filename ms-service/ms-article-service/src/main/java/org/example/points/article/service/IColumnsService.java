package org.example.points.article.service;

import org.example.points.article.entity.Column;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.column.ColumnCreate;
import org.example.points.column.ColumnInfo;
import org.example.points.column.ColumnReqV;
import org.example.points.column.ColumnUpdate;
import org.example.points.common.vo.PageResult;

/**
 * <p>
 * 专栏 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
public interface IColumnsService extends IService<Column> {

    Integer create(ColumnCreate create);

    PageResult<ColumnInfo> columns(ColumnReqV columnReqV);

    ColumnInfo findById(Integer id);

    ColumnInfo findByAuthorId(Integer authorId);

    ColumnInfo updateColumn(Integer columnId, ColumnUpdate update);
}
