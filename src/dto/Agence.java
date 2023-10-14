package dto;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Agence {
    private String code;
    private String nom;
    private String adresse;
    private String tel;
    private List<DemendeCredit> demendeCredits;
    private List<Compte> comptes;
    private List<EmployeAgency> employeAgencies;



}