package dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Operation {
    protected String numero;
    protected LocalDate dateCreation;
    protected Double montant;
}