package implementation;

import dto.*;
import helper.DatabaseConnection;
import interfeces.IVerement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//temps also
public class VirementImpl  implements IVerement {
    private static final String ADD_VIREMENT = "INSERT INTO verements (numero, dateCreation, montant, expediteur, Beneficiaire) VALUES (?, ?, ?, ?, ?)";
    ;
    private static final String DELETE_VIREMENT = "DELETE FROM verements WHERE numero = ?";
    private static final String SEARCH_BY_NUMBER = "SELECT * FROM verements WHERE numero=?";
    private static final String SEARCH_BY_CREATION_DATE = "SELECT * FROM verements WHERE dateCreation = ?"
    ;

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

    public List<Operation> SearchByNumber(String numero) {
        List<Operation> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_NUMBER)) {
            preparedStatement.setString(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String expediteurNumero = resultSet.getString("expediteur");
                String beneficiaireNumero = resultSet.getString("Beneficiaire");

                Compte expediteur = new CompteCourant(expediteurNumero, 0.0, null, null, null, null, null, 0.0);
                Compte beneficiaire = new CompteCourant(beneficiaireNumero, 0.0, null, null, null, null, null, 0.0);

                Operation operation = new Virement(
                        resultSet.getString("numero"),
                        resultSet.getObject("dateCreation", LocalDate.class),
                        resultSet.getDouble("montant"),
                        expediteur,
                        beneficiaire
                );
                resultList.add(operation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
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

    @Override
    public List<Operation> SearchByCreationDate(LocalDate creationDate) {
        List<Operation> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_CREATION_DATE)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(creationDate));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String expediteurNumero = resultSet.getString("expediteur");
                String beneficiaireNumero = resultSet.getString("Beneficiaire");

                Compte expediteur = new CompteCourant(expediteurNumero, 0.0, null, null, null, null, null, 0.0);
                Compte beneficiaire = new CompteCourant(beneficiaireNumero, 0.0, null, null, null, null, null, 0.0);

                Operation operation = new Virement(
                        resultSet.getString("numero"),
                        resultSet.getObject("dateCreation", LocalDate.class),
                        resultSet.getDouble("montant"),
                        expediteur,
                        beneficiaire
                );
                resultList.add(operation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }


}
