package Services;

import dto.Agence;

import interfeces.IAgence;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class SAgence {



    public static void agenceManagement(Scanner scanner, IAgence agenceService) {
        while (true) {
            System.out.println("Affectation Management Menu:");
            System.out.println("1. Create New Agence");
            System.out.println("2. Search Agence By Code");
            System.out.println("3. Delete Agence");
            System.out.println("4. Search Agence By Address");


            System.out.println("5. Back to Main Menu");

            System.out.print("Enter your choice (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addAgence(scanner, agenceService);
                case 2 -> searchAgenceByCode(scanner, agenceService);
                case 3 -> deleteAgence(scanner, agenceService);
                case 4 -> searchByAddress(scanner, agenceService);

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

        Agence newAgence = new Agence(code, nom, adresse, tel,null,null,null);

        Optional<Agence> addedAgence = agenceService.Add(newAgence);

        if (addedAgence.isPresent()) {
            System.out.println("Agence added successfully!");
        } else {
            System.out.println("Failed to add Agence.");
        }


    }
    public static void searchAgenceByCode(Scanner scanner, IAgence agenceService) {
        System.out.println("Search Agence By Code");
        System.out.print("Enter Code: ");
        String code = scanner.nextLine();

        Agence searchAgence = new Agence(code, null, null, null, null, null, null);

        Optional<Agence> foundAgence = agenceService.SearchByCode(searchAgence);

        if (foundAgence.isPresent()) {
            System.out.println("Agence found:");
            System.out.println(foundAgence.get());
        } else {
            System.out.println("No Agence found with the provided code.");
        }
    }

    public static void deleteAgence(Scanner scanner, IAgence agenceService) {
        System.out.println("Delete Agence");
        System.out.print("Enter Code of the Agence to delete: ");
        String codeToDelete = scanner.nextLine();

        boolean isDeleted = agenceService.Delete(codeToDelete);

        if (isDeleted) {
            System.out.println("Agence with code " + codeToDelete + " deleted successfully!");
        } else {
            System.out.println("Failed to delete Agence with code " + codeToDelete + ".");
        }
    }
    public static void searchByAddress(Scanner scanner, IAgence agenceService) {
        System.out.print("Enter Agence Address: ");
        String address = scanner.nextLine();

        List<Agence> foundAgences = agenceService.SearchByAdress(address);

        if (!foundAgences.isEmpty()) {
            System.out.println("Found Agences:");
            for (Agence agence : foundAgences) {
                System.out.println(agence);
            }
        } else {
            System.out.println("No Agences found with the provided address.");
        }
    }
}
