package org.example.points.common.utils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class MarkdownUtil {

    private static final Parser parser;

    private static final HtmlRenderer renderer;

    private static final TextContentRenderer textContentRenderer;

    static {
        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();
        textContentRenderer = TextContentRenderer.builder().build();
    }

    /**
     * markdown 转 html
     *
     * @param markdown markdown 内容
     * @return html 内容
     */
    public static String markdownToHtml(String markdown) {
        final Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    public static String markdownToPlainText(String markdown) {
        final Node document = parser.parse(markdown);
        return textContentRenderer.render(document);
    }
}
