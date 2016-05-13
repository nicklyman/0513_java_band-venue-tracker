import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    Band myBand = new Band("U2");
    assertEquals(true, myBand instanceof Band);
  }

  @Test
  public void getBandName_bandInstantiatesWithBandName_String() {
    Band myBand = new Band("U2");
    assertEquals("U2", myBand.getBandName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Band.all().size());
  }

  @Test
  public void equals_returnsTrueIfBandNamesAretheSame_true() {
    Band firstBand = new Band("U2");
    Band secondBand = new Band("U2");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Band myBand = new Band("U2");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Band myBand = new Band("U2");
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getBandId(), savedBand.getBandId());
  }

  @Test
  public void find_findBandInDatabase_true() {
    Band myBand = new Band("U2");
    myBand.save();
    Band savedBand = Band.find(myBand.getBandId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void update_updateBandInDatabase() {
    Band myBand = new Band("U2");
    myBand.save();
    myBand.update("Nirvana");
    assertEquals("Nirvana", Band.find(myBand.getBandId()).getBandName());
  }

  @Test
  public void addVenue_addsVenueToBand_true() {
    Band myBand = new Band("U2");
    myBand.save();
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    myBand.addVenue(myVenue);
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void getVenues_returnsAllVenues_List() {
    Band myBand = new Band("U2");
    myBand.save();
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    myBand.addVenue(myVenue);
    List savedVenues = myBand.getVenues();
    assertEquals(1, savedVenues.size());
  }
}
