package tk.hongkailiu.test.jest.dao;


import com.google.inject.Inject;
import io.searchbox.client.JestClient;
import tk.hongkailiu.test.jest.entity.Article;

/**
 * Created by hongkailiu on 2017-05-01.
 */
public class ArticleDao extends BaseDao<Article> {

  @Inject
  public ArticleDao(JestClient client) {
    super(client, Article.class);
  }
}
