package Services;

import dto.*;
import implementation.OperationSimpleImpl;
import interfeces.ICompte;
import interfeces.IEmploye;
import interfeces.IOperation;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SOperation {

    public static void operationManagement(Scanner scanner, IOperation operationService, ICompte compteService, IEmploye employeService) {


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
                case 1 -> addOperation(scanner, operationService,compteService,employeService);
                case 2 -> searchOperationByNumber(scanner, operationService);
                case 3 -> deleteOperationByNumber(scanner, operationService);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
    private static void addOperation(Scanner scanner, IOperation operationService, ICompte compteService, IEmploye employeService) {
        System.out.println("Enter Operation Details:");

        System.out.print("Operation Number: ");
        String operationNumber = scanner.nextLine();

        System.out.print("Date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        LocalDate dateCreation = LocalDate.parse(dateStr);

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Operation Type (e.g., DEPOSIT, WITHDRAW): ");
        String typeStr = scanner.nextLine();
        TypeOperation type = TypeOperation.valueOf(typeStr);

        System.out.print("Employee Matricule: ");
        String employeeMatricule = scanner.nextLine();

        System.out.print("Compte Numero: ");
        String compteNumero = scanner.nextLine();

        Compte compte = compteService.GetByNumero(compteNumero);
        Employe employe = employeService.getEmployeById(employeeMatricule);



        OperationSimple operationSimple = new OperationSimple(operationNumber, dateCreation, amount, type, employe, compte);

        Optional<Operation> result = operationService.Add(operationSimple);

        if (result.isPresent()) {
            System.out.println("Operation added successfully: " + result.get());
        } else {
            System.out.println("Failed to add operation.");
        }
    }




    private static void searchOperationByNumber(Scanner scanner, IOperation operationService) {
        System.out.print("Enter Operation Number to search: ");
        String operationNumber = scanner.nextLine();

        List<Operation> operations = operationService.SearchByNumber(operationNumber);

<<<<<<< HEAD
        if (operations.isEmpty()) {
            System.out.println("No operations found with the specified number.");
        } else {
            System.out.println("Operations with Number '" + operationNumber + "':");
            for (Operation operation : operations) {
                System.out.println(operation);
            }
=======
        if (!operations.isEmpty()) {
            System.out.println("Operations with Number '" + operationNumber + "':");
            for (Operation operation : operations) {
                if (operation instanceof OperationSimple operationSimple) {
                    System.out.println("Numero: " + operationSimple.getNumero());
                    System.out.println("DateCreation: " + operationSimple.getDateCreation());
                    System.out.println("Montant: " + operationSimple.getMontant());
                    System.out.println("Type: " + operationSimple.getType());
                    System.out.println();
                }
            }
        } else {
            System.out.println("No operations found with the specified number.");
>>>>>>> EAS-3-Admin-Compte-agence
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
