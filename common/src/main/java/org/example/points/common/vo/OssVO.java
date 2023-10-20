package org.example.points.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssVO implements Serializable {
    private String url;
    private String filename;
    private String extname;
    private LocalDateTime createdAt;
}
