package carsharing.model.customer;

import carsharing.model.Base;

public final class Customer extends Base {

  private int rentedCarId;

  public Customer(int id, String name, int rentedCarId) {
    super(id, name);
    this.rentedCarId = rentedCarId;
  }

  public int getRentedCarId() {
    return rentedCarId;
  }

  public void setRentedCarId(int rentedCarId) {
    this.rentedCarId = rentedCarId;
  }
}
