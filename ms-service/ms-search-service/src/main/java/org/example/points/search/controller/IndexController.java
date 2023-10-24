package org.example.points.search.controller;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/search/index")
public class IndexController {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private ArticleRepository articleRepository;

    @PostMapping("")
    public String create() {
        final boolean b = elasticsearchRestTemplate.indexOps(Article.class).create();
        if (b) {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/doc")
    public String addDoc() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("英语语法的第一个坑if从句，你掉进去了吗？（一）");
        article.setExcerpt("我在学校学语法的时候，最让我头疼的就是各种语法的专有名词，状语，同位语，动名词，动词分词，这些听上去很高大上的称谓到底是什么？这篇文章会分三次推送给，先从什么是状语说起，然后再深入聊聊if的正常语态和虚拟语态句型。 英语中最早学习的复杂句型…");
        article.setContent("我在学校学语法的时候，最让我头疼的就是各种语法的专有名词，状语，同位语，动名词，动词分词，这些听上去很高大上的称谓到底是什么？这篇文章会分三次推送给，先从什么是状语说起，然后再深入聊聊if的正常语态和虚拟语态句型。\n" +
                "\n" +
                "\n" +
                "\n" +
                "英语中最早学习的复杂句型之一就是状语从句，比如“如果。。。。，我就会。。。。”（语法书管这个叫条件状语从句），“当我。。。。的时候，他在。。。。”（这个叫时间状语从句），我记得我小时候学这两个句型总是会搞不清楚主从句分别该用什么时态，因为那时候是死记硬背的。但现在回头总结一下，其实是可以发现规律的。在总结规律之前，我们得先来讨论一下状语到底是个什么东西。\n" +
                "\n" +
                "\n" +
                "\n" +
                "我理解的状语就是描述状态的语句，说的更直白一点，就像是给故事设定一个场景。你做某个动作的先决条件是什么（if），你做某个动作在时间线上的逻辑是什么（when，after,before，as,while等等）。因此可以推论，状语从句的结构就是主线句（做的动作）+状语从句（做动作伴随的状态，比如要达成的条件，或者时间地点等）。\n" +
                "\n" +
                "比如看这句话： if it rains tomorrow, I won’t go to the park. 初学者对这句话肯定很多疑问，明明有tomorrow，为什么不用将来时，要用一般现在时？而后面这句话为什么又用了将来时？其实奥秘就是，if it rains tomorrow是一个触发未来某个主线动作的条件，这个条件本身是没有时间概念的，一旦达成了条件，就自动触发主线动作，如果没达成，就不触发。即：如果下雨，就不会去公园；如果不下雨，就（也许）会去公园。那么后面这个主线动作用将来时就很好理解了，它表示了主人公自己的意愿，will的意思是表达主观意愿，所以才有be willing to do sth.（愿意做某事）这个说法。\n" +
                "\n" +
                "因此这句话可以这么理解：如果满足了某个设定的条件，则触发未来某个动作。\n" +
                "\n" +
                "\n" +
                "\n" +
                "后记：这是不是很像简单的数学逻辑？如果a=b 那么a + b = 2a= 2b。If it rains tomorrow, i won’t go to the park.这句话你就可以把他理解成这样一个数学逻辑，如果满足条件x，则触发结果y。条件是一个客观存在，结果表达主观意愿（will对应原文的“会”）。\n" +
                "\n" +
                "\n" +
                "\n" +
                "如果看到这里你觉得if句型原来这么简单，那你就太天真了，keke。下一篇文章中我会给大家讲解if句型更灵活的时态用法，欢迎关注我的公众号英语小黑课，获得自动推送~");
        return "success";
    }
}
