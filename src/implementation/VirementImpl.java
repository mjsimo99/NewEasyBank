package implementation;

import dto.Operation;
import dto.Virement;
import helper.DatabaseConnection;
import interfeces.IVerement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VirementImpl  implements IVerement {
    private static final String ADD_VIREMENT = "INSERT INTO verements (numero, dateCreation, montant, expediteur, Beneficiaire) VALUES (?, ?, ?, ?, ?)";
    ;
    private static final String DELETE_VIREMENT = "INSERT INTO verements (numero, dateCreation, montant, expediteur, Beneficiaire) VALUES (?, ?, ?, ?, ?)";

    @Override
    public Optional<Operation> Add(Operation operation) {
        if (!(operation instanceof Virement virement)) {
            throw new IllegalArgumentException("Operation type not supported.");
        }

        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_VIREMENT)) {
            preparedStatement.setString(1, virement.getNumero());
            preparedStatement.setDate(2, java.sql.Date.valueOf(virement.getDateCreation()));
            preparedStatement.setDouble(3, virement.getMontant());
            preparedStatement.setString(4, virement.getComptesource().getNumero());
            preparedStatement.setString(5, virement.getComptedestination().getNumero());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return Optional.of(virement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Operation> SearchByNumber(String numero) {
        return null;
    }

    @Override
    public boolean Delete(String numero) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VIREMENT)) {
            preparedStatement.setString(1, numero);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
