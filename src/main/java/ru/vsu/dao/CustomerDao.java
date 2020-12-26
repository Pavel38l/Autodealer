package ru.vsu.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.data.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@AllArgsConstructor
public class CustomerDao implements DAO<Customer>{
    private ConnectionBuilder connectionBuilder;
    private static final String SELECT_ALL = "select id, fullname, date_of_birth, sex from customer";
    private static final String SELECT_ID = "select id, fullname, date_of_birth, sex from customer where id=?";
    private static final String INSERT = "insert into customer (id, fullname, date_of_birth, sex) values(?, ?, ?, ?)";
    private static final String UPDATE = "update customer set fullname=?, date_of_birth=?, sex=? where id=?";
    private static final String DELETE = "delete from customer where id=?";

    @Override
    public Optional<Customer> get(int id) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(SELECT_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(Customer.builder()
                        .id(resultSet.getInt("id"))
                        .fullName(resultSet.getString("fullname"))
                        .dateOfBirth(resultSet.getDate("date_of_birth"))
                        .sex(resultSet.getString("sex"))
                        .build());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> getAll() {
        final List<Customer> result = new ArrayList<>();

        try (Statement stmt = connectionBuilder.getConnection().createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    result.add(
                            Customer.builder()
                                    .id(resultSet.getInt("id"))
                                    .fullName(resultSet.getString("fullname"))
                                    .dateOfBirth(resultSet.getDate("date_of_birth"))
                                    .sex(resultSet.getString("sex"))
                                    .build()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void create(Customer model) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(INSERT)
        ){
            int count = 1;
            preparedStatement.setInt(count++, model.getId());
            preparedStatement.setString(count++, model.getFullName());
            preparedStatement.setDate(count++, model.getDateOfBirth());
            preparedStatement.setString(count++, model.getSex());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Customer model) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(UPDATE)) {
            int count = 1;
            preparedStatement.setString(count++, model.getFullName());
            preparedStatement.setDate(count++, model.getDateOfBirth());
            preparedStatement.setString(count++, model.getSex());
            preparedStatement.setInt(count++, model.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Customer model) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(
                DELETE)
        ) {
            preparedStatement.setInt(1, model.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + model.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
