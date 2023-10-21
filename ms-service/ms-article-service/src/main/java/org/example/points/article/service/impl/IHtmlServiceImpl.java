package org.example.points.article.service.impl;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.example.points.article.service.IHtmlService;
import org.springframework.stereotype.Service;

@Service("htmlService")
public class IHtmlServiceImpl implements IHtmlService {

    private static final Parser parser;

    private static final HtmlRenderer renderer;

    static {
        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();
    }

    /**
     * markdown 转 html
     *
     * @param markdown markdown 内容
     * @return html 内容
     */
    public String markdownToHtml(String markdown) {
        final Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
