package interfeces;

import dto.EmployeAgency;

import java.util.List;
import java.util.Optional;

public interface IEmployeAgency {


    Optional<EmployeAgency> Affecter(EmployeAgency employeAgency);

    Optional<EmployeAgency> muter(String oldAgencyCode, String newAgencyCode, EmployeAgency employeAgency);
    List<EmployeAgency> ShowList();


}
