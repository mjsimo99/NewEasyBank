package implementation;

import dto.Agence;
import dto.Compte;
import dto.Employe;
import interfeces.IAgence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AgenceImpl implements IAgence {
    private final Connection connection;
    public AgenceImpl(Connection connection) {
        this.connection = connection;
    }
    private static final String ADDAGENCE = "INSERT INTO Agences (code, nom, adresse, tel) VALUES (?, ?, ?, ?)";
    @Override
    public Optional<Agence> Add(Agence agence) {

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
    public List<Compte> SearchByCode(String code) {
        return null;
    }

    @Override
    public boolean Delete(String code) {
        return false;
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
