package View;

import java.util.Scanner;

public class SSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Loan Simulation Calculator");

        System.out.print("Enter Borrowed Capital (Loan Amount): $");
        double borrowedCapital = scanner.nextDouble();

        System.out.print("Enter Number of Monthly Payments: ");
        int numberOfMonthlyPayments = scanner.nextInt();

        double annualInterestRate = 0.12;
        double monthlyInterestRate = annualInterestRate / 12;

        double monthlyPayment = (borrowedCapital * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonthlyPayments));

        System.out.println("\nCalculated Monthly Payment: $" + monthlyPayment);

        scanner.close();
    }
}
