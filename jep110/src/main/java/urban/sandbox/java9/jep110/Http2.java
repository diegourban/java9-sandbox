package urban.sandbox.java9.jep110;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Http2 {

    private static final Logger LOGGER = Logger.getLogger(Http2.class.getSimpleName());

    private static final String TEST_URI = "http://localhost:8888";

    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO: https://dzone.com/articles/an-introduction-to-http2-support-in-java-9
        final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        final HttpRequest request = HttpRequest.newBuilder(URI.create(TEST_URI)).POST(HttpRequest.BodyProcessor.fromString("Hello World")).build();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        LOGGER.info("Synchronous Response:" + response.body());

        final List<CompletableFuture<String>> responseFutures = new Random()
                .ints(10)
                .mapToObj(String::valueOf)
                .map(message -> client
                        .sendAsync(
                                HttpRequest.newBuilder(URI.create(TEST_URI))
                                        .POST(HttpRequest.BodyProcessor.fromString(message))
                                        .build(), HttpResponse.BodyHandler.asString()
                        ).thenApply(r -> r.body())
                ).collect(Collectors.toList());

        CompletableFuture.allOf(responseFutures.toArray(new CompletableFuture[0])).join();
        responseFutures.stream().forEach(future -> {
            LOGGER.info("Async response: " + future.getNow(null));
        });
    }

}