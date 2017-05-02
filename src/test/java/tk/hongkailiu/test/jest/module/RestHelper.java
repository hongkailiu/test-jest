package tk.hongkailiu.test.jest.module;

import com.google.inject.Inject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import tk.hongkailiu.test.jest.config.Config;

/**
 * Created by hongkailiu on 2017-05-01.
 */
public class RestHelper {

  private final Config config;

  @Inject
  public RestHelper(Config config) {
    this.config = config;
  }

  public void indexExists(String index) {
    String baseUri = config.getProtocol() + "://" + config.getHost();
    RestAssured.given().baseUri(baseUri).port(config.getPort())
        .queryParam("format", "json").
        get("/_cat/indices").then().log().ifError().assertThat()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .body("index", Matchers.hasItem(index));
  }

  public void deleteIndex(String index) {
    String baseUri = config.getProtocol() + "://" + config.getHost();
    RestAssured.given().baseUri(baseUri).port(config.getPort()).
        delete("/article").then().log().ifError().assertThat()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .body("acknowledged", Matchers.is(true));
  }
}
