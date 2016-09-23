import org.junit.rules.ExternalResource;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class ClientTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void save_storesInternalInformationInDatabase_void() {
    Client testClient = new Client("Jom Terry", 1);
    testClient.save();
    assertTrue(testClient.equals(Client.findById(testClient.getId())));
  }

  @Test
  public void getMethods_testOfGetandSetMethods() {
    Client testClient = new Client("Jom Terry", 1);
    testClient.save();
    assertEquals("getName test", "Jom Terry", Client.findById(testClient.getId()).getName());
    testClient.setName("Pad Britt");
    assertEquals("setName test", "Pad Britt", Client.findById(testClient.getId()).getName());
    assertEquals("getStylistId test", 1, Client.findById(testClient.getId()).getStylistId());
  }

  @Test
  public void delete_deletesInformationWithinDatabase_void() {
    Client testClient = new Client("Jom Terry", 1);
    testClient.save();
    testClient.delete();
    assertEquals("delete test", null, Client.findById(testClient.getId()));
  }

  @Test
  public void getAppointments_returnsAllAppointmentsWithClientId_ArrayList() {
    Client testClient = new Client("Jim Bean", 1);
    testClient.save();
    Appointment testAppointment = new Appointment("11/11", "11:11", testClient.getId());
    Appointment testAppointment2 = new Appointment("12/12", "12:12", testClient.getId());
    testAppointment.save();
    testAppointment2.save();
    List<Appointment> appts = testClient.getAppointments();
    assertEquals("by appts list", appts.get(0), testAppointment);
    assertEquals("by getAppointments list", testClient.getAppointments().get(1), testAppointment2);
  }
}
