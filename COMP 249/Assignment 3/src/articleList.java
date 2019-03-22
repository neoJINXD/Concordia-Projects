// ----------------------------------------------------
// Assignment 3
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.util.ArrayList;

/**
 * Class made to read through a formatted string in order to create all the articles, from every single file
 * @author Anik Patel - 40091908
 */
public class articleList {
    private ArrayList<Article> library = new ArrayList<Article>();

    /**
     * Constructor to parse through the strings and create every Article object
     * @param input array of strings containing everything that was read form the files
     */
    public articleList(String[] input) {
        Article currentArticle = new Article();
        for (int i = 0; i < input.length; i++) {

            if (input[i].contains("@ARTICLE")) {
                ++i;
                currentArticle.setiD(input[i]);
                continue;
            }
            if (input[i].contains("author")) {
                ++i;
                currentArticle.setAuthor(input[i]);
                continue;
            }
            if (input[i].contains("journal")) {
                ++i;
                currentArticle.setJournal(input[i]);
                continue;
            }
            if (input[i].contains("title")) {
                ++i;
                currentArticle.setTitle(input[i]);
                continue;
            }
            if (input[i].contains("year")) {
                ++i;
                currentArticle.setYear(input[i]);
                continue;
            }
            if (input[i].contains("volume")) {
                ++i;
                currentArticle.setVolume(input[i]);
                continue;
            }
            if (input[i].contains("number")) {
                ++i;
                currentArticle.setNumber(input[i]);
                continue;
            }
            if (input[i].contains("pages")) {
                ++i;
                currentArticle.setPages(input[i]);
                continue;
            }
            if (input[i].contains("keywords")) {
                ++i;
                currentArticle.setKeywords(input[i]);
                continue;
            }
            if (input[i].contains("doi")) {
                ++i;
                currentArticle.setDoi(input[i]);
                continue;
            }
            if (input[i].contains("ISSN")) {
                ++i;
                currentArticle.setISSN(input[i]);
                continue;
            }
            if (input[i].contains("month")) {
                ++i;
                currentArticle.setMonth(input[i]);
                continue;
            }
            //Stores full Article and creates a new one
            if (!currentArticle.notFull()) {
                library.add(currentArticle);
                currentArticle = new Article();
            }

        }
    }

    /**
     * Accessor method to obtain an ArrayList of all the Articles that were made
     * @return ArrayList containing all the Articles
     */
    public ArrayList<Article> getLibrary() {
        return library;
    }
}
