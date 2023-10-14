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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgenceImpl implements IAgence {

    private static final String ADDAGENCE = "INSERT INTO Agences (code, nom, adresse, tel) VALUES (?, ?, ?, ?)";
    private static final String SEARCHBYCODE = "SELECT * FROM Agences WHERE code = ?";
    private static final String DELETEBYCODE = "DELETE FROM Agences WHERE code = ?";
    private static final String SEARCHBYADRESS = "SELECT * FROM Agences WHERE adresse = ?";
    private static final String UPDATEBYCODE =  "UPDATE Agences SET nom = ?, adresse = ?, tel = ? WHERE code = ?";

    private static final String SEARCHBYEMPLOYE =  "SELECT a.* " +
                                                    "FROM Agences a " +
                                                    "INNER JOIN employesagency ea ON a.code = ea.agence_code " +
                                                    "WHERE ea.employe_matricule = ?";
    private static final String SHOWLIST =  "SELECT * FROM agences";




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
    public Optional<Agence> SearchByCode(String code) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCHBYCODE)) {
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Agence agence = new Agence();
                agence.setCode(resultSet.getString("code"));
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
        Connection connection = DatabaseConnection.getConn();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATEBYCODE)
        ) {
            preparedStatement.setString(1, agence.getNom());
            preparedStatement.setString(2, agence.getAdresse());
            preparedStatement.setString(3, agence.getTel());
            preparedStatement.setString(4, agence.getCode());

            int rowsUpdated = preparedStatement.executeUpdate();
            return (rowsUpdated > 0) ? Optional.of(agence) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Agence> SearchByEmployee(Employe employe) {
        List<Agence> agences = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();


        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCHBYEMPLOYE)) {
            preparedStatement.setString(1, employe.getMatricule());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String agenceCode = resultSet.getString("code");
                String nom = resultSet.getString("nom");
                String adresse = resultSet.getString("adresse");
                String tel = resultSet.getString("tel");

                Agence agence = new Agence(agenceCode, nom, adresse, tel, null, null, null);
                agences.add(agence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return agences;
    }


    @Override
    public List<Agence> SearchByAdress(String adresse) {
        List<Agence> agences = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();


        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCHBYADRESS)) {
            preparedStatement.setString(1, adresse);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String agenceCode = resultSet.getString("code");
                String nom = resultSet.getString("nom");
                String tel = resultSet.getString("tel");

                Agence agence = new Agence(agenceCode, nom, adresse, tel, null, null, null);
                agences.add(agence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return agences;
    }


    @Override
    public List<Agence> ShowList() {

        Connection connection = DatabaseConnection.getConn();
        List<Agence> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOWLIST)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Agence agence = getAgenceFromResultSet(resultSet);
                resultList.add(agence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private Agence getAgenceFromResultSet(ResultSet resultSet) throws SQLException {
        return new Agence(
                resultSet.getString("code"),
                resultSet.getString("nom"),
                resultSet.getString("adresse"),
                resultSet.getString("tel"),
                null,
                null,
                null
        );
    }



    }
