import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/** GET and POST to JSONPlaceholder (free public REST API); status codes and bodies go to RestResponse.txt. */
public class Task2 {
    private static final String URL = "https://jsonplaceholder.typicode.com/posts";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest get = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/1"))
                .GET()
                .build();
        HttpResponse<String> getResponse = client.send(get, HttpResponse.BodyHandlers.ofString());

        String json = "{\"title\":\"Assignment 4\",\"body\":\"REST POST test\",\"userId\":1}";
        HttpRequest post = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> postResponse = client.send(post, HttpResponse.BodyHandlers.ofString());

        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream("RestResponse.txt"), "UTF-8"))) {
            out.println("=== GET " + URL + "/1 ===");
            out.println("Status code: " + getResponse.statusCode());
            out.println("Body:");
            out.println(getResponse.body());
            out.println();
            out.println("=== POST " + URL + " ===");
            out.println("Request body: " + json);
            out.println("Status code: " + postResponse.statusCode());
            out.println("Body:");
            out.println(postResponse.body());
        }
        System.out.println("GET status: " + getResponse.statusCode()
                + ", POST status: " + postResponse.statusCode() + " (saved to RestResponse.txt)");
    }
}
