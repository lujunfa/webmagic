package org.patric.webmagic.demon2;

import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * @Author: lujunfa  2018/12/17 14:59
 */
public class WeiXinPaqu implements PageProcessor {
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

        //使用css选择器，获取到指定dom元素的下的所有图片url链接地址
        List<String> imgSrc = (List<String>)page.getHtml().$("[data-copyright='0']", "data-src").all();

        //通过xpath定位元素，并输出标签文本内容
        List<String> content = (List<String>)page.getHtml().xpath("section/p/html()").all(); //或者text()
        String html = page.getHtml().toString();

        System.out.println("html:"+html);

        //将该网页的图片通过网络爬到本地
        for(String imgsrc : imgSrc){
            try {
                FileUtils.copyURLToFile(new URL(imgsrc), new File("C:\\Users\\13574\\Desktop\\爬虫图片\\"
                        + UUID.randomUUID()+".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        content.forEach(System.out::println);
        System.err.println("爬到的图片: " + imgSrc);

    }

    /*4.爬取*/
    public static void main(String[] args) {
        /*添加爬取的url链接，开启5个线程爬取*/
        Spider spider = Spider.create(new WeiXinPaqu())
                .addUrl("https://mp.weixin.qq.com/s/nrfOyt9jlUzwlCmqI0dnRA")
                .thread(5);
        /*爬虫启动*/
        spider.run();
    }
}
