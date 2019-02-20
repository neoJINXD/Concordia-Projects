// ---------------------------------------------------------------
// Assignment 3
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------

/*  This program will simulate FIFA tournaments where 16 countries play in it, their goals
    for each match will be randomized, giving an extra point to a randomly chosen team if it
    happens to result in a tie.
    It will also keep each goals saved to calculate average scores for each tournament.
    The user will pick a team, if that team is not in the tournament, the program will
    be terminated, if they are playing, the program will simulate up to 20 tournaments to
    see if the chosen team will win.
 */


import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

public class Question_1 {

    public static void main (String[] args)
    {
        //Welcome message
        System.out.println("Welcome to the FIFA World Cup of 2018\n");

        //Initializes Keyboard input
        Scanner input = new Scanner(System.in);
        //Initializes the random class for randomizing scores
        Random rand = new Random();

        //Initializes all the arrays
        //2D array to hold the teams and the winners of each respective round
        //Where the first index is for the round, and the second index is for the teams in said round
        String[][] tourney = new String[5][16];
        String[] team16 = {"Uruguay", "Portugal", "France", "Argentina", "Brazil",
                "Mexico", "Belgium", "Japan", "Spain", "Russia", "Croatia", "Denmark",
                "Sweden", "Switzerland", "Colombia", "England"};
        tourney[0] = team16;         //Round of 16

        //Initializes 2D array to hold the scores of each match that happens
        int[][] goals = new int[20][15];

        //Initializes variables needed
        boolean condition = false;
        String userTeam;
        int tournamentNumber = 0;
        int goal1, goal2;
        int tieBreaker;
        int scoreIterator;
        int count = 0;
        int countScores = 0;
        int countOverAverage = 0;
        double average=0, totalAverage=0;

        //Getting user input for a chosen team

        System.out.print("Please enter your favorite team: ");
        userTeam = input.nextLine();
        userTeam = userTeam.replaceAll(" ","");
        userTeam = userTeam.toLowerCase();
        for (int i=0 ; i<team16.length-1 ; i++)
            if (team16[i].equalsIgnoreCase(userTeam))
            {
                condition = true;
                break;
            }
        //Checks the condition if team is in the tournament
        //If the chosen team is not playing, force kills the program with a message
        if (!condition)
        {
            System.out.println("Error, the team you have specified is not in these tournaments\nProgram Terminated.\nHave a nice day!");
            System.exit(0);
        }



        //Displays user's selected team
        System.out.println("\nThe chosen team is "+userTeam+"\n");


        //Loop for the tournaments
        do {
            scoreIterator = 0;
            System.out.println("This is tournament #"+(tournamentNumber+1));
            //Loop for each round
            for(int i=0; i<4; i++)
            {

                //Tells the user what each round is
                switch(i)
                {
                    case 0:
                        System.out.print("Round of 16: ");
                        break;
                    case 1:
                        System.out.print("Quarter Finals: ");
                        break;
                    case 2:
                        System.out.print("Semi Finals: ");
                        break;
                    case 3 :
                        System.out.print("Finals: ");
                        break;
                }
                //count is reset each round since each round is the first dimension in the 2D array tourney
                count = 0;

                //Loop for each match
                for (int match=0; match<16; match+=2)
                {
                    //If either position is empty, goes to the next set
                    if(tourney[i][match]==null || tourney[i][match+1]==null)
                       continue;
                    //Randomized goals from 0 to 4
                    goal1 = rand.nextInt(5);
                    goal2 = rand.nextInt(5);

                    //Randomizes which team wins if the first randomizing of goals results in a tie
                    if (goal1 == goal2)
                    {
                        tieBreaker = rand.nextInt(2);
                        if (tieBreaker == 0)
                            goal1++;
                        else if (tieBreaker == 1)
                            goal2++;
                    }
                    //Addds the sum of both goals to the 2D array that contains all goals
                    goals[tournamentNumber][scoreIterator] = goal1+goal2;
                    scoreIterator++;

                    //Condition to pick which team advances to the next round
                    if (goal1>goal2)
                    {
                        tourney[i+1][count] = tourney[i][match];

                    }
                    else if (goal1<goal2)
                    {
                        tourney[i+1][count] = tourney[i][match+1];

                    }
                    //Prints out what happened in this round
                    System.out.print("["+tourney[i][match]+ " " +goal1+":"+goal2+" "+tourney[i][match+1]+"]");



                    count++;


                }

                System.out.println();
            }


            tournamentNumber++;

            //Checks if the winner of the tournament is the user selected team
            //Breaks out of the loop if it is the case
            System.out.println("The winners of tournament #"+tournamentNumber+" are "+tourney[4][0]);
            if (tourney[4][0].equalsIgnoreCase(userTeam))
                break;

            //Breaks out of loop if reaches 20 tournaments played to prevent error from emptying the arrays
            if (tournamentNumber==20)
                break;

            //Empties all arrays except for the main "Round of 16" on that contains the teams
            for (int i =1; i<5;i++)
            {
                for (int j =0;j<16;j++)
                {
                    tourney[i][j] = null;
                }
            }

        }
        while(tournamentNumber<20);

        //Tells the user if their chosen team won or just kept losing
        if (tourney[4][0].equalsIgnoreCase(userTeam))
            System.out.println("\nCongratulations, your chosen team of \""+userTeam+"\" won in "+tournamentNumber+" tournaments!");
        else
            System.out.println("\nWe are sorry, but your team failed to win in "+tournamentNumber+" tournaments");

        //Closes the Scanner, no longer needed
        input.close();

        //Show the stats for the goals that happened when playing
        System.out.println("Now time to present the stats for goals:");

        //Loops for each tournament that happened
        for (; countScores<tournamentNumber; countScores++)
        {
            System.out.print("[Tournament "+(countScores+1)+"] Total goals: [");
            //Loops for all the matches that happened per tournament
            for (int j =0;j<15;j++)
            {
                if (j==14)
                    System.out.print(goals[countScores][j]);
                else
                {
                    System.out.print(goals[countScores][j]+", ");
                }
                average += goals[countScores][j];
            }
            average /= 16;
            //Since the round method rounds off and "kills" all decimals
            //Multiplying the number by 10 initially saves the first decimal from getting removed
            //Dividing back by 10.0 will make sure its a double and will put the decimal back in its place
            average = Math.round(average *10)/10.0;
            System.out.println("] [ Average: "+average+"]");
            totalAverage += average;
            //Resets average
            average = 0;

        }
        totalAverage /= countScores;
        //Same as with the other averages
        totalAverage = Math.round(totalAverage*10)/10.0;

        System.out.println("Average goals for "+tournamentNumber+" tournament(s): "+totalAverage);
        System.out.print("Total matches in all tournaments over the average goal value: ");

        //Nested loops to check how many matches went over the average goals made
        for(int i=0; i<20; i++)
        {
            for(int j=0; j<15; j++)
            {
                if (goals[i][j]>totalAverage)
                    countOverAverage++;
            }
        }
        System.out.print(countOverAverage );

        //Closing message
        System.out.println("\n\nThe FIFA World Cup is now over.\nThank you for coming to spectate the matches.\nHave a great day!");

    }
}
