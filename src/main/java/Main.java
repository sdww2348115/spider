import pageprocessor.GithubRepoPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by sdww on 16-4-28.
 */
public class Main {
  public static void main(String[] args) {
    Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
  }
}
