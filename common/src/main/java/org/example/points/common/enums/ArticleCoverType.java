package org.example.points.common.enums;

/**
 * <h1>文章封面类型 枚举</h1>
 *  1: 单图,<br/>
 *  2: 纯文字
 */
public enum ArticleCoverType {
    ONE_IMAGE(1, "单图"),
    WORDS(2, "纯文字");

    public final Integer type;
    public final String value;

    ArticleCoverType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}