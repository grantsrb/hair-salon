import org.junit.rules.ExternalResource;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class AppointmentTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void testOfGetandSetMethods() {
    Appointment testAppointment = new Appointment("11/11", "11:11", 1);
    testAppointment.save();
    assertEquals("getDate test", "11/11", Appointment.findById(testAppointment.getId()).getDate());
    assertEquals("getTime test", "11:11", Appointment.findById(testAppointment.getId()).getTime());
    testAppointment.setDate("12/12");
    testAppointment.setTime("12:12");
    assertEquals("setDate test", "12/12", Appointment.findById(testAppointment.getId()).getDate());
    assertEquals("setTime test", "12:12", Appointment.findById(testAppointment.getId()).getTime());
    assertEquals("getClientId test", 1, Appointment.findById(testAppointment.getId()).getClientId());
  }

  @Test
  public void delete_deletesInformationWithinDatabase_void() {
    Appointment testAppointment = new Appointment("11/11", "11:11", 1);
    testAppointment.save();
    testAppointment.delete();
    assertEquals("delete test", null, Appointment.findById(testAppointment.getId()));
  }

  @Test
  public void getStylistId_returnsStylistIdOfInternalClient_int() {
    Stylist testStylist = new Stylist("Roppy Bap");
    testStylist.save();
    Client testClient = new Client("Hubert Graxvest", testStylist.getId());
    testClient.save();
    Appointment testAppointment = new Appointment("11/11", "11:11", testClient.getId());
    testAppointment.save();
    assertEquals(testStylist.getId(), testAppointment.getStylistId());
  }
}
