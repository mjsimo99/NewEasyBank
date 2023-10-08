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
    private static final String  TRANSFEREMPLOYE = "UPDATE EmployesAgency SET agence_code = ?, datedebut = ?, datefin = ? WHERE employe_matricule = ? AND agence_code = ?";

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
    public Optional<EmployeAgency> muter(String oldAgencyCode, String newAgencyCode, EmployeAgency employeAgency) {
        Connection connection = DatabaseConnection.getConn();

        try {
            PreparedStatement updateStatement = connection.prepareStatement(TRANSFEREMPLOYE);
            updateStatement.setString(1, newAgencyCode);
            updateStatement.setDate(2, java.sql.Date.valueOf(employeAgency.getDatedebut()));
            updateStatement.setDate(3, java.sql.Date.valueOf(employeAgency.getDatefin()));
            updateStatement.setString(4, employeAgency.getEmploye().getMatricule());
            updateStatement.setString(5, oldAgencyCode);

            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                return Optional.of(employeAgency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }



    @Override
    public Optional<List<EmployeAgency>> ShowList() {
        return Optional.empty();
    }
}