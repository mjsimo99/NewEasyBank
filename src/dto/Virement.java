package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Virement extends Operation{
    private Compte comptesource;
    private Compte comptedestination;

    public Virement(String numero, LocalDate dateCreation, Double montant, Compte comptesource, Compte comptedestination) {
        super(numero, dateCreation, montant);
        this.comptesource = comptesource;
        this.comptedestination = comptedestination;
    }



    @Override
    public String toString() {
        return "Virement{" +
                "comptesource=" + comptesource +
                ", comptedestination=" + comptedestination +
                "} " + super.toString();
    }
}
