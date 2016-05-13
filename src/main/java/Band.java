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
}
