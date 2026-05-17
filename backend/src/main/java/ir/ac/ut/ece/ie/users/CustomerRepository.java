package ir.ac.ut.ece.ie.users;

import org.springframework.stereotype.Repository;


import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class CustomerRepository {
    private final Set<Customer> customers = new LinkedHashSet<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Optional<Customer> findByUsername(String username) {
        return customers.stream()
                .filter(customer -> customer.getUsername().equals(username))
                .findFirst();
    }
}
