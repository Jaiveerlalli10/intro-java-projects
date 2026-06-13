// Jaiveer Lalli
// 1-22-2026
// In this class we produce a stock simulator. Users are able to buy, and sell
// various stocks given to through files. We're able to go through files and
// identify which stocks the user would like to buy or sell. The user is also
// given the options to save their portfolio and display all stocks available to purchase



import java.util.*;
import java.io.*;

public class Stonks {

    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner console = new Scanner(System.in);
        System.out.print("Enter stocks file name: ");
        String fileName = console.nextLine();
        System.out.println();

        Scanner fileScanner = new Scanner(new File(fileName));
        String firstLine = fileScanner.nextLine();
        Scanner lineScan = new Scanner(firstLine);

        int numStocks = 0;
        while (lineScan.hasNext()) {
            if (lineScan.hasNextInt()) {
                numStocks = lineScan.nextInt();
            } else {
                lineScan.next();
            }
        }

        String[] stocks = new String[numStocks];
        double[] prices = new double[numStocks];
        double[] portfolio = new double[numStocks];


        stockData(fileScanner, stocks, prices, portfolio);
        
        introMessage(stocks, prices, numStocks);
        
        System.out.println();

        String choice = "";

        while (!choice.equalsIgnoreCase("Q")) {
            System.out.println("Menu: (B)uy, (Se)ll, (D)isplay, (S)ave, (Q)uit");
            System.out.print("Enter your choice: ");
            choice = console.nextLine();
            
            
            if (choice.equalsIgnoreCase("B")) {
                buyStock(console, stocks, prices, portfolio, numStocks);
            }


            else if (choice.equalsIgnoreCase("Se")) {
                sellStock(console, stocks, portfolio, numStocks);
            }

            else if (choice.equalsIgnoreCase("D")) {
                display(stocks, portfolio, numStocks);
            }

            else if (choice.equalsIgnoreCase("S")) {
                savePortfolio(console, stocks, portfolio, numStocks);
            }
            else if (choice.equalsIgnoreCase("q")) {
                System.out.println();
                }
            
            else {
                System.out.println("Invalid choice: " + choice);
                System.out.print("Please try again");
                System.out.println();
            }
        }

