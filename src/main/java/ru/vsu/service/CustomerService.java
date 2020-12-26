package ru.vsu.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.dao.DAO;
import ru.vsu.data.entity.Car;
import ru.vsu.data.entity.Customer;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomerService {
    private DAO<Customer> customerDAO;

    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }

    public void createCustomer(Customer customer) {
        customerDAO.create(customer);
    }

    public void editCustomer(Customer customer) {
        customerDAO.update(customer);
    }

    public Customer getCustomer(int id) {
        return customerDAO.get(id).orElseThrow();
    }
}
