import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Appointment {
  private String date;
  private String time;
  private int clientId;
  private int stylistId;
  private int id;


  /// Constructor
  public Appointment(String _date, String _time, int _clientId) {
    this.date = _date;
    this.time = _time;
    this.clientId = _clientId;
  }

  /////////////////////////////////////////////////////////////////////////////
  // Local methods

  public String getDate() {
    return this.date;
  }

  public void setDate(String _date) {
    this.date = _date;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String _time) {
    this.time = _time;
  }

  public int getId() {
    return this.id;
  }

  public int getClientId() {
    return this.clientId;
  }

  public int getStylistId() {
    return this.stylistId;
  }

  /////////////////////////////////////////////////////////////////////////////
  // Local database methods

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO appointments (date, time, clientId, stylistId) VALUES (:date, :time, :clientId, :stylistId)", true)
        .addParameter("date", this.date)
        .addParameter("time", this.time)
        .addParameter("clientId", this.clientId)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate().getKey();
    }
  }

  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients WHERE id = :id")
        .addParameter("id", this.clientId)
        .executeAndFetch(Client.class);
    }
  }

  public List<Stylist> getStylists() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM stylists WHERE id = :id")
        .addParameter("id", this.stylistId)
        .executeAndFetch(Stylist.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM appointments WHERE id = :id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  // Static methods

  public static Appointment findById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM appointments WHERE id = :id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Appointment.class);
    }
  }

}
