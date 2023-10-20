package org.example.points.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegister implements Serializable {
    private Integer authorId;
    private String nickName;
    private String email;
}
