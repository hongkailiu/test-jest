package tk.hongkailiu.test.jest.entity;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * Created by hongkailiu on 2017-05-01.
 */
@Data
public class User extends BaseEntity {
  private String firstName;
  private String lastName;
  private String userName;
}

