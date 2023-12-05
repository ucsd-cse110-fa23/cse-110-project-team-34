package BackEnd.server;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

public class Server {

  // initialize server port and hostname
  private static final int SERVER_PORT = 8100;
  private static final String SERVER_HOSTNAME = "0.0.0.0";

  public static void main(String[] args) throws IOException {
    // create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    server.createContext("/", new RootRequestHandler());
    server.createContext("/recipelist", new RecipeListRequestHandler());
    server.createContext("/login", new LoginRequestHandler());
    server.createContext("/signup", new SignupRequestHandler());
    server.createContext("/createRecipe", new CreateRecipeHandler());


    /*
     * A share URL should have the format http://localhost:8100/share?userID=<User ID Goes Here>&recipeName=<RecipeNameGoesHere> 
     */
    server.createContext("/share", new ShareRequestHandler()); 
    server.setExecutor(threadPoolExecutor);
    server.start();

    System.out.println("Server started on port " + SERVER_PORT);

  }
}
