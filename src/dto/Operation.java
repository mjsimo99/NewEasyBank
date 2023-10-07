package dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Operation {
    protected String numero;
    protected LocalDate dateCreation;
    protected Double montant;





    @Override
    public String toString() {
        return "Operation{" +
                "numero='" + numero + '\'' +
                ", dateCreation=" + dateCreation +
                ", montant=" + montant +

                '}';
    }
}
