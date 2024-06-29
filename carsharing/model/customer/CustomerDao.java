package carsharing.model.customer;

import java.util.List;

public interface CustomerDao {

  void add(String name);

  List<Customer> findAll();

  Customer findById(int id);

  void update(Customer customer);

}
