// ----------------------------------------------------
// Assignment 3
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.*;

/**
 * Main class containing the driver to run the program
 * @author Anik Patel - 40091908
 */
public class AuthorBibCreator {

    private static String[] expectedFiles = {".\\Latex1.bib", ".\\Latex10.bib", ".\\Latex2.bib", ".\\Latex3.bib", ".\\Latex4.bib", ".\\Latex5.bib", ".\\Latex6.bib", ".\\Latex7.bib", ".\\Latex8.bib", ".\\Latex9.bib"};
    //ArrayLists to contain all the paths and the info from all the files
    private static ArrayList<String> bibPaths = new ArrayList<>();
    private static ArrayList<String> allFiles = new ArrayList<>();
    private static articleList allArticles;


    /**
     * Static method that gets a Scanner so that it can ask the user for an author to search for
     * @param input Scanner object to get user input
     * @return String containing the author to search for, formatted as uppercase first letter and lowercase for the rest
     */
    public static String getAuthor(Scanner input) {
        System.out.print("Please enter the name of the author you are searching for: ");
        String author = input.next();
        input.close();
        //Ignores input's capitalization
        author = author.substring(0, 1).toUpperCase() + author.substring(1).toLowerCase();
        return author;
    }

    /**
     * Static method that will read all the .bib files present in the directories
     * @param sc Scanner object to read all the files that need to be read
     * @throws FileNotFoundException thrown when there is a file that is missing
     */
    public static void readFiles(Scanner sc) throws FileNotFoundException {
        //Initializes the directory
        File[] paths = new File(".").listFiles();
        //A temporary String var, to be used later
        String spleet = "";

        //Gets the absolute paths to all the .bib files that are in the default directory
        for (File i : paths) {
            if (i.getPath().matches(".*\\.bib"))
                bibPaths.add(i.getName());
        }
        //Detects if there are less files than expected
        if (bibPaths.toArray().length != expectedFiles.length) {
            System.out.println("There is a file missing!");
            throw new FileNotFoundException();
        } else {
            System.out.println("All the proper files where found, continuing the program...");
        }
        for (String j : bibPaths) {
            //Prints the paths of found .bib files
            //System.out.println("Opening file: " + j);
            String file = "";
            String splitFile = "";
            sc = new Scanner(new FileInputStream(j));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                file += line;
                file += "\n";
            }

            for (String i : file.split("[\n{}]+")) {
                splitFile += i;
                splitFile += "\n";
            }

            String[] split = splitFile.split("[\n]");
            for (String i : split) {
                //System.out.println(i);
                if (i.matches("\\s"))
                    continue;
                else
                    spleet += i + "\n";
            }

            String[] inputList = spleet.split("[\\n]+");

            for (String i : inputList)
                if (i.matches("\\s"))
                    continue;
                else
                    allFiles.add(i);

            spleet = "";

            //System.out.println("CLOSING SCANNER");
            sc.close();
        }
    }

    /**
     * Static method to check if the files with the author that is searched for already exists
     * @param author Author to check for
     * @throws FileExistsException thrown when a file is found with the author's name
     */
    public static void checkFiles(String author) throws FileExistsException {
        File[] dir = new File(".").listFiles();
        //Initial file checking, assuming there are no files with author already existing
        boolean[] result = {false, false, false};
        boolean toThrowException = false;
        for (File r : dir) {
            String file = r.getName();
            if (file.contains(author)) {
                if (file.matches(".*IEEE\\.json"))
                    result[0] = true;
                else if (file.matches(".*ACM\\.json"))
                    result[1] = true;
                else if (file.matches(".*NJ\\.json"))
                    result[2] = true;
            }
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i] == true)
                toThrowException = true;
        }
        if (toThrowException)
            throw new FileExistsException(result);
    }

    /**
     * Static method to create a String with all the message
     * @param name Name of the file that exists
     * @return Full String with the proper message
     */
    public static String fileExistsMsg(String name) {
        return "Found a file that already exists with the name: " + name + "\nThe already existing file will be renamed to: " + name.substring(0, name.length() - 5) + "-BU.json, and any and all BU files existing prior will be deleted\n";
    }

    /**
     * Static method to turn all the existing files into "-BU.json" files
     * @param author Author that is searched for
     * @param fileCheck Array of booleans obtained from FileExistsException that says which files needs to be backed up
     * @throws IOException thrown when old file could not be copied
     */
    public static void backup(String author, boolean[] fileCheck) throws IOException {
        //System.out.println("BACKING UP");
        File[] dir = new File(".").listFiles();
        String oldName;

        for (File i : dir) {
            Path DestinationPath = Paths.get(i.getAbsolutePath().substring(0, i.getAbsolutePath().length() - 5) + "-BU.json");
            Path IEEESource = null;
            Path ACMSource = null;
            Path NJSource = null;
            if (fileCheck[0]) {
                //IEEE Format
                oldName = i.getName();

                if (oldName.contains(author)) {
                    if (oldName.matches(".*IEEE\\.json")) {
                        IEEESource = Paths.get(i.getAbsolutePath());
                        System.out.println(fileExistsMsg(oldName));
                        Files.copy(IEEESource, DestinationPath, REPLACE_EXISTING);
                    }
                }
            }
            if (fileCheck[1]) {
                //ACM Format
                oldName = i.getName();

                if (oldName.contains(author)) {
                    if (oldName.matches(".*ACM\\.json")) {
                        ACMSource = Paths.get(i.getAbsolutePath());
                        System.out.println(fileExistsMsg(oldName));
                        Files.copy(ACMSource, DestinationPath, REPLACE_EXISTING);
                    }
                }
            }
            if (fileCheck[2]) {
                //NJ Format
                oldName = i.getName();

                if (oldName.contains(author)) {
                    if (oldName.matches(".*NJ\\.json")) {
                        NJSource = Paths.get(i.getAbsolutePath());
                        System.out.println(fileExistsMsg(oldName));
                        Files.copy(NJSource, DestinationPath, REPLACE_EXISTING);
                    }
                }
            }
            if (IEEESource != null)
                Files.deleteIfExists(IEEESource);
            if (ACMSource != null)
                Files.deleteIfExists(ACMSource);
            if (NJSource != null)
                Files.deleteIfExists(NJSource);
        }
        System.out.println();
    }


    /**
     * Static method to write up all the output files with the proper formatting
     * @param author Author to search for
     * @throws FileNotFoundException thrown when a new file could not be made
     */
    public static void outputFiles(String author) throws FileNotFoundException {
        ArrayList<Article> foundArticles = new ArrayList<>();
        PrintWriter writerIEEE = new PrintWriter(author + "-IEEE.json");
        PrintWriter writerACM = new PrintWriter(author + "-ACM.json");
        PrintWriter writerNJ = new PrintWriter(author + "-NJ.json");
        int ArticleCounter = 0;

        for (Article i : allArticles.getLibrary()) {
            if (i.getAuthor().contains(author)) {
                foundArticles.add(i);
                ArticleCounter++;
            }
        }
        int ACMcounter = 1;
        for (Article i : foundArticles) {
            String[] authors = i.getAuthor().split("and");
            String ArticleAuthorsIEEE = "";
            String ArticleAuthorsNJ = "";
            //IEEE
            for (int x = 0; x < authors.length; x++) {
                ArticleAuthorsIEEE += authors[x];
                if (x < authors.length - 1)
                    ArticleAuthorsIEEE += ", ";
            }
            writerIEEE.println(ArticleAuthorsIEEE + ", \"" + i.getTitle() + "\", " + i.getJournal() + ", vol. " + i.getVolume() + ", no. " + i.getNumber() + ", p. " + i.getPages() + ", " + i.getMonth() + i.getYear() + "\n");

            //ACM
            writerACM.println("[" + ACMcounter + "]\t\t" + authors[0] + "et al. " + i.getYear() + ". " + i.getTitle() + ". " + i.getJournal() + ". " + i.getVolume() + ", " + i.getNumber() + " (" + i.getYear() + "), " + i.getPages() + ". DOI:https://doi.org/" + i.getDoi() + ".\n");
            ACMcounter++;

            //NJ
            for (int x = 0; x < authors.length; x++) {
                ArticleAuthorsNJ += authors[x];
                if (x < authors.length - 1)
                    ArticleAuthorsNJ += " & ";
            }
            writerNJ.println(ArticleAuthorsNJ + ". " + i.getTitle() + ". " + i.getJournal() + ". " + i.getVolume() + ", " + i.getPages() + "(" + i.getYear() + ").\n");

        }
        System.out.println("\nThere are " + ArticleCounter + " records of author(s) with the name: " + author + "\nFiles named " + author + "-IEEE.json, " + author + "-ACM.json, and " + author + "-NJ.json have been created.\n");
        writerIEEE.close();
        writerACM.close();
        writerNJ.close();
    }

    /**
     * Static method that serves as the core engine of the program. Catches all the exceptions thrown from the other static methods.
     * @param input Scanner that serves as user input
     * @param sc Scanner that serves to read files
     */
    public static void processBibFiles(Scanner input, Scanner sc) {
        //Get user input for author
        String searchingFor = getAuthor(input);
        System.out.println();
        try {
            checkFiles(searchingFor);
            System.out.println("No preexisting files have been found, proceeding with the search...\n");
        } catch (FileExistsException e) {
            //System.out.println(e.getMessage());
            try {
                backup(searchingFor, e.getFilesFound());
            } catch (IOException t) {
                System.out.println(t.getMessage());
            }
        }

        try {
            readFiles(sc);
        } catch (FileNotFoundException e) {
            //Catches and identifies the missing file
            for (String x : expectedFiles) {
                boolean foundMissing = true;
                for (String y : bibPaths) {
                    if (x.equals(y)) {
                        foundMissing = false;
                        break;
                    }
                }
                if (foundMissing) {
                    System.out.println(x + " is missing. Terminating program.");
                    break;
                }
            }
        }
        //Sets up all the articles as objects
        String[] stringArticles = allFiles.toArray(new String[0]);
        allArticles = new articleList(stringArticles);

        try {
            outputFiles(searchingFor);
        } catch (FileNotFoundException e) {
            System.out.println("File could not be created! Deleting all files that were created and exiting program!");
            try {
                deleteThis(searchingFor);
            } catch (IOException t) {
                System.out.println(t.getMessage());
            }
        } finally {
            //System.out.println("Closed the PrintWriter");
        }
    }

    /**
     * Static method to delete the files that have been created if the output method fails
     * @param author Author to check for
     * @throws IOException thrown when a file could not be deleted
     */
    public static void deleteThis(String author) throws IOException {
        //Directory
        File[] dir = new File(".").listFiles();
        //Loop to delete all the files that contain author name if output fails
        for (File i : dir) {
            if (i.getName().contains(author)) {
                Path source = Paths.get(i.getAbsolutePath());
                Files.deleteIfExists(source);
            }
        }

    }

    /**
     * Main method that runs the program
     * @param args
     */
    public static void main(String[] args) {
        //Start message
        System.out.println("Welcome to the Article Searcher!\n");
        //Initializations of Scanners
        Scanner input = new Scanner(System.in);
        Scanner sc = null;
        //Processes the reading and output of files
        processBibFiles(input, sc);
        //Exit message
        System.out.println("Program has completed the task, Thank you for using it!");
    }
}
