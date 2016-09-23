import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Appointment {
  private String date;
  private String time;
  private int clientId;
  private int id;


  /// Constructor
  public Appointment(String _date, String _time, int _clientId) {
    this.date = _date;
    this.time = _time;
    this.clientId = _clientId;
  }

  public String getDate() {
    return this.date;
  }

  public void setDate(String _date) {
    this.date = _date;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE appointments SET date=:date WHERE id=:id")
      .addParameter("date", _date)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String _time) {
    this.time = _time;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE appointments SET time=:time WHERE id=:id")
      .addParameter("time", _time)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public int getId() {
    return this.id;
  }

  public int getClientId() {
    return this.clientId;
  }

  public int getStylistId() {
    return Client.findById(this.clientId).getStylistId();
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO appointments (date, time, clientId) VALUES (:date, :time, :clientId)", true)
        .addParameter("date", this.date)
        .addParameter("time", this.time)
        .addParameter("clientId", this.clientId)
        // .addParameter("stylistId", this.stylistId)
        .executeUpdate().getKey();
    }
  }

  @Override
  public boolean equals(Object _testObj) {
    if(!(_testObj instanceof Appointment))
      return false;
    else {
      Appointment appointmentCast = (Appointment) _testObj;
      return (this.id == appointmentCast.getId() && this.clientId == appointmentCast.getClientId());
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM appointments WHERE id = :id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  /////////////////////////////////////////////////////////////
  // Static methods

  public static Appointment findById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM appointments WHERE id = :id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Appointment.class);
    }
  }

  public static List<Appointment> getAll() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM appointments")
        .executeAndFetch(Appointment.class);
    }
  }

  public static boolean conflictExists(Appointment _appointment) {
    List<Appointment> apptList = new ArrayList<>();
    try (Connection con = DB.sql2o.open()) {
       apptList = con.createQuery("SELECT * FROM appointments WHERE date = :date AND time = :time")
        .addParameter("date", _appointment.getDate())
        .addParameter("time", _appointment.getTime())
        .executeAndFetch(Appointment.class);
    }
    for (int i = 0; i < apptList.size(); i++) {
      if (_appointment.getClientId() == apptList.get(i).getClientId() || _appointment.getStylistId() == apptList.get(i).getStylistId())
        return true;
    }
    return false;
  }

}
