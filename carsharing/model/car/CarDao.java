package carsharing.model.car;

import java.util.List;

public interface CarDao {

  List<Car> findAll(int companyId);

  void add(String carName, int companyId);

  Car findById(int id);

  void update(Car car);

}
