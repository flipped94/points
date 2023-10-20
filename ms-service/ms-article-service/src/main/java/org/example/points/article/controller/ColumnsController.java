package org.example.points.article.controller;

import org.example.points.article.service.IColumnsService;
import org.example.points.column.ColumnInfo;
import org.example.points.column.ColumnReqV;
import org.example.points.common.vo.CommonResponse;
import org.example.points.common.vo.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 专栏 前端控制器
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
@RestController
@RequestMapping("/columns")
public class ColumnsController {

    @Resource
    private IColumnsService columnsService;

    @GetMapping("")
    public CommonResponse<PageResult<ColumnInfo>> columns(ColumnReqV columnReqV) {
        PageResult<ColumnInfo> result = columnsService.columns(columnReqV);
        return new CommonResponse<>(200, "success", result);
    }

    @GetMapping("/{id}")
    public CommonResponse<ColumnInfo> column(@PathVariable("id") Integer id) {
        ColumnInfo result = columnsService.findById(id);
        return new CommonResponse<>(200, "success", result);
    }

    @GetMapping("/author/{authorId}")
    public CommonResponse<ColumnInfo> authorColumn(@PathVariable("authorId") Integer authorId) {
        ColumnInfo columnInfo = columnsService.findByAuthorId(authorId);
        return new CommonResponse<>(200, "success", columnInfo);
    }
}
