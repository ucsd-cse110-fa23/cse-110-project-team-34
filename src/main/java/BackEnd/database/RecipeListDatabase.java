package BackEnd.database;

import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.result.DeleteResult;

import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.function.Consumer;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Sorts.descending;
import static java.util.Arrays.asList;

public class RecipeListDatabase {
    
    private static final String uri = "mongodb+srv://admin:admin123@pantypal-team34.8qzrbi4.mongodb.net/?retryWrites=true&w=majority";

    /**
     * Creates an empty recipe list given a user id
     * @param userId user id of the owner of the list
     * @return True if success, false if failure
     */
    public static boolean createEmptyRecipeList(String userId){
        
        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> recipeListsCollection = pantryPalDatabse.getCollection("RecipeLists");

            Document newRecipeList = new Document("_id", UserDatabase.getObjectIdObjectFromString(userId));
            newRecipeList.append("recipeList", asList());

            recipeListsCollection.insertOne(newRecipeList);

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Function that determines if a recipelist exists for the given userId
     * @param userId userId for search
     * @return Whether the recipelist exists
     */
    public static boolean recipeListExists(String userId){
        
        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> recipeListsCollection = pantryPalDatabse.getCollection("RecipeLists");

            Bson idFilter = eq("_id", UserDatabase.getObjectIdObjectFromString(userId));
            long count = recipeListsCollection.countDocuments(idFilter, new CountOptions().limit(1));

            if(count >= 1){
                return true;
            }else{
                return false;
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes recipelist of a given user 
     * @param id id for user account
     * @return True if success, false if failure
     */
    public static boolean deleteRecipelistById(String id){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> recipeListsCollection = pantryPalDatabse.getCollection("RecipeLists");

            Bson idFilter = eq("_id", UserDatabase.getObjectIdObjectFromString(id));
            DeleteResult deleteListResult = recipeListsCollection.deleteOne(idFilter);
            if(deleteListResult.wasAcknowledged()){
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets recipe list of a given user as a JSON simple object
     * @param id id for user account
     * @return Recipe list of given user as a JSONObject jsonsimple
     */
    public static JSONObject getRecipelistByIdAsJSON(String id){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> recipeListsCollection = pantryPalDatabse.getCollection("RecipeLists");

            Bson idFilter = eq("_id", UserDatabase.getObjectIdObjectFromString(id));
            Document recipeListObj = recipeListsCollection.find(idFilter).first();
            List<Document> recipeArray = recipeListObj.getList("recipeList", Document.class);

            JSONArray recipeJSONArray = new JSONArray();
            recipeJSONArray.addAll(recipeArray);

            JSONObject returnJSON = new JSONObject();
            returnJSON.put("recipeList", recipeJSONArray);

            return returnJSON;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 
     * @param id
     * @param recipeListJson
     * @return
     */
    public static boolean setRecipeListByIdGivenJSON(String id, JSONObject recipeListJson){
        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> recipeListsCollection = pantryPalDatabse.getCollection("RecipeLists");

            Bson idFilter = eq("_id", UserDatabase.getObjectIdObjectFromString(id));

            JSONArray recipeListJSONArray = (JSONArray)recipeListJson.get("recipeList");
            ArrayList<DBObject> recipeListArrayList = new ArrayList<DBObject>(recipeListJSONArray);

            Bson recipeListUpdate = set("recipeList", recipeListArrayList);

            recipeListsCollection.updateOne(idFilter, recipeListUpdate);

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

}
