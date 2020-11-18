package cinema;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Cinema {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //Asking about a hall size
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsInRow = scanner.nextInt();

        int totalSeats = rows * seatsInRow;
        int selectedOption = 1;
        boolean wrongSeat = false;
        int moneyCounter = 0;
        int totalIncome = 10 * seatsInRow * (rows / 2) + 8 * seatsInRow * (rows - (rows / 2));

        //Creating a scheme and assign it to char array
        char[][] scheme = makeScheme(rows, seatsInRow);

        while (selectedOption != 0) {
            //Printing menu
            System.out.println(
                "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");

            //Processing answer
            selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case 1:
                    //Printing out the scheme
                    printScheme(scheme);
                    break;
                case 2:
                    //Asking about coordinates of the seat
                    do {
                        System.out.println("Enter a row number:");
                        int rowNumber = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int seatNumber = scanner.nextInt();

                        //Assigning the seat to char array
                        if (rowNumber > rows || seatNumber > seatsInRow) {
                            System.out.println("Wrong input!");
                            wrongSeat = true;
                        } else if (scheme[rowNumber][seatNumber] == 'B') {
                            System.out.println("That ticket has already been purchased!");
                            wrongSeat = true;
                        } else {
                            scheme[rowNumber][seatNumber] = 'B';
                            wrongSeat = false;
                            moneyCounter += priceForTicket(rows, rowNumber, totalSeats);
                            System.out.println("Ticket price: $" + priceForTicket(rows, rowNumber, totalSeats));
                        }

                    } while (wrongSeat);
                    break;

                case 3:
                    //Printing statistics
                    System.out.println("Number of purchased tickets: " + purchasedTickets(scheme));
                    System.out.println("Percentage: " + String.format("%.2f", percentageTickets(scheme, totalSeats)) + "%");
                    System.out.println("Current income: $" + moneyCounter);
                    System.out.println("Total income: $" + totalIncome);
            }
        }
    }

    public static double percentageTickets(char[][] scheme, int totalSeats) {
        double percentage = (double) purchasedTickets(scheme) / totalSeats * 100;

            DecimalFormat df = new DecimalFormat("0.00");
            percentage = Double.parseDouble(df.format(percentage));

            return percentage;
    }

    public static int purchasedTickets(char[][] scheme) {
        int counter = 0;
        for (int i = 0; i < scheme.length; i++) {
            for (int j = 0; j < scheme[0].length; j++) {
                if (scheme[i][j] == 'B') {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static char[][] makeScheme(int rows, int seatsInRow) {

        char[][] cinemaHall = new char[rows + 1][seatsInRow + 1];
        int rowsCounter = 49;
        int seatsCounter = 49;
        cinemaHall[0][0] = ' ';

        for (int i = 1; i < rows + 1; i++) {
            cinemaHall[i][0] = (char) rowsCounter;
            rowsCounter++;
            for (int j = 1; j < seatsInRow + 1; j++) {
                cinemaHall[i][j] = 'S';
            }
        }

        for (int j = 1; j < seatsInRow + 1; j++) {
            cinemaHall[0][j] = (char) seatsCounter;
            seatsCounter++;
        }

        return cinemaHall;
    }

    public static void printScheme(char[][] cinemaHall) {

        System.out.println("Cinema:");
        for (int i = 0; i < cinemaHall.length; i++) {
            for (int j = 0; j < cinemaHall[0].length; j++) {
                System.out.print(cinemaHall[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int priceForTicket(int rows, int rowNumber, int totalSeats) {
        if (totalSeats >= 60 && rowNumber > rows / 2) {
            return 8;
        } else {
            return 10;
        }
    }
}
