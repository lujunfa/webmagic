package org.patric.webmagic.demon1;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * @Author: lujunfa  2018/12/14 16:34
 */
public class SelecttableDemon implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        System.out.println("页面元素:"+page.getHtml().css("span.kwd").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SelecttableDemon())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://www.runoob.com/design-pattern/builder-pattern.html")
                //开启5个线程抓取
                .addPipeline(new ConsolePipeline())
                .thread(5)
                .run();
    }
}
