package implementation;

import dto.Agence;
import dto.Employe;
import dto.EmployeAgency;
import helper.DatabaseConnection;
import interfeces.IEmployeAgency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeAgencyImpl implements IEmployeAgency {
    private static final String INSERT_EMPLOYE_AGENCY = "INSERT INTO EmployesAgency (employe_matricule, agence_code, datedebut, datefin) VALUES (?, ?, ?, ?)";

    private static final String TRANSFEREMPLOYE = "UPDATE EmployesAgency SET agence_code = ?, datedebut = ?, datefin = ? WHERE employe_matricule = ? AND agence_code = ?";

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
    public List<EmployeAgency> ShowList() {
        List<EmployeAgency> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        String query = "SELECT ea.datedebut, ea.datefin, e.matricule AS employe_matricule, e.nom AS employe_nom, a.code AS agence_code, a.nom AS agence_nom " +
                "FROM EmployesAgency ea " +
                "INNER JOIN Employes e ON ea.employe_matricule = e.matricule " +
                "INNER JOIN Agences a ON ea.agence_code = a.code";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String employeMatricule = resultSet.getString("employe_matricule");
                String employeNom = resultSet.getString("employe_nom");
                String agenceCode = resultSet.getString("agence_code");
                String agenceNom = resultSet.getString("agence_nom");
                LocalDate datedebut = resultSet.getDate("datedebut").toLocalDate();
                LocalDate datefin = resultSet.getDate("datefin").toLocalDate();

                Employe employe = new Employe(
                        employeNom, // nom
                        "",
                        LocalDate.now(),
                        "",
                        "",
                        employeMatricule,
                        LocalDate.now(),
                        "",
                        null,
                        null,
                        null
                );

                Agence agence = new Agence();
                agence.setCode(agenceCode);
                agence.setNom(agenceNom);

                EmployeAgency employeAgency = new EmployeAgency(agence, employe, datedebut, datefin);
                resultList.add(employeAgency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }



}