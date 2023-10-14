package View;

import dto.*;
import interfeces.IAgence;
import interfeces.IClient;
import interfeces.IDemendeCredit;
import interfeces.IEmploye;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class VDemendeCredit {
        public static void demendeCreditManagement(Scanner scanner, IDemendeCredit demendeCreditService, IAgence agenceService, IEmploye employeService, IClient clientService) {
            while (true) {
                System.out.println("Demand de Crédit Management Menu:");
                System.out.println("1. Add Demand for Credit");
                System.out.println("2. Search Demand Credit by Number");
                System.out.println("3. Show All Demand");
                System.out.println("4. Search Demand Credit by Date");
                System.out.println("5. Update Demand Status");
                System.out.println("6. Search Demand Credit by Status");
                System.out.println("7. Back to Main Menu");
                System.out.print("Enter your choice (1-5): ");
                int choice = scanner.nextInt();

                scanner.nextLine();

                switch (choice) {
                    case 1 -> addDemandeCredit(scanner, demendeCreditService, agenceService, employeService, clientService);
                    case 2 -> searchDemandeCreditByCode(scanner, demendeCreditService);
                    case 3 -> showList(demendeCreditService, employeService, clientService);
                    case 4 -> searchDemandeCreditByDate(scanner, demendeCreditService);
                    case 5 -> updateDemandeCreditStatus(scanner, demendeCreditService);
                    case 6 -> searchDemandeCreditByStatus(scanner, demendeCreditService);
                    case 7 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please enter a number between 1 and 5.");
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
                        demendeCredit.setMontant(borrowedCapital);
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

        private static void searchDemandeCreditByDate(Scanner scanner, IDemendeCredit demendeCreditService) {
            System.out.print("Enter the date (YYYY-MM-DD) to search for credit requests: ");
            String dateStr = scanner.nextLine();

            try {
                LocalDate date = LocalDate.parse(dateStr);

                List<DemendeCredit> creditRequests = demendeCreditService.SearchBydate(date);

                if (creditRequests.isEmpty()) {
                    System.out.println("No credit requests found for the specified date.");
                } else {
                    System.out.println("Credit requests for the specified date:");
                    for (DemendeCredit creditRequest : creditRequests) {
                        System.out.println(creditRequest);
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    private static void searchDemandeCreditByCode(Scanner scanner, IDemendeCredit demendeCreditService) {
        System.out.print("Enter the credit request number to search: ");
        String code = scanner.nextLine();

        List<DemendeCredit> creditRequests = demendeCreditService.SearchByCode(code);

        if (creditRequests.isEmpty()) {
            System.out.println("No credit request found for the specified code.");
        } else {
            System.out.println("Credit request for the specified code:");
            for (DemendeCredit creditRequest : creditRequests) {
                System.out.println(creditRequest);
            }
        }
    }

    private static void showList(IDemendeCredit demendeCreditService, IEmploye employeService, IClient clientService) {
        List<DemendeCredit> creditRequests = demendeCreditService.ShowList();

        if (creditRequests.isEmpty()) {
            System.out.println("No credit requests found.");
        } else {
            System.out.println("List of Credit Requests:");
            for (DemendeCredit creditRequest : creditRequests) {
                System.out.println("Credit Request Number: " + creditRequest.getNumero());
                System.out.println("Date: " + creditRequest.getDate());
                System.out.println("Montant: " + creditRequest.getMontant());
                System.out.println("Duree: " + creditRequest.getDuree());
                System.out.println("Remarque: " + creditRequest.getRemarque());
                System.out.println("Status: " + creditRequest.getStatus());

                Employe employe = employeService.getEmployeById(creditRequest.getEmploye().getMatricule());
                Client client = clientService.SearchByCode(creditRequest.getClient().getCode()).get(0);

                System.out.println("Employee Name: " + employe.getNom());
                System.out.println("Client Name: " + client.getNom());
                System.out.println();
            }
        }
    }
    private static void updateDemandeCreditStatus(Scanner scanner, IDemendeCredit demendeCreditService) {
        System.out.print("Enter the demendcredit number to update the status: ");
        String code = scanner.nextLine();

        List<DemendeCredit> creditRequests = demendeCreditService.SearchByCode(code);

        if (creditRequests.isEmpty()) {
            System.out.println("No credit request found for the specified code.");
        } else {
            DemendeCredit creditRequest = creditRequests.get(0);

            System.out.print("Enter the new status (EnAttente, Accepte, Refuse): ");
            String newStatusStr = scanner.nextLine();

            try {
                CreditStatus newStatus = CreditStatus.valueOf(newStatusStr);
                creditRequest.setStatus(newStatus);

                Optional<DemendeCredit> updatedCredit = demendeCreditService.UpdateStatus(creditRequest);

                if (updatedCredit.isPresent()) {
                    System.out.println("Status updated successfully.");
                } else {
                    System.out.println("Failed to update status.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter a valid status.");
            }
        }
    }
    private static void searchDemandeCreditByStatus(Scanner scanner, IDemendeCredit demendeCreditService) {
        System.out.print("Enter the status (EnAttente, Accepte, Refuse) to search for credit requests: ");
        String statusStr = scanner.nextLine();

        try {
            CreditStatus status = CreditStatus.valueOf(statusStr);

            List<DemendeCredit> creditRequests = demendeCreditService.SearchByStatus(status);

            if (creditRequests.isEmpty()) {
                System.out.println("No credit requests found for the specified status.");
            } else {
                System.out.println("Credit requests for the specified status:");
                for (DemendeCredit creditRequest : creditRequests) {
                    System.out.println(creditRequest);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Please enter a valid status.");
        }
    }

}
