import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Stylist {
  private String name;
  private int id;

  public Stylist(String _name) {
    this.name = _name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String _newName) {
    this.name = _newName;
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE stylists SET name = :name WHERE id=:id")
        .addParameter("name", this.name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO stylists (name) VALUES (:name)", true)
        .addParameter("name", this.name)
        .executeUpdate().getKey();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM stylists WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Client> getClients() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients WHERE stylistId = :id")
        .addParameter("id", this.id)
        .executeAndFetch(Client.class);
    }
  }

  public List<Appointment> getAppointments() {
    List<Client> clients = this.getClients();
    List<Appointment> appointments = new ArrayList<>();
    // Double for loop to populate appointments list
    for (int clientCount = 0; clientCount < clients.size(); clientCount++) {
      List<Appointment> clientAppointments = clients.get(clientCount).getAppointments();
      for(int appointmentCount = 0; appointmentCount < clientAppointments.size(); appointmentCount++) {
        appointments.add(clientAppointments.get(appointmentCount));
      }
    }
    return appointments;
  }

  public static Stylist findById(int _id) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM stylists WHERE id = :id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Stylist.class);
    }
  }

  public static Stylist findByName(String _name) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM stylists WHERE name = :name")
        .addParameter("name", _name)
        .executeAndFetchFirst(Stylist.class);
    }
  }

  public static List<Stylist> getAll() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM stylists")
        .executeAndFetch(Stylist.class);
    }
  }

  @Override
  public boolean equals(Object _testObj) {
    if(!(_testObj instanceof Stylist))
      return false;
    else {
      Stylist stylistCast = (Stylist) _testObj;
      return this.id == stylistCast.getId() && this.name.equals(stylistCast.getName());
    }
  }
}
