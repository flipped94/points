package org.example.points.author;

import lombok.Builder;
import lombok.Data;
import org.example.points.common.Avatar;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthorInfo {
    private Integer _id;
    private String email;
    private Avatar avatar;
    private String description;
    private String nickName;
    private Integer column;
    private LocalDateTime createAt;
}
