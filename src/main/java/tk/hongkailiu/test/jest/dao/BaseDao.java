package tk.hongkailiu.test.jest.dao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.aliases.GetAliases;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import tk.hongkailiu.test.jest.entity.BaseEntity;

/**
 * Created by hongkailiu on 2017-05-01.
 */
@Slf4j
public class BaseDao<T extends BaseEntity> {

  protected final JestClient client;

  private Class<T> typeClass;

  public BaseDao(JestClient client, Class<T> typeClass) {
    this.client = client;
    this.typeClass = typeClass;
    try {
      createIndexIfNeeded();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createIndexIfNeeded() throws IOException {
    GetAliases aliases = new GetAliases.Builder().build();
    JsonObject jsonObject = client.execute(aliases).getJsonObject();
    String index = typeClass.getSimpleName().toLowerCase();
    log.debug("index: " + index);

    JsonElement indexJsonElement = jsonObject.get(index);
    if (indexJsonElement == null) {
      log.debug("creating index: " + index);
      client.execute(new CreateIndex.Builder(index).build());
    } else {
      log.debug("skipped creating index (already existed): " + index);
    }

  }

  public void index() {

  }
}
