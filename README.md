# PantryPal - Team 34

Welcome to PantryPal, the AI powered solution to efficient meal planning.

- Elevate your cooking experience with a mere utterance as PantryPal transforms 
your spoken ingredients into personalized recipes, complete with vibrant visuals 
that bring your culinary creations to life. 

- Seamlessly share your kitchen triumphs with friends, thanks to easy-to-follow 
directions and captivating images.

Join the PantryPal community, where ingredients become art, and every dish tells 
a delicious story. 

## Github Repository Link
[https://github.com/ucsd-cse110-fa23/cse-110-project-team-34](https://github.com/ucsd-cse110-fa23/cse-110-project-team-34)

## How do I use PantryPal?

### Requirements
- Java JDK version 17
- JavaFX
- Gradle

### Recommendations
Using VSCode is recommended for ease of use and compatibility

### General Running Instructions
1. Copy platform specific JavaFX lib files into the lib folder - The default has Windows x64 lib files
2. Run gradle build and clean workspace
3. Run server instance by using the Server run action
4. Run local program using the Main run action

## FAQs

### Why is it not running?
One of the most common issues arises from the fact that Gradle does not support JRE 21

Try compiling and running using JDK 17

Another common issue is using the wrong JavaFX lib files

Make sure you are using the proper JavaFX lib files for your OS