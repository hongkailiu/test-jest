package tk.hongkailiu.test.jest.module;

import static org.junit.Assert.fail;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import tk.hongkailiu.test.jest.config.Config;
import tk.hongkailiu.test.jest.dao.ArticleDao;

/**
 * Created by hongkailiu on 2017-05-01.
 */
public class TestModule extends AbstractModule {

  private Config config;

  @Override
  protected void configure() {
    bind(ArticleDao.class).in(Singleton.class);
    bind(RestHelper.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  Config provideConfig() {
    try {
      return Config.load(getClass().getClassLoader().getResource("app.json").getFile());
    } catch (FileNotFoundException | MalformedURLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Provides
  @Singleton
  JestClient provideJestClient(Config config) {
    JestClientFactory factory = new JestClientFactory();
    factory.setHttpClientConfig(new HttpClientConfig
        .Builder(config.getElasticUrl())
        .multiThreaded(true)
        //Per default this implementation will create no more than 2 concurrent connections per given route
        .defaultMaxTotalConnectionPerRoute(3)
        // and no more 20 connections in total
        .maxTotalConnection(10)
        .build());
    return factory.getObject();
  }
}
