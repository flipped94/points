package org.example.points.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avatar implements Serializable {

    private Integer _id;
    private String url;
    private LocalDateTime createdAt;

}
