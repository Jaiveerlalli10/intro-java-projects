// Jaiveer Lalli
// 10/20/25
// CSE 121
// TA: Nhan Truong
// P1: Election Simulator
// This class goes through an election of multiple simulations of different
// districts and keeps track of the turnout, votes, and voting percentage
// to provide data and a winner of the election

import java.util.*;

public class ElectionSimulator {
    // class constant variables
    public static final String PURPLE = "🟪";
    public static final String YELLOW = "🟡";
    public static final int NUM_SIMS = 5;
    public static final int NUM_DISTS = 10;
    public static final double PURPLE_POLL_AVG = 0.51;
    public static final double YELLOW_POLL_AVG = 1.0 - PURPLE_POLL_AVG;
    public static final double PURPLE_POLL_ERR = 0.05;
    public static final double YELLOW_POLL_ERR = -1.0 * PURPLE_POLL_ERR;
    
    public static void main(String[] args) {
        System.out.println("Welcome to the Election Simulator!");
        System.out.println("Running " + NUM_SIMS + " simulations of " + 
                NUM_DISTS + " districts.");
        System.out.println();
        System.out.println("The Purple Party is polling at " + 
                (100 * PURPLE_POLL_AVG) + "%");
        System.out.println("The Yellow Party is polling at " + 
                (100 * YELLOW_POLL_AVG) + "%");
        votes();
        System.out.println();
    }
    // This method does all of the work throughout the program by storing values, 
    // using for loops, and printing what we need for the election simulator to work
    public static void votes() {
        // variables that are used throughout the program
        Random randy = new Random();
        int turnout = 0;
        int purpleVotes = 0;
        int yellowVotes = 0;
        double purpleVotePercent = 0.0;
        double yellowVotePercent = 0.0;
        int totTurnout = 0;
        int currPurpleVotes = 0;
        int currYellowVotes = 0;
        double purplePercentFinal = 0.0;
        double purplePercentFinalRound = 0.0;
        double yellowPercentFinal = 0.0;
        double yellowPercentFinalRound = 0.0;

        System.out.println();

        for (int i = 1; i <= NUM_SIMS; i++) {
            // this loop goes through the different districts and keeps track of votes
            // for purple and yellow
            System.out.println("Running Simulation " + i + ":");
            for (int j = 1; j <= NUM_DISTS; j++) {
                turnout = randy.nextInt(1000) + 1;
                double districtError = randy.nextGaussian() * 0.5;
                purpleVotePercent = districtError * PURPLE_POLL_ERR + PURPLE_POLL_AVG;
                yellowVotePercent = districtError * YELLOW_POLL_ERR + YELLOW_POLL_AVG;
                        
                purpleVotes = (int) (Math.round(purpleVotePercent * turnout));
                currPurpleVotes += purpleVotes;
                yellowVotes = (int) (Math.round(yellowVotePercent * turnout));
                currYellowVotes += yellowVotes;
                        
                totTurnout += purpleVotes + yellowVotes;
                        
                System.out.println("  District #" + j + " - " + PURPLE + " " +
                        purpleVotes + "  " + YELLOW + " " + yellowVotes);
            }
            System.out.println();
                
            double currentPurpleVotePercent = (100.0 * currPurpleVotes / totTurnout);
            purplePercentFinal += currentPurpleVotePercent;
            double currPurpleVotePercentRound = 
                    Math.round(currentPurpleVotePercent * 100) / 100.0;
                    
            double currentYellowVotePercent = (100.0 * currYellowVotes / totTurnout);
            yellowPercentFinal += currentYellowVotePercent;
            double currYellowVotePercentRound = 
                    Math.round(currentYellowVotePercent * 100) / 100.0;

            // Provides the user with the data at the end of the current simulation

            System.out.println("Results for Simulation " + i + ":");
            System.out.println("  Total Turnout: " + totTurnout);
            System.out.println("  Purple Party's votes: " + currPurpleVotes + 
                    " (" + currPurpleVotePercentRound + "%)");
            System.out.println("  Yellow Party's votes: " + currYellowVotes + 
                    " (" + currYellowVotePercentRound + "%)");
            System.out.print("  Visualization: ");
                
            // The following two for loops print out the visualization tab of the assignment
            for (int purple = 1; purple <= currPurpleVotes / 100; purple++) {
                System.out.print(PURPLE);
            }
            System.out.println();
            System.out.print("                 ");

            for (int yellow = 1; yellow <= currYellowVotes / 100; yellow++) {
                System.out.print(YELLOW);
            }
            System.out.println();
            System.out.println();
            System.out.println();
                    
            totTurnout = 0;
            currPurpleVotes = 0;
            currYellowVotes = 0;
        }
        
        purplePercentFinal = purplePercentFinal / NUM_SIMS;
        purplePercentFinalRound = Math.round(purplePercentFinal * 100) / 100.0;

        yellowPercentFinal = yellowPercentFinal / NUM_SIMS;
        yellowPercentFinalRound = Math.round(yellowPercentFinal * 100) / 100.0;
        
        // Provides the final election results as well as the winner

        System.out.println("Election Simulator Results:");
        if (purplePercentFinalRound > yellowPercentFinalRound) {
            System.out.println(PURPLE + " Win = true (" + purplePercentFinalRound + "%)");
            System.out.print(YELLOW + " Win = false (" + yellowPercentFinalRound + "%)");
        }
        else if (purplePercentFinalRound == yellowPercentFinalRound) {
            System.out.println(PURPLE + " Win = true (" + purplePercentFinalRound + "%)");
            System.out.print(YELLOW + " Win = true (" + yellowPercentFinalRound + "%)");
        } else {
            System.out.println(PURPLE + " Win = false (" + purplePercentFinalRound + "%)");
            System.out.print(YELLOW + " Win = true (" + yellowPercentFinalRound + "%)");
        }
    }
}
