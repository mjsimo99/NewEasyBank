package dto;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class OperationSimple extends Operation {
    private TypeOperation type;
    private Employe employe;
    private Compte compte;

    public OperationSimple(String numero, LocalDate dateCreation, Double montant, TypeOperation type, Employe employe, Compte compte) {
        super(numero, dateCreation, montant);
        setType(type);
        setEmploye(employe);
        setCompte(compte);

    }



}
