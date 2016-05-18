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

  public int getVenueId() {
    return id;
  }

  public static List<Venue> all() {
    String sql = "SELECT * FROM venues;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getVenueName().equals(newVenue.getVenueName()) && this.getVenueId() == newVenue.getVenueId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      // String sql = "INSERT INTO venues (venue_name) VALUES (:venue_name) ON CONFLICT (venue_name) DO NOTHING;";
      String sql = "INSERT INTO venues (venue_name) VALUES (:venue_name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("venue_name", this.venue_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues WHERE id = :id;";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  public void update(String venue_name) {
    if (venue_name.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE venues SET venue_name = :venue_name WHERE id = :id;";
        con.createQuery(sql)
          .addParameter("venue_name", venue_name)
          .addParameter("id", this.getVenueId())
          .executeUpdate();
      }
    }
  }

  public void addBand(Band band) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
    con.createQuery(sql)
      .addParameter("band_id", band.getBandId())
      .addParameter("venue_id", this.getVenueId())
      .executeUpdate();
  }
}

  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT band_id FROM bands_venues WHERE venue_id = :venue_id";
      List<Integer> band_ids = con.createQuery(joinQuery)
        .addParameter("venue_id", this.getVenueId())
        .executeAndFetch(Integer.class);

      List<Band> bands = new ArrayList<Band>();

      for (Integer band_id : band_ids) {
        String venueQuery = "SELECT * FROM bands WHERE id = :band_id";
        Band band = con.createQuery(venueQuery)
          .addParameter("band_id", band_id)
          .executeAndFetchFirst(Band.class);
        bands.add(band);
      }
      return bands;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM venues WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getVenueId())
          .executeUpdate();

      String joinDeleteQueryBandsVenues = "DELETE FROM bands_venues WHERE venue_id = :venue_id";
        con.createQuery(joinDeleteQueryBandsVenues)
          .addParameter("venue_id", this.getVenueId())
          .executeUpdate();
    }
  }
}
