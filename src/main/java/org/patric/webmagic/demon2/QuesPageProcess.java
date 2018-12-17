package org.patric.webmagic.demon2;

import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/*1.实现PageProcessor接口*/
public class QuesPageProcess implements PageProcessor {
    /*2.设置抓取网站的相关配置*/
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public Site getSite() {
        // TODO Auto-generated method stub
        /*3.返回site*/
        return site;
    }

    /*5.爬取逻辑*/
    @Override
    public void process(Page page) {
        // TODO Auto-generated method stub
        page.putField("html", page.getHtml());

        //获取到指定dom元素的下的所有图片url链接地址，这里在获取的同时还保存在map中
        page.putField("imgSrc", page.getHtml().$("div.related-news img", "src").all());
        String pageHtml = page.getResultItems().get("html").toString();
        List<String> imgSrc = (List<String>)page.getResultItems().get("imgSrc");
        for(String imgsrc : imgSrc){
            try {
                FileUtils.copyURLToFile(new URL(imgsrc), new File("C:\\Users\\13574\\Desktop\\"
                        + UUID.randomUUID()+".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.err.println("爬到的页面内容: " + pageHtml);
        System.err.println("爬到的图片: " + imgSrc);

    }

    /*4.爬取*/
    public static void main(String[] args) {
        /*添加爬取的url链接，开启5个线程爬取*/
        Spider spider = Spider.create(new QuesPageProcess())
        .addUrl("https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9638962869149114766%22%7D&n_type=0&p_from=1")
        .thread(5)
                //添加持久层
        .addPipeline(new ConsolePipeline())
        .addPipeline(new MyDemonPipeLine());
        /*爬虫启动*/
        spider.run();
    }
}
