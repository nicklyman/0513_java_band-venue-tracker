import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Band {
  private int id;
  private String band_name;

  public Band(String name) {
    this.band_name = name;
  }

  public String getBandName() {
    return band_name;
  }

  public int getBandId() {
    return id;
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getBandName().equals(newBand.getBandName()) && this.getBandId() == newBand.getBandId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (band_name) VALUES (:band_name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("band_name", this.band_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id = :id;";
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void update(String band_name) {
    if (band_name.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE bands SET band_name = :band_name WHERE id = :id;";
        con.createQuery(sql)
          .addParameter("band_name", band_name)
          .addParameter("id", this.getBandId())
          .executeUpdate();
      }
    }
  }
}
