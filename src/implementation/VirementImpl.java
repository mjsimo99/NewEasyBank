package implementation;

import dto.Operation;
import interfeces.IVerement;

import java.util.List;
import java.util.Optional;

public class VirementImpl  implements IVerement {
    @Override
    public Optional<Operation> Add(Operation operation) {
        return Optional.empty();
    }

    @Override
    public List<Operation> SearchByNumber(String numero) {
        return null;
    }

    @Override
    public boolean Delete(String numero) {
        return false;
    }
}
