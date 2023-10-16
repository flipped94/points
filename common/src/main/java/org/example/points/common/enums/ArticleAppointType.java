package org.example.points.common.enums;

/**
 * <h1>文章发布操作类型 枚举</h1>
 * <h2>说明</h2>
 *  1. 定时发布,<br/>
 *  0: 即时发布<br/>
 */
public enum ArticleAppointType {
    TIMING(1, "文章定时发布 - 定时"),
    IMMEDIATELY(0, "文章立即发布 - 即时");

    public final Integer type;
    public final String value;

    ArticleAppointType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
