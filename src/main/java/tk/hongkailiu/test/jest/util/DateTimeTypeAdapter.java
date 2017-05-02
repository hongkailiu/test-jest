package tk.hongkailiu.test.jest.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created by ehongka on 5/2/17.
 * ref
 * https://gist.github.com/hivemind02/7885704
 * https://github.com/gkopff/gson-jodatime-serialisers
 */
public class DateTimeTypeAdapter implements JsonSerializer<DateTime>,
    JsonDeserializer<DateTime> {
  @Override
  public DateTime deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {
    return DateTime.parse(json.getAsString());
  }

  @Override
  public JsonElement serialize(DateTime src, Type typeOfSrc,
      JsonSerializationContext context) {
    return new JsonPrimitive(ISODateTimeFormat
        //.dateTimeNoMillis()
        .dateTime()
        .print(src));
  }
}
