package org.example.points.oss.controller;

import org.example.points.common.vo.CommonResponse;
import org.example.points.common.vo.OssVO;
import org.example.points.oss.service.OssService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RequestMapping("/oss")
@RestController
public class OssController {

    @Resource
    private OssService ossService;

    @PostMapping("/upload")
    public CommonResponse<OssVO> upload(MultipartFile file) {
        final OssVO ossVO = ossService.upload(file);
        return new CommonResponse<>(200, "success", ossVO);
    }

}
