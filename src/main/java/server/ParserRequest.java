package server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс обрабатывает запросы от клиентов. Определяет, корректный запрос или нет.
 */
public class ParserRequest {
    private final Pattern postRequest = Pattern.compile("^(POST) /(queue|topic) (.+)");
    private final Pattern getRequest = Pattern.compile("^(GET) /(queue|topic)/(.*)");

    public String[] parsRequest(String request) {
        Matcher postMatcher = postRequest.matcher(request);
        Matcher getMatcher = getRequest.matcher(request);
        if (postMatcher.find()) {
            return getArrayRequest(request, postMatcher);
        } else if (getMatcher.find()) {
            return getArrayRequest(request, getMatcher);
        }
        return new String[0];
    }

    private String[] getArrayRequest(String request, Matcher matcher) {
        String[] getArrayRequest = new String[3];
        getArrayRequest[0] = matcher.group(1).trim();
        getArrayRequest[1] = matcher.group(2).equals("queue") ? "queue" : "topic";
        getArrayRequest[2] = matcher.group(3).trim();
        return getArrayRequest;
    }
}


