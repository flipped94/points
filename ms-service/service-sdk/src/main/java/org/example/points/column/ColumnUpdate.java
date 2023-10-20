package org.example.points.column;

import lombok.Data;
import org.example.points.common.Avatar;

@Data
public class ColumnUpdate {
    private String title;
    private String description;
    private Avatar avatar;
}
