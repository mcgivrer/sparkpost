/**
 * SparkPost a demo project by McGivrer.
 * (c) June 2016 - http://mcgivrer.wordpress.com/
 */
package fr.mcgivrer.apps.sparkpost;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.before;
import static spark.Spark.threadPool;
import static spark.Spark.halt;

/**
 * The main class for our demo. This class parse some data and serve those data
 * to JSON client.
 * 
 * @author frederic
 */
public class App {

	private static final String WS_SECRET_KEY = "MySecretkey";

	public static void main(String[] args) {
		int maxThreads = 8;
		int minThreads = 2;
		int timeOutMillis = 30000;
		threadPool(maxThreads, minThreads, timeOutMillis);

		// retrieve some data
		get("/post", (request, response) -> {
			return "post";
		});

		// retrieve some data with parameters
		get("/post/:id", (request, response) -> {
			return "Post id: " + request.params(":id");
		});

		// Create some data
		post("/post", (request, response) -> {
			return "post created";
		});

		// Update some data
		put("/post/:id", (request, response) -> {
			String postId = request.params(":id");
			return "post updated";
		});

		// delete some data
		delete("/", (request, response) -> {
			return "post deleted";
		});

	/**
	  * Verify some thing in header on each request (authentication ?!)
	  */
	  before((request, response) -> {
	   boolean authenticated = ( ( request.headers("WS-KEY")!=null) 
	        && request.headers("WS-KEY").equals(WS_SECRET_KEY));
	      if (!authenticated) {
	        halt(401, "You are not welcome here");
	      }
	    }); 

	}

}