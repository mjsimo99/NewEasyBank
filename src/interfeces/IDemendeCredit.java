package interfeces;

import dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IDemendeCredit {
    Optional<DemendeCredit> Add(DemendeCredit demendeCredit);

    List<DemendeCredit> searchByagency(Agence agence);
    List<DemendeCredit> ShowList();

    List<DemendeCredit> ListByStatus(CreditStatus status);
    List<DemendeCredit> ListBydate(LocalDate date);

}
