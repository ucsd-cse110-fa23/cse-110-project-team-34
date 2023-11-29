package BackEnd;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CountOptions;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.function.Consumer;

import java.util.Random;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import static java.util.Arrays.asList;

public class RecipeListDatabase {
    
    private static final String uri = "mongodb+srv://admin:admin123@pantypal-team34.8qzrbi4.mongodb.net/?retryWrites=true&w=majority";

    
}
