package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor


public final class OperationSimple extends Operation {
    TypeOperation type;
    private Employe employe;
    private Compte compte;


    public <T> OperationSimple(String numero, T datecreation, double montant, TypeOperation type, Object o, Object o1) {
    }
}
