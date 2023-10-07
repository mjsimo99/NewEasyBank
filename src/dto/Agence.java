package dto;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agence {
    private String code;
    private String nom;
    private String adresse;
    private String tel;
    private List<DemendeCredit> demendeCredits;
    private List<Compte> comptes;
    private List<EmployeAgency> employeAgencies;

    public Agence(String code, String nom, String adresse, String tel) {
        this.code = code;
        this.nom = nom;
        this.adresse = adresse;
        this.tel = tel;
    }
}