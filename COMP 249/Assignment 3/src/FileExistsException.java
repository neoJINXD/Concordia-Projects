// ----------------------------------------------------
// Assignment 3
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

/**
 * Custom exception made to be thrown when there already is a file with the author you are searching for
 * @author Anik Patel - 40091908
 */
public class FileExistsException extends Exception {

    private boolean[] filesFound;

    /**
     * Constructor to throw a specific message and to store an array that says which files already exists
     * @param in takes in an array of booleans such that [IEEE exists, ACM exists, NJ exists], position is true if file does exist
     */
    public FileExistsException(boolean[] in){
        super("Exception! There is already an existing file for that author. The file will be renamed to BU, and older BU files will be deleted!");
        filesFound = in;
    }

    /**
     * Accessor method for the array of booleans
     * @return array of booleans
     */
    public boolean[] getFilesFound(){
        return filesFound;
    }
}
