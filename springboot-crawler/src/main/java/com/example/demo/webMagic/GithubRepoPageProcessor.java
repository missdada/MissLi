package com.example.demo.webMagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
	static int count = 0;
    @Override
    public void process(Page page) {
    	 //判断链接是否符合http://www.cnblogs.com/任意个数字字母-/p/7个数字.html格式
//        if(page.getUrl().toString().startsWith("https://blog.csdn.net/spencer_tseng/article/details/")){
//            //加入满足条件的链接
//            page.addTargetRequests(page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class='post_item_body']/h3/a/@href").all());
//        }else{                         
            //获取页面需要的内容
            System.out.println("抓取的内容=======>"+page.getHtml().xpath("//div[@class='htmledit_views']/p/a/text()").all());
            count ++;
//        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://blog.csdn.net/spencer_tseng/article/details/79106266").thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }

}
