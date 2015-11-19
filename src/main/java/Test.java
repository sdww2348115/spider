/**
 * Created by sdww on 15-11-18.
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Test implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static final String urlBase = "http://www.acfun.tv/v/list110/index_%s.htm";
    private static PrintWriter outPrint;

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

        page.addTargetRequests(page.getHtml().$(".title", "href").all());
        //System.out.println(page.getHtml().$(".title", "href").all());
        String author = page.getHtml().$("#area-title-view .name", "innerHtml").toString();
        //page.putField("author", author);
        //System.out.println(author);
        pri(author);
        System.out.println(author);
    }

    public Site getSite() {
        return site;
    }

    private synchronized void pri(String str) {
        outPrint.println(str);
        outPrint.flush();
    }

    public static void main(String[] args) throws Exception{
        try {
            File outFile = new File("out.txt");
            File logFile = new File("log.txt");
            outPrint = new PrintWriter(new FileWriter(outFile));
            PrintWriter logPrint = new PrintWriter(new FileWriter(logFile));
            for (int i = 0; i < 10000; i++) {
                String url = String.format(urlBase, i);
                logPrint.println("start process:" + url);
                logPrint.flush();
                Spider.create(new Test()).addUrl(url).thread(5).run();
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (Exception e) {
                    logPrint.println("ERROR");
                }
                Spider.create(new Test()).addUrl("http://www.acfun.tv/v/list110/index_1.htm").thread(5).run();
                TimeUnit.SECONDS.sleep(10);
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
