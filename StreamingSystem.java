import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamingSystem {

    // Static arraylist to hold all user objects
    private static ArrayList<User> userDatabase = new ArrayList<User>();

    // Shows menu in which users can choose what methods to call
    public final void showMenu() {
        System.out.println("Please choose one of the following options!\n\t1. Add user\n\t2. Remove user\n\t3. List all users\n\t4. User sub-menu\n\t5. Exit program");
    }

    // Method that calls other methods and that uses loops to make sure
    // user input values are valid for all of main menu options
    public void runMenu(byte menuOption) throws IOException {
        switch (menuOption) {
            // Case 1 allows users to add themselves into the system
            case 1:
                System.out.println("\nAwesome, let's add you in! What is your name? (Note: your name can only contain letters and you can not change it after creating it!)");

                System.out.println("\nAlso feel free to enter BACK if you want to go back to the main menu here! (You will not have created a user if you go back)");

                //Calls loop that will make sure that the name entered is only made of letters
                String nameOfUser = inputCanOnlyBeLetters();

                //Checks to see if user wants to exit this step of the program
                if(nameOfUser.equals("BACK")) {
                    System.out.println();
                    break;
                }
                
                //Creates a unique id for the new user
                String uniqueId = createUniqueId();

                System.out.println("\nOne more thing! What do you want to call your playlist? (It can be anything but blank!)");

                // loop to make sure user makes a playlist name
                String userPlaylistName = inputCannotBeBlank();

                // create object of user class and add it to the userDatabase arraylist
                userDatabase.add(new User(nameOfUser, uniqueId, userPlaylistName));
                
                break;

            // Case 2 allows users to remove themselves from the system
            case 2:

                // Checks if no users in list
                if (userDatabase.isEmpty()) {
                    System.out.println("\nSorry, there is no one in our database currently. Please go ahead and add yourself to our database!\n");
                    break;
                }

                System.out.println("\nPlease enter your unique id!");

                System.out.println("\nAlso feel free to enter 0 if you want to go back to the main menu here! (This will keep you in the system)");

                // Makes sure that the user input is an integer
                String uniqueIdOfUser = inputPositiveIntChecker();

                if(uniqueIdOfUser.equals("0")) {
                    System.out.println("\n");
                    break;
                } else {
                    // Checks to see if the user exists via the int they put in
                    removeUser (uniqueIdOfUser);
                }
                
                break;

            // Case three is for users to see list of created users
            case 3:

                // Checks to see if there is a unique id in the database arraylist
                if (userDatabase.isEmpty()) {
                    System.out.println("\nNo users currently in our database, but you should totally add yourself to it!\n");
                    break;
                }

                System.out.println("\nAll users by name: \n");
                int i = 0;

                // lists all users in database
                for (User user : userDatabase) {
                    i++;
                    System.out.println("[" + i + "] " + user.getName());
                }
                System.out.println();
                break;

            // Case 4 makes user go to submenu to add, remove, play, etc,
            // recordings in their playlist
            case 4:
                System.out.println("\nPlease choose one of the following options!\n\tA. Add a recording to your playlist\n\tB. Add recordings from a file\n\tC. Add recordings from another playlist\n\tD. Remove recording from your playlist\n\tE. Play recording from your playlist\n\tF. Play entire playlist in order\n\tG. Shuffle entire playlist and play it\n\tH. Save entire playlist externally\n\tI. Display playlist statistics\n\tZ. Go back\n");

                //char c = '\0';
                char c=inputCanOnlyBeChar();
                 
                // if else if to get user to the right method
                if (c == 'a' || c == 'A') {
                    runSubMenuA();
                } else if (c == 'b' || c == 'B') {
                    runSubMenuB();
                } else if (c == 'c' || c == 'C') {
                    runSubMenuC();
                } else if (c == 'd' || c == 'D') {
                    runSubMenuD();
                } else if(c == 'e' || c == 'E') {
                    runSubMenuE();
                } else if(c == 'f' || c == 'F') {
                    runSubMenuF();
                } else if(c == 'g' || c == 'G') {
                    runSubMenuG();
                } else if(c == 'h' || c == 'H') {
                    runSubMenuH();
                } else if(c == 'i' || c == 'I') {
                    runSubMenuI();
                } else if (c == 'z' || c == 'Z') {
                    break;
                }

                break;

            // Case 5 termintes program
            case 5:
                System.out.println("Exiting program!");
                System.exit(0);
        }
    }

    // Method that creates a String of nums that result in a unique string
    // that an individual can use to access or modify their playlist
    private final String createUniqueId() {
        boolean exists = false;
        String formatted;

        // Creates a new id until it does not exist in database
        do {
            SecureRandom random = new SecureRandom();
            int num = random.nextInt(100000);
            formatted = String.format("%05d", num);

            // Checks every spot in the database to make sure created id if unique or not
            for (int i = 0; i < userDatabase.size(); i++) {
                if (formatted.equals(userDatabase.get(i).getId())) {
                    exists = true;
                }
            }
        } while (exists);
        return formatted;
    }

    // handles non double inputs
    private double inputMustBeDouble() {
        double b;

        // Loops until input is a valid double
        do {
            System.out.print("Your choice: ");
            Scanner someDouble = new Scanner(System.in);

            try {
                b = someDouble.nextDouble();

                if (b > 0) {
                    break;
                } else {
                    System.out.println("Please enter a valid decimal number option that is greater than zero!\n");
                    continue;
                }
            } catch (final InputMismatchException e) {
                System.out.println("Your guess must be a decimal number!\n");
                someDouble.next(); // discard non-double input
                continue; // restart loop, didn't get an integer input
            }
        } while (true);
        return b;
    }

    // Method that makes sure the user's input is a String
    private String inputCanOnlyBeLetters(){
         // Loop to make sure they put valid input
         do {
            Scanner someGroupOfletters = new Scanner(System.in);
            System.out.print("Your input: ");
            String letters = someGroupOfletters.nextLine(); // user input

            if (letters.isEmpty()) {
                System.out.println("Sorry!!! Your input cannot be blank, please enter a valid name!!!\n");
                continue; // restart
            }

            Pattern digit = Pattern.compile("[0-9]");
            Matcher hasDigit = digit.matcher(letters);

            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(letters);
            boolean b = m.find();

            if (hasDigit.find()) {
                System.out.println("Sorry! Your input cannot contain any numbers, please try again!\n");
                continue; // restart loop
            } else if (b) {
                System.out.println(
                        "Sorry!! Your input cannot contain any special characters, please try again!!\n");
            } else if (letters != null) {
                return letters;
            }
        } while (true);
    }

    // Method that makes sure that the user's input is not empty
    private String inputCannotBeBlank() {
        // Loop to make sure they put valid input
        do {      
            Scanner notToBeBlank = new Scanner(System.in);
            System.out.print("Your input: ");
            String notBlankInput = notToBeBlank.nextLine(); // user input

            if (notBlankInput.isEmpty()) {
                System.out.println("Sorry! Your input can not be blank, please try again!\n");
                continue;
            } else if (notBlankInput != null) {
                return notBlankInput;
            }
        } while (true);
    }

    // Method that makes sure the user's input is a positive integer
    private String inputPositiveIntChecker() {
        // Loops until valid input is typed in
        do{
            Scanner anIntInput = new Scanner(System.in);
            System.out.print("Your input: ");
            String anInt = anIntInput.nextLine();

            if(anInt.isEmpty()){
                System.out.println("Your input cannot be blank!\n");
                continue;
            }

            Pattern letter = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
            Matcher hasLetter = letter.matcher(anInt);

            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(anInt);
            boolean b = m.find();

            if (hasLetter.find()) {
                System.out.println("Sorry! Your input cannot contain any letters, please try again!\n");
                continue; // restart loop, didn't get an integer input
            } else if (b) {
                System.out.println("Sorry!! Your input cannot contain any special characters, please try again!! (Note: the negative sign will call this error, please enter a positive number!)\n");
                continue; // restart loop, didn't get an integer input
            }

            try {
                Integer.parseInt(anInt);
                return anInt;
            } catch (final NumberFormatException nfe) {
                System.out.println("Error. NumberFormatException!");
            }
        } while (true);
    }

    // Removes user from the arraylist userDatabase
    private void removeUser (String userId) {
        // loops through arraylist to see if it finds the String inside of it and then to removes it if it exists
        for (int i = 0; i < userDatabase.size(); i++) {
            if (userId.equals(userDatabase.get(i).getId())) {
                userDatabase.remove(i);
                System.out.println("\nSorry to see you go! Please try us again some other time!\n");
                return;
            }
        }
        System.out.println("\nSorry! Could not find any user in our system with the unique id: " + userId + "\nPlease try again!\n");
    }

    // Method that makes sure that the input by user is a single character
    private char inputCanOnlyBeChar() {
        // Loop to make sure they input a valid character
        do {
            Scanner someChar = new Scanner(System.in);
            System.out.print("Your choice: ");
            String unverifiedChar = someChar.next(); // Blocks for user input

            Pattern digit = Pattern.compile("[0-9]");
            Matcher hasDigit = digit.matcher(unverifiedChar);

            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(unverifiedChar);
            boolean b = m.find();

            boolean notParsable = notParsable(unverifiedChar);

            if (b) {
                System.out.println("Sorry!!! There is no special character choice, please try again!!!\n");
            } else if ((unverifiedChar.length() > 1) && notParsable) {
                System.out.println("Sorry! There is no option that is more than one letter, please try again!\n");
            } else if (hasDigit.find()) {
                System.out.println("Sorry!! There is no number choice, please try again!!\n");
                continue; // restart loop
            } else if (unverifiedChar.isEmpty()) {
                System.out.println("Please enter a character of your choice that corresponds to the options displayed!!!!\n");
                continue;
            } else if (unverifiedChar != null) {
                return unverifiedChar.charAt(0);
            }
        } while (true);
    }

    // returns true if input is a single char
    private boolean notParsable(String s) {
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i);
            if (temp > '0' && temp < '9') {
                return false;
            }
        }
        return true;
    }

    // Method that makes sure input is an int
    private int inputCanOnlyBeInt() {   
        // Loops until input is an integer
         do {
            Scanner someIntInput = new Scanner(System.in);
            System.out.print("Your input: ");
            String someInt = someIntInput.nextLine();

            if(someInt.isEmpty()){
                System.out.println("Your input cannot be blank!\n");
                continue;
            }

            try {
                return Integer.parseInt(someInt);
            } catch (final InputMismatchException ime) {
                Pattern letter = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
                Matcher hasLetter = letter.matcher(someInt);

                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(someInt);
                boolean b = m.find();

                if (hasLetter.find()) {
                    System.out.println("Sorry! Your input cannot contain any letters, please try again!\n");
                } else if (b) {
                    System.out.println("Sorry!! Your input cannot contain any special characters, please try again!!\n");
                }

                someIntInput.next(); // discard non-int input
                continue; // restart loop, didn't get an integer input
            } catch (final NumberFormatException nfe) {
                System.out.println("Sorry!!! Your input cannot be a decimal number, please try again!!!\n");
                continue;
            }         
        } while (true);
    }

    // Submenu option A which allows users to add a recording to their playlist
    private void runSubMenuA() {
        System.out.println("\nTo make sure your recording is added to your playlist, Please enter your unique id!");

        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);

        System.out.println("Sweet! Now we can get rolling!");
        System.out.println("\nIs this going to be an audio or video recording? (Enter the letter A for audio recording and enter the letter V for video recording)");

        // Getting the recording type determines which lines to print as well as which method to call
        char rType = getTypeOfRecordingResponse();

        if (rType == 'A' || rType == 'a') {
            System.out.println("Awesome! Let's get some information about this audio recording.");
        }

        if (rType == 'v' || rType == 'V') {
            System.out.println("Awesome! Let's get some information about this video recording.");
        }

        System.out.println("\nPlease enter the artist name!");
        String artistName = inputCannotBeBlank();

        System.out.println("\nPlease enter the song name!");
        String songName = inputCannotBeBlank();

        System.out.println("\nPlease enter the duration of the song in seconds!");
        int dur = inputCanOnlyBeInt();

        // Creates recordings in the Playlist class
        Playlist p = new Playlist();

        if (rType == 'A' || rType == 'a') {
            System.out.println("\nPlease enter the bitrate of the song!");
            double bit = inputMustBeDouble();

            p.addA(playlistName, artistName, songName, dur, bit);
            System.out.println("Successfully added your audio recording!\n");
        }

        if (rType == 'v' || rType == 'V') {
            System.out.println("\nPlease enter the framerate of the video!");
            double frame = inputMustBeDouble();

            p.addV(playlistName, artistName, songName, dur, frame);
            System.out.println("Successfully added your video recording!\n");
        }
    }

    // Looks throught the database to see if input matches anything in the arraylist
    // then it returns the index of where it is located (returns -1 if it is not found)
    private int getLocationOfUserObjectByUserId (String userId) {
        for (int i=0; i < userDatabase.size(); i++) {
            if (userId.equals(userDatabase.get(i).getId())) {
                return i;
            } else if ((i == userDatabase.size() - 1)) {
                return -1;
            }
        }
        return -1;
    }

    // Method that returns playlist of user by index
    private String getPlaylistOfUserObjectByIndex (int index) {
        return userDatabase.get(index).getPlaylist();
    }

    // Method that returns the index of where the playlist exists by its name
    private int getPlaylistIndexFromPlaylistName (String playlistName) {
       Playlist p = new Playlist(); 
       return p.getPlaylistIndex(playlistName);
    }

    // Method that returns true if user's id is found in database arraylist
    private boolean doesIdExistInDatabase (String userId) {
        for (int i=0; i < userDatabase.size(); i++) {
            if (userId.equals(userDatabase.get(i).getId())) {
                return true;
            }
        }
        System.out.println("Sorry could not find your user id in our database, please try again!\n");
        return false;
    }

    // Method that gets a char input and checks if it is a valid recording type to save
    private char getTypeOfRecordingResponse() {
        char rType;
        do {
            rType = inputCanOnlyBeChar();
            if (rType == 'a') {
                break;
            }
            if (rType == 'A') {
                break;
            }
            if (rType == 'V') {
                break;
            }
            if (rType == 'v') {
                break;
            }
            System.out.println("Sorry, " + rType + " does not correspond to one of the recording types listed!\n");
        } while (true);
        return rType;
    }

    // Method that finds, reads, and saves recordings frm a csv file
    private void runSubMenuB() {  
        System.out.println("\nTo make sure your recording is added to your playlist, Please enter your unique id!");

        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);

        File file;
        System.out.println("\nPlease enter the name of the file you want the program to read! Also if you change your mind enter 'BACK' to go back. (Note: only csv files can be read and also enter the name of the file without the .csv ending)");

        Boolean notEscape = true;
        do{         
            try{
                file = tryToObtainValidFile();

                if(file.getName().equals("BACK.csv")){
                    notEscape = false;
                    break;
                } else if(file.exists() && file.isFile() && file.canRead()) {
                    break;
                }
            } catch (FileNotFoundException fnf) {
                fnf.printStackTrace();
                System.out.println("\nSorry! That file doesn't exist on this device, please try again!");
            }
        } while (true);

        System.out.println("\nFound a readable file with that name on this device!");
        if(notEscape){
            tryToReadFileProvided(file, playlistName);        
        }
    }

    // Method to creates a string of the file name with the appropriate endings and returns that string
    private File tryToObtainValidFile() throws FileNotFoundException {
        String fileName = inputCannotBeBlank();
        if(fileName.equals("BACK")){
            return new File("BACK.csv");
        }
        return new File(fileName+".csv");
    }

    // Method that tries to read the file that was provided and if successful
    // saves the recordings listed in the file onto the specified playlist
    private void tryToReadFileProvided(File file, String playlistName) {
        String line="";
        do {
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                line = br.readLine();
            
                while (line != null) {
                    // getting line and splitting the commas and creating recordings if not null
                    String[] splitLine=null;
                    if(line!=null){
                        splitLine = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    }
                    if(splitLine!=null){
                        createRecordingsFromReadFile(splitLine, playlistName);
                    }else{
                        System.out.println("Sorry! Provided file is blank, please try importing another file!");
                        break;
                    }
                    line = br.readLine();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } while (line!=null);
    }

    // Method that creates recordings from the file the array passed in and saves it to specified playlist
    private void createRecordingsFromReadFile(String[] recordingFromFile, String playlistName){
        Playlist p = new Playlist();
        if(recordingFromFile[0].equals("A")){
            p.addA(playlistName, recordingFromFile[1], recordingFromFile[2], Integer.parseInt(recordingFromFile[3]), Double.parseDouble(recordingFromFile[4]));
        } else if(recordingFromFile[0].equals("V")){
            p.addV(playlistName, recordingFromFile[1], recordingFromFile[2], Integer.parseInt(recordingFromFile[3]), Double.parseDouble(recordingFromFile[4]));
        }
    }

    // Method to get user to add all recordings from aanother user's palylist into their own
    private void runSubMenuC() {
        System.out.println("\nAlright, please enter the unique id of the individual's playlist in which you want to copy in your own!");

        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);
        int playlistIndex = getPlaylistIndexFromPlaylistName(playlistName);

        System.out.println("\nAwesome, going to add the playlist " + playlistName + " to your own!\n");
        System.out.println("To make sure this will go into your playlist, please enter your unique id!");

        String userId2;
        do {
            userId2 = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId2));

        int userObjectIndex2 = getLocationOfUserObjectByUserId(userId2);
        String playlistName2 = getPlaylistOfUserObjectByIndex(userObjectIndex2);
        int playlistIndex2 = getPlaylistIndexFromPlaylistName(playlistName2);

        System.out.println("\nAwesome, going to add the playlist " + playlistName2 + " to your own!\n");

        // Adds all songs into the users playlist
        Playlist p = new Playlist();
        p.add(playlistIndex, playlistIndex2);
        System.out.println("\nSuccess!!!!\n");
    }

    // Method that allows the user to remove a recording from their playlist
    private void runSubMenuD() {
        System.out.println("\nTo make sure we find the correct recording from the correct playlist, please enter your unique id! ");

        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);
        int playlistIndex = getPlaylistIndexFromPlaylistName(playlistName);

        Playlist p = new Playlist();
        if((p.recordings.get(playlistIndex)).isEmpty()){
            System.out.println("Sorry! There are no recordings in this playlist.\nSending you back to the main menu.\n");
            return;
        }

        System.out.println("\nAwesome! We found you on our database. Just to be sure, is the playlist "+playlistName+" the one you wanted to access? (Enter Y for yes, N for no)");
        
        char choice;
        do{
            choice = inputCanOnlyBeChar();
            if(choice=='Y'||choice=='y'){
                System.out.println("Good! Let's proceed.");
                break;
            }else if(choice=='N'||choice=='n'){
                System.out.println("That's ok! We will send you back to the menu.\n");
                break;
            }
        }while(true);

        if(choice=='N'||choice=='n'){
            return;
        }

        System.out.println("\nDo you want to remove a recording by its name or by the spot it is in your playlist? (Enter name for the 1st choice or num for the second)");
        
        String nameOrNum;
        do{
            nameOrNum=inputCanOnlyBeLetters();
            if(nameOrNum.equals("name")){
                break;
            } else if(nameOrNum.equals("num")){
                break;
            }
            System.out.println("You did not enter a valid word choice, please try again!");
        }while(true);

        if(nameOrNum.equals("name")){
            System.out.println("\nSweet! What is the name of the song you want to get rid of? (Note: You can enter BACK if you want return to the menu)");
            do{

                String nameOfRecording=inputCanOnlyBeLetters();

                if(nameOfRecording.equals("BACK")){
                    System.out.println("Going back to the menu!\n");
                    break;
                }

                for(int i=0; i<p.recordings.get(playlistIndex).size(); i++){
                    Recording r = p.recordings.get(playlistIndex).get(i);
                    if(nameOfRecording.equals(r.getSongName())){
                        System.out.println(r.getSongName());
                        System.out.println("---");
                        p.recordings.get(playlistIndex).remove(i);
                        p.recordingsStats.get(playlistIndex).remove(i);
                        System.out.println("Successfully removed recording from your playlist!\n");
                        return;
                    }
                }

                System.out.println("Sorry! Could not find that specific recording in your playlist, please try again!\n");

            }while(true);
        }
        else if(nameOrNum.equals("num")){
            System.out.println("\nSweet! What is the spot number of the song you want to get rid of? (Note: You can enter BACK if you want return to the menu)");
            
            do{
                int numOfRecording=-1;
                String tempInput="";

                do{
                    try{
                        tempInput=inputCannotBeBlank();
                        numOfRecording=Integer.parseInt(tempInput);
                        if((numOfRecording-1)>-1){
                            numOfRecording=(numOfRecording-1);
                            break;
                        }
                    }catch(NumberFormatException nfe){
                        if(tempInput.equals("BACK")){
                            System.out.println("Going back to the menu!\n");
                            return;
                        }
                    }
                }while(true);

                for(int i=0; i<p.recordings.get(playlistIndex).size(); i++){
                    if((p.recordings.get(playlistIndex).get(numOfRecording)).equals(p.recordings.get(playlistIndex).get(i))){
                        p.recordings.get(playlistIndex).remove(i);
                        p.recordingsStats.get(playlistIndex).remove(i);
                        System.out.println("Successfully removed recording from your playlist!\n");
                        System.out.println(p.recordings.get(playlistIndex));
                        return;
                    }
                }

                System.out.println("Sorry! Could not find that specific recording in your playlist, please try again!\n");

            }while(true);
        }
    }

    // Allows a user to play a recording that they choose
    private void runSubMenuE() {
        System.out.println("\nTo make sure we find the correct recording from the correct playlist, please enter your unique id! ");

        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);
        int playlistIndex = getPlaylistIndexFromPlaylistName(playlistName);

        Playlist p = new Playlist();
        if((p.recordings.get(playlistIndex)).isEmpty()){
            System.out.println("Sorry! There are no recordings in this playlist.\nSending you back to the main menu.\n");
            return;
        }

        System.out.println("\nAwesome! We found you on our database. Just to be sure, is the playlist "+playlistName+" the one you wanted to access? (Enter Y for yes, N for no)");
        
        char choice;
        do{
            choice = inputCanOnlyBeChar();
            if(choice=='Y'||choice=='y'){
                System.out.println("Good! Let's proceed.");
                break;
            }else if(choice=='N'||choice=='n'){
                System.out.println("That's ok! We will send you back to the menu.\n");
                // break;
                return;
            }
        }while(true);

        System.out.println("\nDo you want to play a recording by its name or by the spot it is in your playlist? (Enter name for the 1st choice or num for the second)");
        
        String nameOrNum;
        do{
            nameOrNum=inputCanOnlyBeLetters();
            if(nameOrNum.equals("name")){
                break;
            } else if(nameOrNum.equals("num")){
                break;
            }
            System.out.println("You did not enter a valid word choice, please try again!");
        }while(true);

        if(nameOrNum.equals("name")){
            System.out.println("\nSweet! What is the name of the song you want to play? (Note: You can enter BACK if you want return to the menu)");
            
            do{
                String nameOfRecording=inputCanOnlyBeLetters();

                if(nameOfRecording.equals("BACK")){
                    System.out.println("Going back to the menu!\n");
                    break;
                }

                for(int i=0; i<p.recordings.get(playlistIndex).size(); i++){
                    Recording r = p.recordings.get(playlistIndex).get(i);

                    if(nameOfRecording.equals(r.getSongName())){
                        System.out.println("\nNow playing: "+p.recordings.get(playlistIndex).get(i));
                        p.incrementRecordingStats(playlistIndex, i);
                        realTimeCounter(r.getDuration());
                        System.out.println("Successfully played recording from your playlist!\n");
                        return;
                    }
                }

                System.out.println("Sorry! Could not find that specific recording in your playlist, please try again!\n");

            }while(true);
        }
        else if(nameOrNum.equals("num")){
            System.out.println("\nSweet! What is the spot number of the song you want to play? (Note: You can enter BACK if you want return to the menu)");
            
            do{
                int numOfRecording=-1;
                String tempInput="";

                do{
                    try{
                        tempInput=inputCannotBeBlank();
                        numOfRecording=Integer.parseInt(tempInput);
                        if((numOfRecording-1)>-1){
                            numOfRecording=(numOfRecording-1);
                            break;
                        }
                    }catch(NumberFormatException nfe){
                        if(tempInput.equals("BACK")){
                            System.out.println("Going back to the menu!\n");
                            return;
                        }
                    }
                }while(true);

                for(int i=0; i<p.recordings.get(playlistIndex).size(); i++){
                    if((p.recordings.get(playlistIndex).get(numOfRecording)).equals(p.recordings.get(playlistIndex).get(i))){
                        System.out.println("\nNow playing: "+p.recordings.get(playlistIndex).get(i));
                        p.incrementRecordingStats(playlistIndex, i);
                        Recording r = p.recordings.get(playlistIndex).get(i);
                        realTimeCounter(r.getDuration());
                        System.out.println("Successfully played recording from your playlist!\n");
                        return;
                    }
                }

                System.out.println("Sorry! Could not find that specific recording in your playlist, please try again!\n");

            }while(true);
        }
    }

    // Method that allows a user to play their complete playlist
    private void runSubMenuF() {
        System.out.println("\nPlease enter the uniqueID of the individual who you want to play the complete playlist of in order: ");
        
        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);
        int playlistIndex = getPlaylistIndexFromPlaylistName(playlistName);

        Playlist p = new Playlist();
        if((p.recordings.get(playlistIndex)).isEmpty()){
            System.out.println("Sorry! There are no recordings in this playlist.\nSending you back to the main menu.\n");
            return;
        }

        System.out.println();

        int count=0;
        for(Recording r: p.recordings.get(playlistIndex)){
            System.out.println("Now playing: "+r);
            p.incrementRecordingStats(playlistIndex, count);
            realTimeCounter(r.getDuration());
            System.out.println("Finished playing! Going onto the next recording.\n");
            count++;
        }
    }

    // Method that allows another method to go on until the passed in duration
    // equals the amount of time that has passed
    private void realTimeCounter(int duration){
        long start = System.currentTimeMillis();
        System.out.print("Progress: 0%");

        byte first = 0;
        byte second = 0;
        byte third = 0;
        byte fourth = 0;
        byte fifth = 0;

        do{
            if(( ( duration/6.0 )==( ( (System.currentTimeMillis() - start) / 1000.0) ) ) && (first == 0)){
                System.out.print(" ..16.67%");
                first++;
            }

            if(( ( duration*2/6.0 )==( ( (System.currentTimeMillis() - start) / 1000.0) )) && (second == 0)){
                System.out.print(" ..33.33%");
                second++;
            }

            if(( ( duration*3/6.0 )==( ( (System.currentTimeMillis() - start) / 1000.0) )) && (third == 0)){
                System.out.print(" ..50%");
                third++;
            }

            if(( ( duration*4/6.0 )==( ( (System.currentTimeMillis() - start) / 1000.0) )) && (fourth == 0)){
                System.out.print(" ..66.67%");
                fourth++;
            }

            if(( ( duration*5/6.0 )==( ( (System.currentTimeMillis() - start) / 1000.0) )) && (fifth == 0)){
                System.out.print(" ..83.33%");
                fifth++;
            }

            if(duration == ( (System.currentTimeMillis() - start) / 1000 ) ){
                System.out.println(" ..100%");
                break;
            }
        }while(true);
    }

    // Method that lets a user play their playlist in complete random order for all recordings
    private void runSubMenuG() {
        System.out.println("\nPlease enter the uniqueID of the individual who you want to play the complete playlist of in random order: ");
        
        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);
        int playlistIndex = getPlaylistIndexFromPlaylistName(playlistName);

        Playlist p = new Playlist();
        if((p.recordings.get(playlistIndex)).isEmpty()){
            System.out.println("Sorry! There are no recordings in this playlist.\nSending you back to the main menu.\n");
            return;
        }

        int[] array = new int[p.recordings.get(playlistIndex).size()];
        int[] shuffledArray = shuffleArray(array);

        System.out.println();

        for(int i=0; i<shuffledArray.length; i++){
            System.out.println("Now playing: "+p.recordings.get(playlistIndex).get(shuffledArray[i]));
            Recording r = p.recordings.get(playlistIndex).get(shuffledArray[i]);
            p.incrementRecordingStats(playlistIndex, i);
            realTimeCounter(r.getDuration());
            System.out.println("Successfully played recording from your playlist!\n");
        }
    }

    // Method that shuffles a users playlist in an array and returns it
    private int[] shuffleArray(int[] arr) {
        int[] shuffledArray = new int[arr.length];
        for (int i=0; i<arr.length; i++) {
            arr[i]=-1;
        }

        int count=0;
        do{
            int r = (int) (Math.random()*arr.length);
            if(count==(arr.length-1)){
                break;
            } else if(Arrays.stream(shuffledArray).anyMatch(i -> i == r)){
                continue;
            }

            shuffledArray[count]=r;
            count++;
        }while(true);
        return shuffledArray;
    }

    // Method that allows user to save their playlist's recordings onto an external file
    private void runSubMenuH(){
        System.out.println("\nPlease enter the uniqueID of the individual who you want to save the playlist of externally in a csv file: ");
        
        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);
        int playlistIndex = getPlaylistIndexFromPlaylistName(playlistName);

        Playlist p = new Playlist();
        if((p.recordings.get(playlistIndex)).isEmpty()){
            System.out.println("Sorry! There are no recordings in this playlist.\nSending you back to the main menu.\n");
            return;
        }

        String user=userDatabase.get(userObjectIndex).getName();
        SimpleDateFormat formatter = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
        Date date = new Date();
        String saveFileName = (user.toUpperCase() + "_" + playlistName.toUpperCase() + "_" + formatter.format(date) + ".csv");

        BufferedWriter outputWriter = null;
        try {
            outputWriter = new BufferedWriter(new FileWriter(saveFileName));
        } catch (IOException ioe) {
            System.out.println("Sorry! There seems to be an error in creating your playlist file!!");
        }

        for (int i = 0; i < p.recordings.get(playlistIndex).size(); i++) {
            try {
                if('A'==(p.recordings.get(playlistIndex).get(i).getType())){
                    AudioRecording ar = (AudioRecording) p.recordings.get(playlistIndex).get(i);
                    outputWriter.write(ar.getType()+","+ar.getSongName()+","+ar.getArtist()+","+ar.getDuration()+","+ar.getBitrate()+"\n");
                } else if('V'==(p.recordings.get(playlistIndex).get(i).getType())){
                    VideoRecording vr = (VideoRecording) p.recordings.get(playlistIndex).get(i);
                    outputWriter.write(vr.getType()+","+vr.getSongName()+","+vr.getArtist()+","+vr.getDuration()+","+vr.getFrameRate()+"\n");
                }
            } catch (IOException ioe) {
                System.out.println("Sorry!!! There seems to be an error in writing your recordings out onto the file!!!!");
            }
        }

        try {
            outputWriter.flush();
            outputWriter.close();
        } catch (IOException e) {
            System.out.println("Error flushing and/or closing.");
        }  

        System.out.println("\nFinished!\n");
    }

    // Method that allows the user to see how many times they have played their recordings from highest to lowest
    private void runSubMenuI(){
        System.out.println("\nPlease enter the uniqueID of the individual who you want to see the top played recordings of: ");
        
        String userId;
        do {
            userId = inputPositiveIntChecker();
        } while (!doesIdExistInDatabase(userId));

        int userObjectIndex = getLocationOfUserObjectByUserId(userId);
        String playlistName = getPlaylistOfUserObjectByIndex(userObjectIndex);
        int playlistIndex = getPlaylistIndexFromPlaylistName(playlistName);

        Playlist p = new Playlist();
        if((p.recordingsStats.get(playlistIndex).get(0)) == 0){
            if(p.recordingsStats.get(playlistIndex).stream().allMatch(p.recordingsStats.get(playlistIndex).get(0)::equals)){
                System.out.println("\nSorry! You have not played any recordings in this playlist.\nSending you back to the main menu.\n");
                return;
            }
        }

        Collections.sort(p.recordingsStats.get(playlistIndex), Collections.reverseOrder());
        System.out.println();

        for (int i = 0; i < p.recordings.get(playlistIndex).size(); i++) {
            System.out.println(p.recordings.get(playlistIndex).get(i).getArtist() + " - " + p.recordings.get(playlistIndex).get(i).getSongName() + " - " + p.recordingsStats.get(playlistIndex).get(i));
        }

        System.out.println("\nDone! Sending you back to the menu!!\n");
    }
}