package tk.hongkailiu.test.jest.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import tk.hongkailiu.test.jest.util.Helper;

/**
 * Created by hongkailiu on 2017-05-01.
 */
@Slf4j
public class Config {

  @Getter
  private String name;
  @Getter
  private String elasticUrl;
  @Getter
  private String index;

  private URL url;


  public static Config load(String fileName, Helper helper)
      throws FileNotFoundException, MalformedURLException {
    Config config = new Gson()
        .fromJson(new JsonReader(new FileReader(fileName)), Config.class);
    String elasticUrlFromHelper = helper.getElasticUrl();
    if (elasticUrlFromHelper!=null) {
      config.elasticUrl = elasticUrlFromHelper;
    }
    log.info("config.elasticUrl: " + config.elasticUrl);
    config.url = new URL(config.elasticUrl);
    return config;
  }

  public String getProtocol() {
    return url.getProtocol();
  }

  public String getHost() {
    return url.getHost();
  }

  public int getPort() {
    return url.getPort();
  }
}
