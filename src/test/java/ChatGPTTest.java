import BackEnd.api.ChatGPT;
import BackEnd.api.Whisper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatGPTTest {
    private ChatGPT chatGPT;
    private Whisper whisper;
    
    @BeforeEach
    void setUp() {
        
    }
    
    //@Test
    public static void main(String[] args) {
        Scanner input;
        try {
            input = new Scanner(new File("storage.json"), StandardCharsets.UTF_8);
            while (input.hasNextLine())
            {
                System.out.println(input.nextLine());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

            
        
    }

}