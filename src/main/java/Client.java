import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Client {
  private String name;
  private int stylistId;
  private int id;

  public Client(String _name, int _stylistId) {
    this.name = _name;
    this.stylistId = _stylistId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String _name) {
    this.name = _name;
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE clients SET name = :name WHERE id=:id")
        .addParameter("name", this.name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public int getStylistId() {
    return this.stylistId;
  }

  public void setStylistId(int _stylistId) {
    this.stylistId = _stylistId;
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE clients SET stylistId = :stylistId WHERE id=:id")
        .addParameter("stylistId", this.stylistId)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO clients (name, stylistId) VALUES (:name, :stylistId)", true)
        .addParameter("name", this.name)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate().getKey();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM clients WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Appointment> getAppointments() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM appointments WHERE clientId = :id")
        .addParameter("id", this.id)
        .executeAndFetch(Appointment.class);
    }
  }

  public static Client findById(int _id) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients WHERE id = :id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Client.class);
    }
  }

  public static Client findByName(String _name) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients WHERE name = :name")
        .addParameter("name", _name)
        .executeAndFetchFirst(Client.class);
    }
  }

  public static List<Client> getAll() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients")
        .executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object _testObj) {
    if(!(_testObj instanceof Client))
      return false;
    else {
      Client clientCast = (Client) _testObj;
      return (this.id == clientCast.getId() && this.stylistId == clientCast.getStylistId());
    }
  }
}
