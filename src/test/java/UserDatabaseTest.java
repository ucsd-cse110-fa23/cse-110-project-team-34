import org.junit.jupiter.api.Test;

import BackEnd.database.UserDatabase;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserDatabaseTest {
    
    public static final String testUsername = "testUserNameForTesting";
    public static final String testPassword = "testPasswordForTestUsername";

    public static final String temporaryUsername = "temporaryUserNameForTesting";
    public static final String temporaryPassword = "temporaryPasswordForTestUsername";

    public static final String notRealUsername = "blahblahblahthisisnotarealusername";
    public static final String notRealPassword = "thisisthewrongpassworddonotusethispassword";

    public static String userId = "";

    @BeforeEach
    void setUp() {
        if(UserDatabase.usernameExists(testUsername)){
            UserDatabase.deleteUserForce(testUsername);
        }

        userId = UserDatabase.createUser(testUsername, testPassword);
    }
    
    @Test
    void testUserNameExistsTrue() {
        assertTrue(UserDatabase.usernameExists(testUsername));
    }

    @Test
    void testUserNameExistsFalse() {
        assertFalse(UserDatabase.usernameExists(notRealUsername));
    }

    @Test
    void testGetIdByUsernamePassword(){
        assertEquals(userId, UserDatabase.getIdByUsernamePassword(testUsername, testPassword));
    }

    @Test
    void testGetUsernameById(){
        assertEquals(testUsername, UserDatabase.getUsernameById(userId));
    }

    @Test
    void testGetPasswordById(){
        assertEquals(testPassword, UserDatabase.getPasswordById(userId));
    }

    @Test
    void testCreateUserNoUser() {
        boolean success = true;
        String tempId = UserDatabase.createUser(temporaryUsername, temporaryPassword);
        success = success && (tempId.equals(UserDatabase.getIdByUsernamePassword(temporaryUsername, temporaryPassword)));
        success = success && (temporaryUsername.equals(UserDatabase.getUsernameById(tempId)));
        success = success && (temporaryPassword.equals(UserDatabase.getPasswordById(tempId)));
        assertTrue(success);
    }

    @Test
    void testDeleteuserWithUsernamePasswordCorrect() {
        assertTrue(UserDatabase.deleteUserWithUsernamePassword(testUsername,testPassword));
    }

    @Test
    void testDeleteuserWithUsernamePasswordIncorrectPassword() {
        assertFalse(UserDatabase.deleteUserWithUsernamePassword(testUsername,notRealPassword));
    }

    @Test
    void testDeleteuserWithUsernamePasswordIncorrectUsername() {
        assertFalse(UserDatabase.deleteUserWithUsernamePassword(notRealUsername,notRealPassword));
    }

    @Test
    void testDeleteuserForce() {
        UserDatabase.deleteUserForce(testUsername);
        assertFalse(UserDatabase.usernameExists(testUsername));
    }

    @AfterAll
    static void cleanup(){
        if(UserDatabase.usernameExists(testUsername)){
            UserDatabase.deleteUserForce(testUsername);
        }

        if(UserDatabase.usernameExists(temporaryUsername)){
            UserDatabase.deleteUserForce(temporaryUsername);
        }
    }

}