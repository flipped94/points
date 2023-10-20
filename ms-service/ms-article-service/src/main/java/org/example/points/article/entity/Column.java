package org.example.points.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.example.points.column.ColumnInfo;
import org.example.points.common.Avatar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
@Getter
@Setter
@TableName("t_columns")
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 昵称
     */
    private String title;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    public static ColumnInfo toInfo(Column column) {
        return ColumnInfo.builder()
                ._id(column.id)
                .createdAt(column.createdTime)
                .author(column.authorId)
                .avatar(new Avatar(null, column.avatar, null))
                .description(column.description)
                .title(column.title)
                .build();
    }

    public static List<ColumnInfo> toInfo(List<Column> columns) {
        List<ColumnInfo> res = new ArrayList<>();
        columns.forEach(column -> res.add(Column.toInfo(column)));
        return res;
    }
}
