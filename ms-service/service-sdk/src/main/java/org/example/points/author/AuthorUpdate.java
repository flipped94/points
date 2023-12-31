package org.example.points.author;

import lombok.Data;
import org.example.points.common.Avatar;

@Data
public class AuthorUpdate {
    private String nickName;
    private String description;
    private Avatar avatar;
}
