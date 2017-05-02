package tk.hongkailiu.test.jest.entity;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * Created by hongkailiu on 2017-05-01.
 */
@Data
public class Article extends BaseEntity {
  private String title;
  private String content;
  private User user;
  private DateTime dateTime;
}

