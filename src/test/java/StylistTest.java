import org.junit.rules.ExternalResource;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class StylistTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void save_storesInternalInformationInDatabase_void() {
    Stylist testStylist = new Stylist("Jom Terry");
    testStylist.save();
    assertTrue(testStylist.equals(Stylist.findById(testStylist.getId())));
  }

  @Test
  public void getMethods_testOfGetandSetMethods() {
    Stylist testStylist = new Stylist("Jom Terry");
    testStylist.save();
    assertEquals("getName test", "Jom Terry", Stylist.findById(testStylist.getId()).getName());
    testStylist.setName("Pad Britt");
    assertEquals("setName test", "Pad Britt", Stylist.findById(testStylist.getId()).getName());
  }

  @Test
  public void delete_deletesInformationWithinDatabase_void() {
    Stylist testStylist = new Stylist("Jom Terry");
    testStylist.save();
    testStylist.delete();
    assertEquals("delete test", null, Stylist.findById(testStylist.getId()));
  }

  @Test
  public void getClients_returnsListOfAllClients_ArrayList() {
    Stylist testStylist = new Stylist("Jom Terry");
    testStylist.save();
    Client testClient = new Client("Jilly Bill", testStylist.getId());
    Client testClient2 = new Client("Silly Bjill", testStylist.getId());
    testClient.save();
    testClient2.save();
    assertTrue("test 1",testStylist.getClients().get(0).equals(testClient));
    assertTrue("test 2",testStylist.getClients().get(1).equals(testClient2));
  }

  @Test
  public void getAll_returnsListOfAllStylists_ArrayList() {
    Stylist testStylist = new Stylist("Jom Terry");
    testStylist.save();
    Stylist testStylist2 = new Stylist("Kom Blarry");
    testStylist2.save();
    assertEquals(false, Stylist.getAll().get(1).equals(testStylist));
  }

  @Test
  public void findByName_returnFirstStylistWithTheSpecifiedName_Stylist() {
    Stylist testStylist = new Stylist("Jom Terry");
    testStylist.save();
    assertEquals(true, testStylist.equals(Stylist.findByName("Jom Terry")));
    Stylist.findByName("Jom Terry").delete();
    assertEquals("combined delete test", null, Stylist.findByName("Jom Terry"));
  }

}
