package interfeces;

import dto.Operation;
import dto.TypeOperation;

import java.util.List;

public interface IOperationSimple extends IOperation{
    List<Operation> SearchByType(TypeOperation type);

}
