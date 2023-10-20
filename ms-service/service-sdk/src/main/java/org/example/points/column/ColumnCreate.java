package org.example.points.column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnCreate {

    private String title;

    private String description;

    private Integer authorId;
}
