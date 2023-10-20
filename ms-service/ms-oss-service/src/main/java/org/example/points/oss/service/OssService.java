package org.example.points.oss.service;

import org.example.points.common.vo.OssVO;
import org.springframework.web.multipart.MultipartFile;


public interface OssService {

    OssVO upload(MultipartFile file);
}
