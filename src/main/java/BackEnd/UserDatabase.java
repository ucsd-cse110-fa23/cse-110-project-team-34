package BackEnd;

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

import java.util.Random;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import static java.util.Arrays.asList;

public class UserDatabase {
    
    private static final String uri = "mongodb+srv://admin:admin123@pantypal-team34.8qzrbi4.mongodb.net/?retryWrites=true&w=majority";

    /**
     * Function that determines if a username already exists
     * @param username username to check
     * @return true if the username already exists
     */
    public static boolean usernameExists(String username){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> usersCollection = pantryPalDatabse.getCollection("Users");

            long count = usersCollection.countDocuments(eq("username", username), new CountOptions().limit(1));

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
     * Creates a new user (IMPORTANT: Does not check for duplicate, please use UserDatabase.usernameExists(String username))
     * @param username username for new user account
     * @param password password for new user account
     * @return The unique user ID as a hex string as given by the _id in MongoDB. Meant to be stored locally for ease of access. Returns null if failed.
     */
    public static String createUser(String username, String password){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> usersCollection = pantryPalDatabse.getCollection("Users");

            ObjectId newUserId = new ObjectId();
            Document newUser = new Document("_id", newUserId);
            newUser.append("username", username);
            newUser.append("password", password);

            usersCollection.insertOne(newUser);
            return newUserId.toString();

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks if username and password are valid then deletes the user.
     * @param username username for user account
     * @param password password for user account
     * @return True if success, false if failure
     */
    public static boolean deleteUserWithUsernamePassword(String username, String password){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> usersCollection = pantryPalDatabse.getCollection("Users");

            Bson userNameFilter = eq("username", username);
            Document user = usersCollection.find(userNameFilter).first();
            if(user.get("password").equals(password)){
                DeleteResult deleteUserResult = usersCollection.deleteOne(userNameFilter);
                if(deleteUserResult.wasAcknowledged()){
                    return true;
                }
            }else{
                return false;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * DANGER: This will delete the first user with a given username without checking for
     * password equivalency
     * 
     * Deletes user by username no password check
     * @param username username for user account
     * @return True if success, false if failure
     */
    public static boolean deleteUserForce(String username){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> usersCollection = pantryPalDatabse.getCollection("Users");

            Bson userNameFilter = eq("username", username);
            DeleteResult deleteUserResult = usersCollection.deleteOne(userNameFilter);
            if(deleteUserResult.wasAcknowledged()){
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets the username associated with a given user id
     * @param id the user id
     * @return the username associated with the given user id
     */
    public static String getUsernameById(String id){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> usersCollection = pantryPalDatabse.getCollection("Users");

            Bson idFilter = eq("_id", id);
            Document user = usersCollection.find(idFilter).first();
            if(user != null){
                return user.get("username").toString();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DANGER: This isn't the best as far as cybersecurity goes, but it is useful for testing
     * NOTE: You probably shouldn't be using this in the actual code.
     * Gets the password associated with a given user id
     * @param id the user id
     * @return the password associated with the given user id
     */
    public static String getPasswordById(String id){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> usersCollection = pantryPalDatabse.getCollection("Users");

            Bson idFilter = eq("_id", id);
            Document user = usersCollection.find(idFilter).first();
            if(user != null){
                return user.get("password").toString();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Gets the username associated with a given user id
     * @param id the user id
     * @return the username associated with the given user id
     */
    public static String getIdByUsernamePassword(String username, String password){

        try(MongoClient mongoClient = MongoClients.create(uri)){

            MongoDatabase pantryPalDatabse = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> usersCollection = pantryPalDatabse.getCollection("Users");

            Bson userNameFilter = eq("username", username);
            Document user = usersCollection.find(userNameFilter).first();
            if(user.get("password").equals(password)){
                return user.get("_id").toString();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }



}
