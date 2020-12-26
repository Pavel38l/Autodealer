package ru.vsu.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.data.entity.Car;
import ru.vsu.data.entity.CarType;
import ru.vsu.data.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class SoldCarDAO<T> implements DAO<Car> {
    private ConnectionBuilder connectionBuilder;
    private DAO<Customer> customerDAO;
    private static final String SELECT_ALL = "select id, car_type, brand, model, customer_id from sold_car";
    private static final String SELECT_ID = "select id, car_type,  brand, model, customer_id from sold_car where id=?";
    private static final String INSERT = "insert into sold_car (car_type, brand, model, customer_id) values(?, ?, ?, ?)";
    private static final String UPDATE = "update sold_car set car_type=?, brand=?, model=?, customer_id=? where id=?";
    private static final String DELETE = "delete from sold_car where id=?";

    @Override
    public Optional<Car> get(int id) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(SELECT_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer customer_id = resultSet.getInt("customer_id");
                Customer customer = null;
                if (customer_id != null) {
                    customer = customerDAO.get(customer_id).orElse(null);
                }
                return Optional.of(Car.builder()
                        .id(resultSet.getInt("id"))
                        .carType(CarType.valueOf(resultSet.getString("car_type").toUpperCase()))
                        .brand(resultSet.getString("brand"))
                        .model(resultSet.getString("model"))
                        .customer(customer)
                        .build());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Car> getAll() {
        final List<Car> result = new ArrayList<>();

        try (Statement stmt = connectionBuilder.getConnection().createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    Integer customer_id = resultSet.getInt("customer_id");
                    Customer customer = null;
                    if (customer_id != null) {
                        customer = customerDAO.get(customer_id).orElse(null);
                    }
                    result.add(
                            Car.builder()
                                    .id(resultSet.getInt("id"))
                                    .carType(CarType.valueOf(resultSet.getString("car_type").toUpperCase()))
                                    .brand(resultSet.getString("brand"))
                                    .model(resultSet.getString("model"))
                                    .customer(customer)
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
    public void create(Car model) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(INSERT)
        ){
            int count = 1;
            preparedStatement.setString(count++, model.getCarType().toString());
            preparedStatement.setString(count++, model.getBrand());
            preparedStatement.setString(count++, model.getModel());
            preparedStatement.setInt(count++, model.getCustomer() == null ? null : model.getCustomer().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Car model) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(UPDATE)) {
            int count = 1;
            preparedStatement.setString(count++, model.getCarType().toString());
            preparedStatement.setString(count++, model.getBrand());
            preparedStatement.setString(count++, model.getModel());
            preparedStatement.setInt(count++, model.getCustomer() == null ? null : model.getCustomer().getId());
            preparedStatement.setInt(count++, model.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Car model) {
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
