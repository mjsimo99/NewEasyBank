package View;

import implementation.*;
import interfeces.*;

import java.util.Scanner;



public class VMenu {
    private static final IEmploye employeService = new EmployeImpl();

    private static final IClient clientService = new ClientImpl();
    private static final IMission missionService = new MissionImpl();
    private static final IAffectation affectationService = new AffectationImpl();
    private static final IOperation operationService = new OperationSimpleImpl();
    private static final ICompte compteCourantService = new CompteCourantImpl();
    private static final ICompte compteEpargneService = new CompteEpargneImpl();
    private static final IAgence agenceService = new AgenceImpl();
    private static final IEmployeAgency employeAgencyService = new EmployeAgencyImpl();
    private static final CompteCourantImpl compteService = new CompteCourantImpl();
    private static final VirementImpl virementService = new VirementImpl();
    private static final DemendeCreditImpl demendeCreditService = new DemendeCreditImpl();



    private static final Scanner scanner = new Scanner(System.in);

    public static void startMenu() {
        while (true) {
            System.out.println("EasyBank Management System Menu:");
            System.out.println("1. Employee Management");
            System.out.println("2. Client Management");
            System.out.println("3. Mission Management");
            System.out.println("4. Affectation Management");
            System.out.println("5. compte Courant Management");
            System.out.println("6. compte Epargne Management");
            System.out.println("7. Operation Management");
            System.out.println("8. Agence Management");
            System.out.println("9. EmployeAgence Management");
            System.out.println("10. Verement Management");
            System.out.println("11. DemendeCredit Management");

            System.out.println("12. Exit");

            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    VEmploye.employeeManagement(scanner, employeService);
                    break;
                case 2:
                    VClient.clientManagement(scanner, clientService);
                    break;
                case 3:
                    VMission.missionManagement(scanner, missionService);
                    break;
                case 4:
                    VAffectation.affectationManagement(scanner, affectationService, employeService, missionService);
                    break;
                case 5:
                    VCOmpteC.compteCourantManagement(scanner, compteCourantService,agenceService);
                    break;
                case 6:
                    VCOmpteE.compteEpargneManagement(scanner, compteEpargneService,agenceService);
                    break;
                case 7:
                    VOperation.operationManagement(scanner, operationService,compteService,employeService);
                    break;
                case 8:
                    VAgence.agenceManagement(scanner, agenceService);
                    break;
                case 9:
                    VEMployeAgency.employeAgencyManagement(scanner, employeAgencyService, employeService, agenceService);
                    break;
                case 10:
                    VVerement.virementManagement(scanner, virementService, compteService);
                    break;
                case 11:

                    VDemendeCredit.demendeCreditManagement(scanner, demendeCreditService, agenceService,employeService,clientService);
                    break;
                case 12:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }
}