package implementation;

import dto.Agence;
import dto.CreditStatus;
import dto.DemendeCredit;
import helper.DatabaseConnection;
import interfeces.IDemendeCredit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DemendeCreditImpl implements IDemendeCredit {
    private static final String CREATE_DEMANDE = "INSERT INTO DemendeCredits " +
            "(numero, date, montant, duree, remarque, status, agence_code, employe_matricule, client_code) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    @Override
    public Optional<DemendeCredit> Add(DemendeCredit demendeCredit) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEMANDE)) {
            preparedStatement.setString(1, demendeCredit.getNumero());
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setDouble(3, demendeCredit.getMontant());
            preparedStatement.setString(4, demendeCredit.getDuree());
            preparedStatement.setString(5, demendeCredit.getRemarque());
            preparedStatement.setString(6, demendeCredit.getStatus().toString());
            preparedStatement.setString(7, demendeCredit.getAgence().getCode());
            preparedStatement.setString(8, demendeCredit.getEmploye().getMatricule());
            preparedStatement.setString(9, demendeCredit.getClient().getCode());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return Optional.of(demendeCredit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<DemendeCredit> searchByagency(Agence agence) {
        return null;
    }

    @Override
    public List<DemendeCredit> ShowList() {
        return null;
    }

    @Override
    public List<DemendeCredit> ListByStatus(CreditStatus status) {
        return null;
    }

    @Override
    public List<DemendeCredit> ListBydate(LocalDate date) {
        return null;
    }
}