        finalMessage(numStocks, prices, portfolio);

        

    }
   

    // We use the parameters: stocks, which represents the stock tickers. prices
    // which represent the prices of the stocks, and numStocks being the total number
    // of stocks. In the method we produce a welcome message which prints out the
    // total number of stocks available for purchase
    public static void introMessage(String[] stocks, double[] prices, int numStocks) {
        System.out.println("Welcome to the CSE 122 Stocks Simulator!");
        System.out.println("There are " + numStocks + " stocks on the market:");
        for (int i = 0; i < numStocks; i++) {
            if (stocks[i] != null) {
                System.out.println(stocks[i] + ": " + prices[i]);
            }
        }
    }


    // This method's purpose is to fill in the stocks arrays with information. We use 
    // the parameters fileScanner which scans over a specific file of stock information
    // array of stock names and their prices and their total portfolio of stocks the user has.
    public static void stockData(Scanner fileScanner, String[] stocks, 
                                double[] prices, double[] portfolio) {
        int index = 0;

                                    
        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Scanner lineScan = new Scanner(line);

            if (lineScan.hasNext()) {
                String name = lineScan.next();
                if (lineScan.hasNextDouble()) {
                    double price = lineScan.nextDouble();
                    stocks[index] = name;
                    prices[index] = price;
                    portfolio[index] = 0;
                    index++;
                }
            }
        }
    } 
    

    // In this method we take  parameters that represent the user input, console, stock names,
    // their prices, the user's portfolio, and the total number of stocks in numStocks.
    // This method goes over if the user wants to buy a stock, they are able to select which
    // stock they want to buy and how many shares they would like to purchase. 
    // The minimum budget is $5.
    public static void buyStock(Scanner console, String[] stocks, double[] prices, 
                            double[] portfolio, int numStocks) {
        System.out.print("Enter the stock ticker: ");
        String stockBought = console.nextLine();
        int index = indexOf(stocks, stockBought, numStocks);
        if (index == -1) {
            System.out.print("Stock not found.");
        } else {
            System.out.print("Enter your budget: ");
            double budget = console.nextDouble();                    
            console.nextLine();
            if (budget >= 5) {
                double stockPrice = priceFind(stocks, stockBought, prices, numStocks);
                double shares = budget / stockPrice;
                portfolio[index] += shares;
                System.out.println("You successfully bought " + stockBought + ".");
            } else {
                System.out.print("Budget must be at least $5");
                System.out.println();

            }
        }
    }

    // In this method we take  parameters that represent the user input, console, stock names,
    // the user's portfolio, and the total number of stocks in numStocks.
    // In this method we go over selling a stock. The user is asked which stock they would
    // like to sell and how many shares they would like to sell. The user can ONLY sell stock
    // if they have less than or equal amount to the amount they are trying to sell.
    public static void sellStock(Scanner console, String[] stocks,
                                    double[] portfolio, int numStocks) {
        System.out.print("Enter the stock ticker: ");
        String stockSold = console.nextLine();
        int index = indexOf(stocks, stockSold, numStocks);
        if (index == -1) {
            System.out.println("Stock not found.");
        } else {
            System.out.print("Enter the number of shares to sell: ");
            double stockAmountSold = console.nextDouble();
            console.nextLine();
            if (stockAmountSold <= portfolio[index]) {
                if (portfolio[index] >= stockAmountSold) {
                    System.out.println("You successfully sold " + stockAmountSold + 
                            " shares of " + stockSold + ".");
                    portfolio[index] -= stockAmountSold;
                } 
            } else {
                System.out.println("You do not have enough shares of " + stockSold +
                            " to sell " + stockAmountSold + " shares.");
            }
        }
    }

    // In this method, we are displaying all of the stocks in the user's portfolio.
    // We take in the parameters of stock names through stocks, the users portfolio, 
    // and the total number of stocks
    public static void display(String[] stocks, double[] portfolio, int numStocks) {
        System.out.println();
        System.out.println("Portfolio:");
        for (int i = 0 ; i < numStocks; i++) {
            if (portfolio[i] > 0) {
                System.out.println(stocks[i] + " " + portfolio[i]);
            }
        }
        System.out.println();
    }

    // In this method we take  parameters that represent the user input, console, stock names,
    // their prices, the user's portfolio, and the total number of stocks in numStocks.
    // We ask the user for the new save file name for the portfolio and then create a new
    // file for it. We then loop through the user's portfolio and add in the data of their 
    // portfolio to a new file
    public static void savePortfolio(Scanner console, String[] stocks, 
            double[] portfolio, int numStocks) throws FileNotFoundException {
        System.out.print("Enter new portfolio file name: ");
        String fileName = console.nextLine();
        File fileSave = new File(fileName);
        PrintStream output = new PrintStream(fileSave);
        for (int i = 0; i < numStocks; i++) {
            if (portfolio[i] > 0) {
                output.println(stocks[i] + " " + portfolio[i]);
            }
        }
        output.close();
    }



    // In this method we take in the array stock which represents the stock tickers
    // stockBought is the stock the user wants to interact with, price is the stocks price
    // and numStocks is the total number of stocks. In this method we find the price
    // of each stock to correspond with it's ticker. We then return the stock price
    // into the array, or -1 if the stock is not found
    public static double priceFind(String[] stock, String stockBought,
                                    double[] price, int numStocks) {
        
        int index = indexOf(stock, stockBought, numStocks);
        if (index == -1) {
            System.out.println("Stock not in dataset.");
            return -1;
        }
        return price[index];
    }


    // In this method we take in the array stock which represents the stock tickers
    // stockBough is the stock the user wants to interact with, price is the stocks price
    // and numStocks is the total number of stocks. In this method we find the index of 
    // each stock to match it up with its information and price. We then return the index 
    // of the stock.
    public static int indexOf(String[] stocks, String stockBought, int numStocks) {
        int index = -1;
        for (int i = 0 ; i < numStocks; i++) {
            if (stocks[i].equalsIgnoreCase(stockBought)) {
                return i;
            }
    
        }
        return index;
    }


    // In this method we take numStock which is the total number of stocks, the prices of stocks
    // and the user's portfolio. We print out the final message when the user exits the program
    // which represents the user's final portfolio value.
    public static void finalMessage(int numStocks, double[] prices, double[] portfolio) {
        double totalValue = 0;
        for (int i = 0; i < numStocks; i++) {
            totalValue += portfolio[i] * prices[i];
        }
        System.out.println("Your portfolio is currently valued at: $" + totalValue);
    }
}
