package org.example.points.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private long page;            // 当前页数
    private long total;            // 总页数
    private List<T> rows;        // 每行显示的内容
}
