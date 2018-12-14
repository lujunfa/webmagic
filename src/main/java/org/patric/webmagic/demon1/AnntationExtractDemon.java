package org.patric.webmagic.demon1;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.example.GithubRepo;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @Author: lujunfa  2018/12/14 17:22
 * 注解方式实现爬虫
 */

//TargetUrl是我们最终要抓取的URL，最终想要的数据都来自这里；
@TargetUrl("http://www.runoob.com/design-pattern/builder-pattern.html")
//而HelpUrl则是为了发现这个最终URL，我们需要访问的页面。
@HelpUrl("http://www.runoob.com/design-pattern/")
public class AnntationExtractDemon {
    @ExtractBy(value = "//h1[@class='entry-title public']/strong/a/text()", notNull = true)
    private String name;

    @ExtractByUrl("https://github\\.com/(\\w+)/.*")
    private String author;

    @ExtractBy("//div[@id='readme']/tidyText()")
    private String readme;

    public static void main(String[] args) {
        OOSpider.create(Site.me().setSleepTime(1000)
                , new ConsolePageModelPipeline(), GithubRepo.class)
                .addUrl("http://www.runoob.com/design-pattern/builder-pattern.html")
                .thread(5)
                .run();
    }

}
