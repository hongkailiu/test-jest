package tk.hongkailiu.test.jest.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import org.joda.time.DateTime;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import tk.hongkailiu.test.jest.config.Config;
import tk.hongkailiu.test.jest.dao.ArticleDao;
import tk.hongkailiu.test.jest.util.DateTimeTypeAdapter;
import tk.hongkailiu.test.jest.util.Helper;

/**
 * Created by hongkailiu on 2017-05-01.
 */
public class TestModule extends AbstractModule {

  private static final Gson
      GSON = new GsonBuilder()
      .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter()).create();

  @Override
  protected void configure() {
    bind(ArticleDao.class).in(Singleton.class);
    bind(RestHelper.class).in(Singleton.class);
    bind(Helper.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  Config provideConfig(Helper helper) {
    try {
      return Config.load(getClass().getClassLoader().getResource("app.json").getFile(), helper);
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
        .gson(GSON)
        .multiThreaded(true)
        //Per default this implementation will create no more than 2 concurrent connections per given route
        .defaultMaxTotalConnectionPerRoute(3)
        // and no more 20 connections in total
        .maxTotalConnection(10)
        .build());
    return factory.getObject();
  }
}
