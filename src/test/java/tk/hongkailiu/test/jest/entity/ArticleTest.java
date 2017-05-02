package tk.hongkailiu.test.jest.entity;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ehongka on 5/2/17.
 */
public class ArticleTest {
  private Article unitUnderTest;
  @Before
  public void setUp() throws Exception {
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setUserName("john");
    unitUnderTest = new Article("ttt", "ccc", user, new DateTime());
    unitUnderTest.setDocumentId("3");
  }

  @Test
  public void testToString() {
    System.out.println(unitUnderTest);
  }
}