package org.example.points.column;

import lombok.Builder;
import lombok.Data;
import org.example.points.common.Avatar;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ColumnInfo implements Serializable {
    private Integer _id;
    private String title;
    private String description;
    private Avatar avatar;
    private Integer author;
    private LocalDateTime createdAt;
}
