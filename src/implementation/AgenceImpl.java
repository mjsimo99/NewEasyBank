package implementation;

import dto.Agence;
import dto.Compte;
import dto.Employe;
import helper.DatabaseConnection;
import interfeces.IAgence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AgenceImpl implements IAgence {

    private static final String ADDAGENCE = "INSERT INTO Agences (code, nom, adresse, tel) VALUES (?, ?, ?, ?)";
    private static final String SEARCHBYCODE = "SELECT * FROM Agences WHERE code = ?";
    private static final String DELETEBYCODE = "DELETE FROM Agences WHERE code = ?";


    @Override
    public Optional<Agence> Add(Agence agence) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADDAGENCE)) {
            preparedStatement.setString(1, agence.getCode());
            preparedStatement.setString(2, agence.getNom());
            preparedStatement.setString(3, agence.getAdresse());
            preparedStatement.setString(4, agence.getTel());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return Optional.of(agence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();


    }

    @Override
    public Optional<Agence> SearchByCode(Agence agence) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCHBYCODE)) {
            preparedStatement.setString(1, agence.getCode());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                agence.setNom(resultSet.getString("nom"));
                agence.setAdresse(resultSet.getString("adresse"));
                agence.setTel(resultSet.getString("tel"));
                return Optional.of(agence);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean Delete(String code) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETEBYCODE)) {
            preparedStatement.setString(1, code);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Agence> Update(Agence agence) {
        return Optional.empty();
    }

    @Override
    public List<Agence> SearchByEmployee(Employe employe) {
        return null;
    }

    @Override
    public List<Compte> SearchByAdress(String adresse) {
        return null;
    }
}
