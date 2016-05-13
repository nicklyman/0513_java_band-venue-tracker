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
}
