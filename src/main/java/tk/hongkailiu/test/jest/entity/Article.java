package tk.hongkailiu.test.jest.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.searchbox.annotations.JestId;
import lombok.Data;
import tk.hongkailiu.test.jest.util.DateTimeTypeAdapter;

import org.joda.time.DateTime;

/**
 * Created by hongkailiu on 2017-05-01.
 */
@Data
public class Article extends BaseEntity {

  private static final Gson
      GSON = new GsonBuilder()
      .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter()).create();

  @JestId
  private String documentId;

  private String title;
  private String content;
  private User user;
  private DateTime dateTime;

  public Article(String title,
      String content,
      User user,
      DateTime dateTime) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return GSON.toJson(this);
  }
}

