package tk.hongkailiu.test.jest.module;

import com.google.inject.Inject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import io.restassured.response.ValidatableResponse;
import tk.hongkailiu.test.jest.config.Config;
import tk.hongkailiu.test.jest.entity.BaseEntity;

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
    RestAssured.given().log().uri().baseUri(baseUri).port(config.getPort())
        .queryParam("format", "json").
        get("/_cat/indices").then().log().ifError().assertThat()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .body("index", Matchers.hasItem(index));
  }

  public void deleteIndex(String index) {
    String baseUri = config.getProtocol() + "://" + config.getHost();
    RestAssured.given().log().uri().baseUri(baseUri).port(config.getPort())
        .pathParam("index", index).
        delete("/{index}").then().log().ifError().assertThat()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .body("acknowledged", Matchers.is(true));
  }

  public ValidatableResponse getAllDoc(String index) {
    String baseUri = config.getProtocol() + "://" + config.getHost();
    return RestAssured.given().log().uri().baseUri(baseUri)
        .port(config.getPort())
        .pathParam("index", index).
            post("/{index}/_search?pretty").then().log().ifError().
            assertThat()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON);
  }

  public void indexDoc(String index, String type, BaseEntity entity) {
    String baseUri = config.getProtocol() + "://" + config.getHost();
    RestAssured.given().log().uri().baseUri(baseUri).port(config.getPort())
        .pathParam("index", index)
        .pathParam("type", type)
        .pathParam("id", entity.getDocumentId())
        .body(entity.toString())
        .put("/{index}/{type}/{id}").then().log().ifError().assertThat()
        .statusCode(HttpStatus.SC_CREATED)
        .contentType(ContentType.JSON)
        .body("created", Matchers.is(true));
  }
}
