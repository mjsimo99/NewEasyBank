package interfeces;

import dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICompte {
    Optional<Compte> Add(Compte compte);
    List<Compte> SearchByClient(Client client);
    boolean Delete(String numero);
    Optional<Compte> UpdateStatus(Compte compte);
    List<Compte> FilterByStatus(EtatCompte etat);
    List<Compte> ShowList();
    List<Compte> FilterByDCreation(LocalDate dateCreation);
    Optional<Compte> Update(Compte compte);
    List<Compte> SearchByOperation(Operation operation);
    Optional<Compte> AffectCompteToAgance(Compte compte, Agence agence);
    Compte GetByNumero(String numero);




}
//gerniriques