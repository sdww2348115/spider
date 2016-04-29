package pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import utils.FileUtil;
import utils.Mailutil;
import utils.WechatUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RenshiPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private static HashSet<String> oldTitles = new HashSet<String>();

    @Override
    public void process(Page page) {
        //首页入口
        if(page.getUrl().toString().matches("http://www.cdpta.gov.cn/netpage/index.do")) {
            page.addTargetRequests(page.getHtml().$("table[background=/websiteImages/netimages1/exam_tit_bg5.gif] a", "href").all());
        } else {
            List<String> titles = page.getHtml().$("a", "innerHtml").all();
            List<String> newTitles = new LinkedList<String>();
            for(String title:titles) {
                if(title.contains("教师")) {
                    if(!oldTitles.contains(title)) {
                        oldTitles.add(title);
                        newTitles.add(title);
                    }
                }
            }

            if(newTitles.size() != 0) {
                String text1 = new String("新招聘公告:\n");
                String text2 = new String();
                for(String title:newTitles) {
                    text2 += title + "\n";
                }
                try {
                    new Mailutil().sendTextEmail(text1 + text2);
                    new WechatUtil().send(text1 + text2);
                } catch (Exception e) {
                    System.out.print(e.getStackTrace());
                }

                FileUtil.write(text2);
            }
        }
    }

    public RenshiPageProcessor() {
        oldTitles.addAll(FileUtil.readLines());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new RenshiPageProcessor()).addUrl("http://www.cdpta.gov.cn/netpage/index.do").thread(5).run();
    }
}