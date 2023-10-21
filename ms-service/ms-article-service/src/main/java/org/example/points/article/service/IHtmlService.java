package org.example.points.article.service;

public interface IHtmlService {

    /**
     * markdown 转 html
     *
     * @param markdown markdown 内容
     * @return html 内容
     */
    String markdownToHtml(String markdown);
}
