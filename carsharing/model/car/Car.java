package carsharing.model.car;

import carsharing.model.Base;

public final class Car extends Base {

  private int companyId;
  private boolean isRented;

  public Car(int id, String name, int companyId, boolean isRented) {
    super(id, name);
    this.companyId = companyId;
    this.isRented = isRented;
  }

  public int getCompanyId() {
    return companyId;
  }

  public boolean isRented() {
    return isRented;
  }

  public void setRented(boolean rented) {
    isRented = rented;
  }
}
