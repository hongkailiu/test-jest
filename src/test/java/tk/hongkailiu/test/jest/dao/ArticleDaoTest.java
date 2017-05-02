package tk.hongkailiu.test.jest.dao;


import static com.google.common.truth.Truth.assertThat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tk.hongkailiu.test.jest.module.RestHelper;
import tk.hongkailiu.test.jest.module.TestModule;

/**
 * Created by hongkailiu on 2017-05-01.
 */
public class ArticleDaoTest {

  private ArticleDao unitUnderTest;
  private RestHelper restHelper;

  @Before
  public void setUp() throws Exception {
    Injector injector = Guice.createInjector(new TestModule());
    unitUnderTest = injector.getInstance(ArticleDao.class);
    assertThat(unitUnderTest).isNotNull();
    restHelper = injector.getInstance(RestHelper.class);
    assertThat(restHelper).isNotNull();
  }

  @After
  public void tearDown() throws Exception {
    restHelper.deleteIndex("article");
  }

  @Test
  public void testConstructor() {
    restHelper.indexExists("article");
  }

}