package interfeces;

import dto.Operation;
import dto.TypeOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IOperation {
    Optional<Operation> Add(Operation operation);
    List<Operation> SearchByNumber(String numero);
    boolean Delete(String numero);
    List<Operation> SearchByCreationDate(LocalDate creationDate);

}
