/**
 * SparkPost a demo project by McGivrer.
 * (c) June 2016 - http://mcgivrer.wordpress.com/
 */
package fr.mcgivrer.apps.sparkpost;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.threadPool;
import static spark.Spark.port;

/**
 * The main class for our demo. This class parse some data and serve those data
 * to JSON client.
 * 
 * @author frederic
 */
public class App {

	private static final String WS_SECRET_KEY = "MySecretkey";

	public static void main(String[] args) {
		int port = 9000;
		int maxThreads = 8;
		int minThreads = 2;
		int timeOutMillis = 30000;
		threadPool(maxThreads, minThreads, timeOutMillis);
		port(port);
		// retrieve some data
		get("/post", (request, response) -> {
			return "post";
		}, new JsonTransformer());

		// retrieve some data with parameters
		get("/post/:id", (request, response) -> {
			if (request.params(":id")!=null) {
				String postId = request.params(":id");
				return "Post id: " + request.params(":id");

			} else {
				halt(404, "post id unknown");
			}
			return null;
		}, new JsonTransformer());

		// Create some data
		post("/post", (request, response) -> {
			return "post created";
		}, new JsonTransformer());

		// Update some data
		put("/post/:id", (request, response) -> {
			if (request.params(":id")!=null) {
				String postId = request.params(":id");
				return "post updated";

			} else {
				halt(404, "post id unknown");
			}
			return null;
		}, new JsonTransformer());

		// delete some data
		delete("/", (request, response) -> {
			return "post deleted";
		}, new JsonTransformer());

		/**
		 * Verify some thing in header on each request (authentication ?!)
		 */
		before((request, response) -> {
			boolean authenticated = ((request.headers("WS-KEY") != null)
					&& request.headers("WS-KEY").equals(WS_SECRET_KEY));
			if (!authenticated) {
				halt(401, "You are not welcome here");
			}
		});

	}

}