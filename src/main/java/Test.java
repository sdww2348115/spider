/**
 * Created by sdww on 15-11-18.
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static final String urlBase = "http://www.douban.com/tag/%E7%88%B1%E6%83%85/movie";

    public void process(Page page) {
        /*page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));*/
        //page.addTargetRequests(page.getHtml().links().regex("http:"));

        List<String> allNext = page.getHtml().$(".paginator a", "herf").all();
        for(String url:allNext) {
            System.out.println(allNext.size() + url);
            System.out.flush();
        }
        page.addTargetRequests(page.getHtml().$(".title", "href").all());
        //String author = page.getHtml().$("#area-title-view .name", "innerHtml").toString();
        //page.putField("author", author);
        //System.out.println(author);
        //pri(author);
        //System.out.println(author);*/
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args){
        Spider.create(new Test()).addUrl(urlBase).thread(5).run();
    }
}
