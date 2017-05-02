package tk.hongkailiu.test.jest.util;

import com.google.common.net.InetAddresses;

/**
 * Created by ehongka on 5/2/17.
 */
public class Helper {
  public String getElasticUrl() {
    String result = System.getenv("ELASTIC_URL");
    if (result != null && InetAddresses.isInetAddress(result)) {
      return result;
    }
    return null;
  }
}
