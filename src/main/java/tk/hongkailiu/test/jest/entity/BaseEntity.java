package tk.hongkailiu.test.jest.entity;

import lombok.Data;

/**
 * Created by hongkailiu on 2017-05-01.
 */
@Data
public class BaseEntity {

  // https://github.com/searchbox-io/Jest/issues/181#issuecomment-155449951
  //@JestId
  // transient needs to be removed if the above issue is fixed
  private transient String documentId;

  public String getType() {
    return getClass().getSimpleName().toLowerCase();
  }

}
