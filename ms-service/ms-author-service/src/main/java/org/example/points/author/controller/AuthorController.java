package org.example.points.author.controller;

import org.example.points.author.AuthorCreate;
import org.example.points.author.AuthorInfo;
import org.example.points.author.AuthorUpdate;
import org.example.points.author.service.IAuthorService;
import org.example.points.common.vo.CommonResponse;
import org.example.points.filter.AccessContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 作者表 前端控制器
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Resource
    private IAuthorService authorService;

    @PostMapping("")
    public CommonResponse<String> create(@RequestBody @Validated AuthorCreate authorCreate) {
        Integer id = authorService.create(authorCreate);
        return new CommonResponse<>(200, "success");
    }

    @GetMapping("/current")
    public CommonResponse<AuthorInfo> current() {
        final Integer accountId = AccessContext.getLoginUserInfo().getId();
        AuthorInfo current = authorService.findByAccountId(accountId);
        return new CommonResponse<>(200, "success", current);
    }

    @GetMapping("/{accountId}")
    public CommonResponse<AuthorInfo> author(@PathVariable("accountId") Integer accountId) {
        final AuthorInfo authorInfo = authorService.findByAccountId(accountId);
        return new CommonResponse<>(200, "success", authorInfo);
    }


    @PatchMapping("")
    public CommonResponse<AuthorInfo> author(@RequestBody @Validated AuthorUpdate authorUpdate) {
        AuthorInfo authorInfo = authorService.updateAuthor(authorUpdate);
        return new CommonResponse<>(200, "success", authorInfo);
    }
}
