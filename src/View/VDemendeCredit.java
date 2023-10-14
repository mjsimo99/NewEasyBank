package View;

import dto.*;
import interfeces.IAgence;
import interfeces.IClient;
import interfeces.IDemendeCredit;
import interfeces.IEmploye;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class VDemendeCredit {
    public static void demendeCreditManagement(Scanner scanner, IDemendeCredit demendeCreditService, IAgence agenceService, IEmploye employeService, IClient clientService) {
        while (true) {
            System.out.println("Demande de Crédit Management Menu:");
            System.out.println("1. Add Demand for Credit");
            System.out.println("2. Search Demand for Credit by Number");
            System.out.println("3. Delete Demand for Credit by Number");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();

            // Consume the newline character
            scanner.nextLine();

            switch (choice) {
                case 1 -> addDemandeCredit(scanner, demendeCreditService, agenceService, employeService, clientService);
                // case 2 -> searchDemandeCreditByNumero(scanner, demendeCreditService);
                // case 3 -> deleteDemandeCreditByNumero(scanner, demendeCreditService);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void addDemandeCredit(Scanner scanner, IDemendeCredit demendeCreditService, IAgence agenceService, IEmploye employeService, IClient clientService) {
        double borrowedCapital = VSimulation.simulate();

        System.out.print("Do you want to confirm and insert this demand for credit? (yes/no): ");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            System.out.println("Enter Demand for Credit Details:");

            System.out.print("Duree (e.g., 12 months): ");
            String duree = scanner.nextLine();

            double montant = borrowedCapital;
            System.out.print("Number: ");
            String numero = scanner.nextLine();

            System.out.print("Remarque: ");
            String remarque = scanner.nextLine();

            System.out.print("Status (EnAttente, Accepte, refuse): ");
            String statusStr = scanner.nextLine();
            CreditStatus status = CreditStatus.valueOf(statusStr);

            System.out.print("Client Code: ");
            String clientCode = scanner.nextLine();
            List<Client> clients = clientService.SearchByCode(clientCode);

            if (!clients.isEmpty()) {
                Client client = clients.get(0);

                System.out.print("Agence Code: ");
                String agenceCode = scanner.nextLine();
                Optional<Agence> agenceOptional = agenceService.SearchByCode(agenceCode);

                if (agenceOptional.isPresent()) {
                    Agence agence = agenceOptional.get();

                    System.out.print("Employee Matricule: ");
                    String employeMatricule = scanner.nextLine();
                    Employe employe = employeService.getEmployeById(employeMatricule);

                    DemendeCredit demendeCredit = new DemendeCredit();
                    demendeCredit.setNumero(numero);
                    demendeCredit.setDuree(duree);
                    demendeCredit.setMontant(montant);
                    demendeCredit.setRemarque(remarque);
                    demendeCredit.setStatus(status);
                    demendeCredit.setClient(client);
                    demendeCredit.setAgence(agence);
                    demendeCredit.setEmploye(employe);

                    Optional<DemendeCredit> addedCredit = demendeCreditService.Add(demendeCredit);

                    if (addedCredit.isPresent()) {
                        System.out.println("Demand for credit inserted successfully.");
                    } else {
                        System.out.println("Failed to insert demand for credit.");
                    }
                } else {
                    System.out.println("Agence with the specified code not found.");
                }
            } else {
                System.out.println("No clients found with the specified Code.");
            }
        } else {
            System.out.println("Demand for credit insertion canceled.");
        }
    }

}
