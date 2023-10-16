package org.example.points.common.enums;

/**
 * <h1>是否枚举</h1>
 * 0: 否,<br/>
 * 1: 是
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
