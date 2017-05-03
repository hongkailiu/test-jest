package tk.hongkailiu.test.jest.dao;


import static com.google.common.truth.Truth.assertThat;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import io.restassured.response.ValidatableResponse;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import tk.hongkailiu.test.jest.config.Config;
import tk.hongkailiu.test.jest.entity.Article;
import tk.hongkailiu.test.jest.entity.User;
import tk.hongkailiu.test.jest.module.RestHelper;
import tk.hongkailiu.test.jest.module.TestModule;

/**
 * Created by hongkailiu on 2017-05-01.
 */
public class ArticleDaoTest {

  private ArticleDao unitUnderTest;
  private RestHelper restHelper;
  private Config config;

  private Article article;

  @Before
  public void setUp() throws Exception {
    Injector injector = Guice.createInjector(new TestModule());
    unitUnderTest = injector.getInstance(ArticleDao.class);
    assertThat(unitUnderTest).isNotNull();
    restHelper = injector.getInstance(RestHelper.class);
    assertThat(restHelper).isNotNull();
    config = injector.getInstance(Config.class);
    assertThat(config).isNotNull();

    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setUserName("john");
    article = new Article("ttt", "ccc", user, new DateTime());
    article.setDocumentId("3");
  }

  @After
  public void tearDown() throws Exception {
    restHelper.deleteIndex(config.getIndex());
  }

  @Test
  public void testConstructor() {
    restHelper.indexExists(config.getIndex());
  }

  @Test
  public void testIndex() throws IOException, InterruptedException {

    unitUnderTest.index(article);
    Thread.sleep(1 * 1000);
    ValidatableResponse response = restHelper.getAllDoc(config.getIndex());
    response.assertThat().body("hits.total", Matchers.is(1))
        .body("hits.hits._index", Matchers.hasItem(config.getIndex()))
        .body("hits.hits._type", Matchers.hasItem(article.getType()))
        .body("hits.hits._id", Matchers.hasItem(article.getDocumentId()));
  }

  @Test
  public void testSearch() throws IOException, InterruptedException {
    restHelper.indexDoc(config.getIndex(), article.getType(), article);
    // https://www.elastic.co/guide/en/elasticsearch/guide/current/near-real-time.html
    Thread.sleep(1 * 1000);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());

    Search search = new Search.Builder(searchSourceBuilder.toString())
        // multiple index or types can be added.
        .addIndex(config.getIndex())
        .addType(article.getType())
        .build();
    SearchResult result = unitUnderTest.search(search);
    List<SearchResult.Hit<Article, Void>> hits = result.getHits(Article.class);
    assertThat(hits).hasSize(1);
    Article hitArticle = hits.get(0).source;
    assertThat(hitArticle.getTitle()).isEqualTo(article.getTitle());
  }

  @Test
  public void testGet() throws IOException, InterruptedException {
    restHelper.indexDoc(config.getIndex(), article.getType(), article);
    // https://www.elastic.co/guide/en/elasticsearch/guide/current/near-real-time.html
    Thread.sleep(1 * 1000);
    Get get = new Get.Builder(config.getIndex(), article.getDocumentId()).type(article.getType()).build();

    JestResult result = unitUnderTest.get(get);
    Article getArticle = result.getSourceAsObject(Article.class);
    assertThat(getArticle.getTitle()).isEqualTo(article.getTitle());
  }
}