package interfeces;

import dto.EmployeAgency;

import java.util.List;
import java.util.Optional;

public interface IEmployeAgency {


    Optional<EmployeAgency> Affecter(EmployeAgency employeAgency);

    Optional<EmployeAgency> muter(EmployeAgency employeAgency);
    Optional<List<EmployeAgency>> ShowList();


}
