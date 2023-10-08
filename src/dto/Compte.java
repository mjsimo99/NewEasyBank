package dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public abstract class Compte {

    protected String numero;
    protected double sold;
    protected LocalDate dateCreation;
    protected EtatCompte etat;
    protected Client client;
    protected Employe employe;
    protected List<Operation> operations;
    protected Agence agence;


    public Compte(String numero, double sold, LocalDate dateCreation, EtatCompte etat, Client client,Employe employe,List<Operation> operations) {
        setNumero(numero);
        setSold(sold);
        setDateCreation(dateCreation);
        setEtat(etat);
        setClient(client);
        setEmploye(employe);
        setOperations(operations);
    }



    @Override
    public String toString() {
        return "Compte{" +
                "numero='" + numero + '\'' +
                ", sold=" + sold +
                ", dateCreation=" + dateCreation +
                ", etat=" + etat +
                ", client=" + client +
                ", employe=" + employe +
                ", operations=" + operations +
                '}';
    }
}
