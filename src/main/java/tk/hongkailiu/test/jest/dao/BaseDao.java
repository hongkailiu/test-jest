package tk.hongkailiu.test.jest.dao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.aliases.GetAliases;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import tk.hongkailiu.test.jest.config.Config;
import tk.hongkailiu.test.jest.entity.BaseEntity;

/**
 * Created by hongkailiu on 2017-05-01.
 */
@Slf4j
public class BaseDao<T extends BaseEntity> {

  protected final JestClient client;
  protected final Config config;

  public BaseDao(JestClient client, Config config) {
    this.client = client;
    this.config = config;
    try {
      createIndexIfNeeded();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createIndexIfNeeded() throws IOException {
    GetAliases aliases = new GetAliases.Builder().build();
    JsonObject jsonObject = client.execute(aliases).getJsonObject();
    String index = config.getIndex();
    log.debug("index: " + index);

    JsonElement indexJsonElement = jsonObject.get(index);
    if (indexJsonElement == null) {
      log.debug("creating index: " + index);
      client.execute(new CreateIndex.Builder(index).build());
    } else {
      log.debug("skipped creating index (already existed): " + index);
    }

  }

  public void index(T t) throws IOException {
    Index index =
        new Index.Builder(t).index(config.getIndex()).type(t.getType())
            .build();
    client.execute(index);
  }

  public SearchResult search(Search search) throws IOException {
    return client.execute(search);
  }

  public JestResult get(Get get) throws IOException {
    return client.execute(get);
  }

  public void update(Update update) throws IOException {
    client.execute(update);
  }
}
