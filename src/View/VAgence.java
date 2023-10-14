package View;

import dto.Agence;

import dto.Employe;
import interfeces.IAgence;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class VAgence {


    public static void agenceManagement(Scanner scanner, IAgence agenceService) {
        while (true) {
            System.out.println("Affectation Management Menu:");
            System.out.println("1. Create New Agence");
            System.out.println("2. Search Agence By Code");
            System.out.println("3. Delete Agence");
            System.out.println("4. Search Agence By Address");
            System.out.println("5. Update Agence");
            System.out.println("6. Search Agence By Employee");
            System.out.println("7. Show List of Agences"); // New option to show the list

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addAgence(scanner, agenceService);
                case 2 -> searchAgenceByCode(scanner, agenceService);
                case 3 -> deleteAgence(scanner, agenceService);
                case 4 -> searchByAddress(scanner, agenceService);
                case 5 -> updateAgence(scanner, agenceService);
                case 6 -> searchAgenceByEmployee(scanner, agenceService);
                case 7 -> showAgenceList(agenceService); // Call the showAgenceList method
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 7.");
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

        Agence newAgence = new Agence(code, nom, adresse, tel, null, null, null);

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

        Optional<Agence> foundAgence = agenceService.SearchByCode(code);

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

    public static void updateAgence(Scanner scanner, IAgence agenceService) {
        System.out.println("Update Agence");
        System.out.print("Enter Code of the Agence to update: ");
        String codeToUpdate = scanner.nextLine();

        Optional<Agence> foundAgence = agenceService.SearchByCode(codeToUpdate);

        if (foundAgence.isPresent()) {
            System.out.println("Agence found:");
            System.out.println(foundAgence.get());

            System.out.print("Enter new Nom (press Enter to keep the existing value): ");
            String newNom = scanner.nextLine();
            if (newNom.isEmpty()) {
                newNom = foundAgence.get().getNom();
            }

            System.out.print("Enter new Adresse (press Enter to keep the existing value): ");
            String newAdresse = scanner.nextLine();
            if (newAdresse.isEmpty()) {
                newAdresse = foundAgence.get().getAdresse();
            }

            System.out.print("Enter new Tel (press Enter to keep the existing value): ");
            String newTel = scanner.nextLine();
            if (newTel.isEmpty()) {
                newTel = foundAgence.get().getTel();
            }

            Agence updatedAgence = new Agence(codeToUpdate, newNom, newAdresse, newTel, null, null, null);

            Optional<Agence> updated = agenceService.Update(updatedAgence);

            if (updated.isPresent()) {
                System.out.println("Agence updated successfully!");
            } else {
                System.out.println("Failed to update Agence.");
            }
        } else {
            System.out.println("No Agence found with the provided code.");
        }
    }
    public static void searchAgenceByEmployee(Scanner scanner, IAgence agenceService) {
        System.out.println("Search Agence By Employee");
        System.out.print("Enter Matricule of the Employee: ");
        String matricule = scanner.nextLine();

        List<Agence> foundAgences = agenceService.SearchByEmployee(new Employe(null, null, null, null, null, matricule, null, null, null, null, null));

        if (!foundAgences.isEmpty()) {
            System.out.println("Agences associated with the Employee (Matricule: " + matricule + "):");
            for (Agence agence : foundAgences) {
                System.out.println(agence);
            }
        } else {
            System.out.println("No Agences found associated with the provided Employee Matricule.");
        }
    }
    public static void showAgenceList(IAgence agenceService) {
        List<Agence> agenceList = agenceService.ShowList();

        if (!agenceList.isEmpty()) {
            System.out.println("List of Agences:");
            for (Agence agence : agenceList) {
                System.out.println(agence);
            }
        } else {
            System.out.println("No Agences found.");
        }
    }
}