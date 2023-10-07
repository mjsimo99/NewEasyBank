package Services;

import dto.Agence;

import interfeces.IAgence;

import java.util.Optional;
import java.util.Scanner;


public class SAgence {



    public static void agenceManagement(Scanner scanner, IAgence agenceService) {
        while (true) {
            System.out.println("Affectation Management Menu:");
            System.out.println("1. Create New Affectation");
            System.out.println("2. Delete Affectation");
            System.out.println("3. Show All Affectations");
            System.out.println("4. Show Assignment History by Matricule");
            System.out.println("5. Back to Main Menu");

            System.out.print("Enter your choice (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addAgence(scanner,agenceService);

                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    public static void addAgence(Scanner scanner, IAgence agenceService) {

        System.out.println("Add New Agence");
        System.out.print("Enter Code: ");
        String code = scanner.nextLine();

        System.out.print("Enter Nom: ");
        String nom = scanner.nextLine();

        System.out.print("Enter Adresse: ");
        String adresse = scanner.nextLine();

        System.out.print("Enter Tel: ");
        String tel = scanner.nextLine();

        Agence newAgence = new Agence(code, nom, adresse, tel);

        Optional<Agence> addedAgence = agenceService.Add(newAgence);

        if (addedAgence.isPresent()) {
            System.out.println("Agence added successfully!");
        } else {
            System.out.println("Failed to add Agence.");
        }


    }


}
