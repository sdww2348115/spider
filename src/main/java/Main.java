import pageprocessor.RenshiPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by sdww on 16-4-28.
 */
public class Main {
  public static void main(String[] args) {
    Spider.create(new RenshiPageProcessor()).addUrl("http://www.cdpta.gov.cn/netpage/index.do").thread(5).run();
  }
}
