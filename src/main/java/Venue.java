import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Venue {
  private int id;
  private String venue_name;

  public Venue(String name) {
    this.venue_name = name;
  }

  public String getVenueName() {
    return venue_name;
  }
}
