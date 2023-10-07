package dto;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemendeCredit {
    private String date;
    private LocalDate numero;
    private Double montant;
    private String duree;
    private String remarque;
    private CreditStatus status;
    private Employe employe;
    private Client client;
    private Agence agence;
}
