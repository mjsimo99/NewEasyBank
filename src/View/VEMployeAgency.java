package View;

import dto.Agence;
import dto.Employe;
import dto.EmployeAgency;
import interfeces.IAgence;
import interfeces.IEmploye;
import interfeces.IEmployeAgency;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class VEMployeAgency {
    public static void employeAgencyManagement(Scanner scanner, IEmployeAgency employeAgencyService,IEmploye employeService,IAgence agenceService) {
        while (true) {
            System.out.println("EmployeAgency Management Menu:");
            System.out.println("1. Affecter EmployeAgency");
            System.out.println("2. Muter EmployeAgency");
            System.out.println("3. Show List of EmployeAgency");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> affecterEmployeAgency(scanner, employeAgencyService,employeService,agenceService);
                case 2 -> muterEmployeAgency(scanner, employeAgencyService,employeService,agenceService);

                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }


    public static void affecterEmployeAgency(Scanner scanner, IEmployeAgency employeAgencyService, IEmploye employeService, IAgence agenceService) {
        System.out.println("Affecter Employe to Agency");

        System.out.print("Enter employee matricule: ");
        String matricule = scanner.nextLine();

        System.out.print("Enter agency code: ");
        String agencyCode = scanner.nextLine();

        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr);

        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine();
        LocalDate endDate = LocalDate.parse(endDateStr);

        Optional<Agence> agenceOptional = agenceService.SearchByCode(agencyCode);

        if (agenceOptional.isPresent()) {
            Agence agence = agenceOptional.get();

            Employe employe = employeService.getEmployeById(matricule);

            EmployeAgency employeAgency = new EmployeAgency(agence, employe, startDate, endDate);

            Optional<EmployeAgency> result = employeAgencyService.Affecter(employeAgency);

            if (result.isPresent()) {
                System.out.println("Employee has been added to the agency successfully.");
            } else {
                System.out.println("Failed to add the employee to the agency.");
            }
        } else {
            System.out.println("Agency with code " + agencyCode + " not found.");
        }
    }
    public static void muterEmployeAgency(Scanner scanner, IEmployeAgency employeAgencyService, IEmploye employeService, IAgence agenceService) {
        System.out.println("Muter EmployeAgency");

        System.out.print("Enter employee matricule: ");
        String matricule = scanner.nextLine();

        System.out.print("Enter old agency code: ");
        String oldAgencyCode = scanner.nextLine();

        System.out.print("Enter new agency code: ");
        String newAgencyCode = scanner.nextLine();

        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr);

        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine();
        LocalDate endDate = LocalDate.parse(endDateStr);

        Optional<Agence> oldAgenceOptional = agenceService.SearchByCode(oldAgencyCode);
        Optional<Agence> newAgenceOptional = agenceService.SearchByCode(newAgencyCode);

        if (oldAgenceOptional.isPresent() && newAgenceOptional.isPresent()) {
            Agence oldAgence = oldAgenceOptional.get();
            Agence newAgence = newAgenceOptional.get();

            Employe employe = employeService.getEmployeById(matricule);

            EmployeAgency employeAgency = new EmployeAgency(oldAgence, employe, startDate, endDate);

            Optional<EmployeAgency> result = employeAgencyService.muter(oldAgencyCode, newAgencyCode, employeAgency);

            if (result.isPresent()) {
                System.out.println("Employee has been transferred to the new agency successfully.");
            } else {
                System.out.println("Failed to transfer the employee to the new agency.");
            }
        } else {
            if (oldAgenceOptional.isEmpty()) {
                System.out.println("Old agency with code " + oldAgencyCode + " not found.");
            }
            if (newAgenceOptional.isEmpty()) {
                System.out.println("New agency with code " + newAgencyCode + " not found.");
            }
        }
    }
}

