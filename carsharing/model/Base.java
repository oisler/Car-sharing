package carsharing.model;

public abstract class Base {

  private int id;
  private String name;

  public Base(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%d. %s", this.getId(), this.getName());
  }

}
