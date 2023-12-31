package interfeces;

import dto.*;

import java.util.List;
import java.util.Optional;

public interface IAgence {
    Optional<Agence> Add(Agence agence);
    Optional<Agence> SearchByCode(String code);
    boolean Delete(String code);
    Optional<Agence> Update(Agence agence);
    List<Agence> SearchByEmployee(Employe employe);
    List<Agence> SearchByAdress(String adresse);
    List<Agence> ShowList();


}
