import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_instantiatesCorrectly_true() {
    Venue myVenue = new Venue("Edgefield");
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
  public void getVenueName_venueInstantiatesWithVenueName_String() {
    Venue myVenue = new Venue("Edgefield");
    assertEquals("Edgefield", myVenue.getVenueName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void equals_returnsTrueIfVenueNamesAretheSame_true() {
    Venue firstVenue = new Venue("Edgefield");
    Venue secondVenue = new Venue("Edgefield");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(myVenue.getVenueId(), savedVenue.getVenueId());
  }

  @Test
  public void find_findVenueInDatabase_true() {
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getVenueId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void update_updateVenueInDatabase() {
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    myVenue.update("Portland Zoo");
    assertEquals("Portland Zoo", Venue.find(myVenue.getVenueId()).getVenueName());
  }

  @Test
  public void addBand_addsBandToVenue() {
    Band myBand = new Band("U2");
    myBand.save();
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    myVenue.addBand(myBand);
    Band savedBand = myVenue.getBands().get(0);
    assertTrue(myBand.equals(savedBand));
  }

@Test
  public void getBands_returnsAllBands_List() {
    Band myBand = new Band("U2");
    myBand.save();
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    myVenue.addBand(myBand);
    List savedBands = myVenue.getBands();
    assertEquals(1, savedBands.size());
  }

  @Test
  public void delete_deletesAllVenuesAndBandsAssociations() {
    Band myBand = new Band("U2");
    myBand.save();
    Venue myVenue = new Venue("Edgefield");
    myVenue.save();
    myVenue.addBand(myBand);
    myVenue.delete();
    assertEquals(0, myBand.getVenues().size());
    assertEquals(0, Venue.all().size());
  }
}
