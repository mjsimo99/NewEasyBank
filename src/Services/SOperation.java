package Services;

import dto.Compte;
import dto.Employe;
import dto.Operation;
import dto.TypeOperation;
import implementation.OperationSimple;
import interfeces.IOperation;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SOperation {

    public static void operationManagement(Scanner scanner, IOperation operationService) {


        while (true) {
            System.out.println("Operation Management Menu:");
            System.out.println("1. Add Operation");
            System.out.println("2. Search Operation by Number");
            System.out.println("3. Delete Operation by Number");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addOperation(scanner, operationService);
                case 2 -> searchOperationByNumber(scanner, operationService);
                case 3 -> deleteOperationByNumber(scanner, operationService);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
    private static void addOperation(Scanner scanner, IOperation operationService) {

    }






    private static void searchOperationByNumber(Scanner scanner, IOperation operationService) {
        System.out.print("Enter Operation Number to search: ");
        String operationNumber = scanner.nextLine();

        Optional<Optional<List<Operation>>> operationsOptional = Optional.of(Optional.ofNullable(operationService.SearchByNumber(operationNumber)));

        Optional<List<Operation>> innerOptional = operationsOptional.get();

        if (innerOptional.isPresent()) {
            List<Operation> operations = innerOptional.get();
            if (operations.isEmpty()) {
                System.out.println("No operations found with the specified number.");
            } else {
                System.out.println("Operations with Number '" + operationNumber + "':");
                for (Operation operation : operations) {
                    System.out.println(operation);
                }
            }
        } else {
            System.out.println("Failed to retrieve operations with the specified number.");
        }
    }



    private static void deleteOperationByNumber(Scanner scanner, IOperation operationService) {
        System.out.print("Enter Operation Number to delete: ");
        String operationNumber = scanner.nextLine();

        boolean isDeleted = operationService.Delete(operationNumber);

        if (isDeleted) {
            System.out.println("Operation with Number '" + operationNumber + "' deleted successfully.");
        } else {
            System.out.println("No operation found with the specified number. Deletion failed.");
        }
    }
}
