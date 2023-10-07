package dto;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeAgency {
    private Agence agence;
    private Employe employe;

    private LocalDate datedebut;
    private LocalDate datefin;
}
