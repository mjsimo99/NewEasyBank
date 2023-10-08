package implementation;

import dto.*;
import helper.DatabaseConnection;
import interfeces.IOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OperationSimpleImpl implements IOperation {
    private static final String ADD_OPERATION = "INSERT INTO operationssimple (numero, datecreation, montant, type, employe_matricule, numeroCompte) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SEARCH_BY_NUMBER = "SELECT * FROM operationssimple WHERE numero=?";
    private static final String DELETE_OPERATION = "DELETE FROM operationssimple WHERE numero=?";
    private static final String GET_COMPTE_EPARGNE = "SELECT * FROM ComptesEpargnes WHERE numeroCompte = ?";
    private static final String GET_COMPTE_COURANT = "SELECT * FROM ComptesCourants WHERE numeroCompte = ?";


    @Override
    public Optional<Operation> Add(Operation operation) {
        if (!(operation instanceof OperationSimple operationSimple)) {
            throw new IllegalArgumentException("Operation type not supported.");
        }

        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_OPERATION)) {
            preparedStatement.setString(1, operationSimple.getNumero());
            preparedStatement.setDate(2, java.sql.Date.valueOf(operationSimple.getDateCreation()));
            preparedStatement.setDouble(3, operationSimple.getMontant());
            preparedStatement.setString(4, operationSimple.getType().name());
            preparedStatement.setString(5, operationSimple.getEmploye().getMatricule());
            preparedStatement.setString(6, operationSimple.getCompte().getNumero());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return Optional.of(operationSimple);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Operation> SearchByNumber(String numero) {
        List<Operation> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_NUMBER)) {
            preparedStatement.setString(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Operation operation = new OperationSimple(
                        resultSet.getString("numero"),
                        resultSet.getObject("datecreation", LocalDate.class),
                        resultSet.getDouble("montant"),
                        TypeOperation.valueOf(resultSet.getString("type")),
                        null,
                        null
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OPERATION)) {
            preparedStatement.setString(1, numero);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}