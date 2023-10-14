package implementation;

import dto.*;
import helper.DatabaseConnection;
import interfeces.IDemendeCredit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DemendeCreditImpl implements IDemendeCredit {
    private static final String CREATE_DEMANDE = "INSERT INTO DemendeCredits " +
            "(numero, date, montant, duree, remarque, status, agence_code, employe_matricule, client_code) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SEARCH_BY_DATE = "SELECT * FROM DemendeCredits WHERE date = ?";
    String SEARCH_BY_CODE = "SELECT * FROM DemendeCredits WHERE numero = ?";
    String updateStatusQuery = "UPDATE DemendeCredits SET status = ? WHERE numero = ?";

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
        List<DemendeCredit> creditRequests = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        String query = "SELECT * FROM DemendeCredits";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DemendeCredit creditRequest = new DemendeCredit();
                creditRequest.setNumero(resultSet.getString("numero"));
                creditRequest.setDate(resultSet.getDate("date").toLocalDate());
                creditRequest.setMontant(resultSet.getDouble("montant"));
                creditRequest.setDuree(resultSet.getString("duree"));
                creditRequest.setRemarque(resultSet.getString("remarque"));
                creditRequest.setStatus(CreditStatus.valueOf(resultSet.getString("status")));
                creditRequest.setAgence(new Agence(resultSet.getString("agence_code"), null, null, null, null, null, null));
                creditRequest.setEmploye(new Employe(null, null, null, null, null, resultSet.getString("employe_matricule"), null, null, null, null, null));
                creditRequest.setClient(new Client(resultSet.getString("client_code"), null, null, null, null, null, null));
                creditRequests.add(creditRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creditRequests;
    }

    @Override
    public List<DemendeCredit> SearchByStatus(CreditStatus status) {
        return null;
    }

    @Override
    public List<DemendeCredit> SearchBydate(LocalDate date) {
        List<DemendeCredit> creditRequests = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();


        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_DATE)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DemendeCredit creditRequest = new DemendeCredit();
                creditRequest.setNumero(resultSet.getString("numero"));
                creditRequest.setDate(resultSet.getDate("date").toLocalDate());
                creditRequest.setMontant(resultSet.getDouble("montant"));
                creditRequest.setDuree(resultSet.getString("duree"));
                creditRequest.setRemarque(resultSet.getString("remarque"));

                creditRequest.setStatus(CreditStatus.valueOf(resultSet.getString("status")));
                creditRequest.setAgence(new Agence(resultSet.getString("agence_code"), null, null, null, null,null,null));
                creditRequest.setEmploye(new Employe(null, null, null, null, null, resultSet.getString("employe_matricule"), null, null, null, null, null));
                creditRequest.setClient(new Client(resultSet.getString("client_code"), null, null, null, null, null, null));

                creditRequests.add(creditRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creditRequests;
    }



    @Override
    public List<DemendeCredit> SearchByCode(String numero) {
        List<DemendeCredit> creditRequests = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_CODE)) {
            preparedStatement.setString(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DemendeCredit creditRequest = new DemendeCredit();
                creditRequest.setNumero(resultSet.getString("numero"));
                creditRequest.setDate(resultSet.getDate("date").toLocalDate());
                creditRequest.setMontant(resultSet.getDouble("montant"));
                creditRequest.setDuree(resultSet.getString("duree"));
                creditRequest.setRemarque(resultSet.getString("remarque"));

                creditRequest.setStatus(CreditStatus.valueOf(resultSet.getString("status")));
                creditRequest.setAgence(new Agence(resultSet.getString("agence_code"), null, null, null, null, null, null));
                creditRequest.setEmploye(new Employe(null, null, null, null, null, resultSet.getString("employe_matricule"), null, null, null, null, null));
                creditRequest.setClient(new Client(resultSet.getString("client_code"), null, null, null, null, null, null));

                creditRequests.add(creditRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creditRequests;
    }

    @Override
    public Optional<DemendeCredit> UpdateStatus(DemendeCredit demendeCredit) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatusQuery)) {
            preparedStatement.setString(1, demendeCredit.getStatus().toString());
            preparedStatement.setString(2, demendeCredit.getNumero());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return Optional.of(demendeCredit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }



}
