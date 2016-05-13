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
}
