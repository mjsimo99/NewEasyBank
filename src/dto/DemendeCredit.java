package dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemendeCredit {
    private String numero;
    private LocalDate date;
    private Double montant;
    private String duree;
    private String remarque;
    private CreditStatus status;
    private Employe employe;
    private Client client;
    private Agence agence;



}
