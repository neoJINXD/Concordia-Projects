// ----------------------------------------------------
// Assignment 3
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

/**
 * This is the class that will contain all the information of each article
 * @author Anik Patel - 40091908
 */
public class Article {
    private String iD;
    private String author;
    private String journal;
    private String title;
    private String year;
    private String volume;
    private String number;
    private String pages;
    private String keywords;
    private String doi;
    private String ISSN;
    private String month;

    /**
     * Method to check if an article has been filled with all the info
     * @return boolean, true if some parameters are still null
     */
    public boolean notFull() {
        return ((this.iD == null) || (this.author == null) || (this.journal == null) || (this.title == null) || (this.year == null) || (this.volume == null) || (this.number == null) || (this.pages == null) || (this.keywords == null) || (this.doi == null) || (this.ISSN == null) || (this.month == null));
    }

    /**
     * Default constructor setting everything to null
     */
    public Article() {
        iD = null;
        author = null;
        journal = null;
        title = null;
        year = null;
        volume = null;
        number = null;
        pages = null;
        keywords = null;
        doi = null;
        ISSN = null;
        month = null;
    }


    /**
     * Mutator method for id
     * @param iD iD of each Article
     */
    public void setiD(String iD) {
        this.iD = iD;
    }
    /**
     * Mutator method for author
     * @param author String with all the authors
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Mutator method for journal
     * @param journal String of the journal
     */
    public void setJournal(String journal) {
        this.journal = journal;
    }

    /**
     * Mutator method for title
     * @param title String of the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Mutator method for year
     * @param year String of the year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Mutator method for volume
     * @param volume String of the volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * Mutator method for number
     * @param number String for number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Mutator method for pages
     * @param pages String for pages
     */
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * Mutator method for keywords
     * @param keywords String containing all the keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Mutator method for doi
     * @param doi String for doi
     */
    public void setDoi(String doi) {
        this.doi = doi;
    }

    /**
     * Mutator method for issn
     * @param ISSN String for issn
     */
    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    /**
     * Mutator method for month
     * @param month String for month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Accessor method for id
     * @return String for id
     */
    public String getiD() {
        return iD;
    }

    /**
     * Accessor method for authors
     * @return String containing all the authors
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Accessor method for journal
     * @return String for journal
     */
    public String getJournal() {
        return journal;
    }

    /**
     * Accessor method for title
     * @return String for title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor method for year
     * @return String for year
     */
    public String getYear() {
        return year;
    }

    /**
     * Accessor method for volume
     * @return String for volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * Accessor method for number
     * @return String for number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Accessor method for pages
     * @return String for pages
     */
    public String getPages() {
        return pages;
    }

    /**
     * Accessor method for keywords
     * @return String containing all the keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Accessor method for doi
     * @return String for doi
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Accessor method for issn
     * @return String for issn
     */
    public String getISSN() {
        return ISSN;
    }

    /**
     * Accessor method for month
     * @return String for month
     */
    public String getMonth() {
        return month;
    }
}
