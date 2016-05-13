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
}
