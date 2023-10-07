package implementation;

import dto.EmployeAgency;
import helper.DatabaseConnection;
import interfeces.IEmployeAgency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeAgencyImpl implements IEmployeAgency {
    private static final String INSERT_EMPLOYE_AGENCY = "INSERT INTO EmployesAgency (employe_matricule, agence_code, datedebut, datefin) VALUES (?, ?, ?, ?)";
    @Override
    public Optional<EmployeAgency> Affecter(EmployeAgency employeAgency) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYE_AGENCY)) {
            preparedStatement.setString(1, employeAgency.getEmploye().getMatricule());
            preparedStatement.setString(2, employeAgency.getAgence().getCode());
            preparedStatement.setDate(3, java.sql.Date.valueOf(employeAgency.getDatedebut()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(employeAgency.getDatefin()));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return Optional.of(employeAgency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<EmployeAgency> muter(EmployeAgency employeAgency) {
        return Optional.empty();
    }

    @Override
    public Optional<List<EmployeAgency>> ShowList() {
        return Optional.empty();
    }
}