package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Класс обрабатывает JSON запросы
 */

public class ObjectJson {
    private final String queue;
    private final String text;

    public ObjectJson(String json) {
        JsonElement jsonParser = JsonParser.parseString(json);
        JsonObject jsonObject = jsonParser.getAsJsonObject();
        queue = jsonObject.get("queue").getAsString();
        text = jsonObject.get("text").getAsString();
    }

    public String convertObjectToJson() {
        Gson gson = new Gson();
        JsonObject rootObject = new JsonObject();
        rootObject.addProperty("queue", this.queue);
        rootObject.addProperty("text", this.text);
        return gson.toJson(rootObject);
    }

    public String getQueue() {
        return queue;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObjectJson that = (ObjectJson) o;
        if (queue != null ? !queue.equals(that.queue) : that.queue != null) {
            return false;
        }
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = queue != null ? queue.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ObjectJson{"
                + "queue='" + queue + '\''
                + ", text='" + text + '\''
                + '}';
    }
}
