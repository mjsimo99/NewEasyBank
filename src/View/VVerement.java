package View;
import dto.Compte;
import dto.Operation;
import dto.Virement;
import interfeces.ICompte;
import interfeces.IOperation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class VVerement {


    public static void virementManagement(Scanner scanner, IOperation virementService, ICompte compteService) {

         while (true) {
        System.out.println("Virement Management Menu:");
        System.out.println("1. Add Virement");
        System.out.println("2. Search Virement by Number");
        System.out.println("3. Delete Virement by Number");
        System.out.println("4. Back to Main Menu");

        System.out.print("Enter your choice (1-4): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addVirement(scanner, virementService, compteService);
            case 2 -> searchVirement(scanner, virementService);
            case 3 -> deleteVirement(scanner, virementService);

            case 4 -> {
                return;
            }
            default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
        }
    }
}
    private static void addVirement(Scanner scanner, IOperation virementService, ICompte compteService) {
        System.out.println("Adding a Virement:");

        System.out.print("Enter Virement Number: ");
        String virementNumber = scanner.nextLine();

        LocalDate dateCreation = LocalDate.now();

        System.out.print("Enter Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Expediteur Compte Number: ");
        String expediteurCompteNumber = scanner.nextLine();

        System.out.print("Enter Beneficiaire Compte Number: ");
        String beneficiaireCompteNumber = scanner.nextLine();

        Virement virement = new Virement();
        virement.setNumero(virementNumber);
        virement.setDateCreation(dateCreation);
        virement.setMontant(montant);

        Compte expediteurCompte = compteService.GetByNumero(expediteurCompteNumber);
        Compte beneficiaireCompte = compteService.GetByNumero(beneficiaireCompteNumber);

        if (expediteurCompte == null || beneficiaireCompte == null) {
            System.out.println("Invalid Compte Numbers. Virement cannot be added.");
            return;
        }

        virement.setComptesource(expediteurCompte);
        virement.setComptedestination(beneficiaireCompte);

        Optional<Operation> addedVirement = virementService.Add(virement);

        if (addedVirement.isPresent()) {
            System.out.println("Virement added successfully.");
        } else {
            System.out.println("Failed to add the Virement.");
        }
    }
    private static void deleteVirement(Scanner scanner, IOperation virementService) {
        System.out.println("Deleting a Virement:");

        System.out.print("Enter Virement Number to delete: ");
        String virementNumber = scanner.nextLine();

        boolean deleted = virementService.Delete(virementNumber);

        if (deleted) {
            System.out.println("Virement with number " + virementNumber + " has been deleted.");
        } else {
            System.out.println("Failed to delete the Virement with number " + virementNumber);
        }
    }
    private static void searchVirement(Scanner scanner, IOperation virementService) {
        System.out.println("Searching for a Virement:");

        System.out.print("Enter Virement Number to search: ");
        String virementNumber = scanner.nextLine();

        List<Operation> virements = virementService.SearchByNumber(virementNumber);

        if (!virements.isEmpty()) {
            System.out.println("Virements with number " + virementNumber + ":");
            for (Operation virement : virements) {
                if (virement instanceof Virement) {
                    Virement virementObject = (Virement) virement;
                    System.out.println("Virement Number: " + virementObject.getNumero());
                    System.out.println("Montant: " + virementObject.getMontant());
                    System.out.println("Expediteur Compte Number: " + virementObject.getComptesource().getNumero());
                    System.out.println("Beneficiaire Compte Number: " + virementObject.getComptedestination().getNumero());
                    System.out.println();
                }
            }
        } else {
            System.out.println("No virements found with number " + virementNumber);
        }
    }

}
