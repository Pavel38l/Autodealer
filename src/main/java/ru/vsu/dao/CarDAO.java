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
@AllArgsConstructor
@NoArgsConstructor
public class CarDAO implements DAO<Car> {
    private ConnectionBuilder connectionBuilder;
    private static final String SELECT_ALL = "select id, car_type, brand, model from car";
    private static final String SELECT_ID = "select id, car_type,  brand, model from car where id=?";
    private static final String INSERT = "insert into car (car_type, brand, model) values(?, ?, ?)";
    private static final String UPDATE = "update car set car_type=?, brand=?, model=? where id=?";
    private static final String DELETE = "delete from car where id=?";

    @Override
    public Optional<Car> get(int id) {
        try (PreparedStatement preparedStatement = connectionBuilder.getConnection().prepareStatement(SELECT_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(Car.builder()
                        .id(resultSet.getInt("id"))
                        .carType(CarType.valueOf(resultSet.getString("car_type").toUpperCase()))
                        .brand(resultSet.getString("brand"))
                        .model(resultSet.getString("model"))
                        .customer(null)
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
                    result.add(
                            Car.builder()
                                    .id(resultSet.getInt("id"))
                                    .carType(CarType.valueOf(resultSet.getString("car_type").toUpperCase()))
                                    .brand(resultSet.getString("brand"))
                                    .model(resultSet.getString("model"))
                                    .customer(null)
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
