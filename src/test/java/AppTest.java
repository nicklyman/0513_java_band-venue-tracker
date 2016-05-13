import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band and Venue Tracker");
    assertThat(pageSource()).contains("Add a New Band");
  }

  @Test
  public void bandIsCreatedAndDisplayedTest() {
    goTo("http://localhost:4567/");
    fill("#band_input").with("Black Flag");
    submit(".btn");
    assertThat(pageSource()).contains("Black Flag");
  }

  @Test
  public void venueIsCreatedAndDisplayedTest() {
    goTo("http://localhost:4567/");
    fill("#venue_input").with("White Eagle");
    submit(".btn");
    assertThat(pageSource()).contains("White Eagle");
  }

  @Test
  public void bandAndVenueFormIsDisplayed() {
    Band testBand = new Band("Nirvana");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getBandId());
    goTo(url);
    assertThat(pageSource()).contains("Nirvana");
  }

  @Test
  public void venueFormIsDisplayed() {
    Venue testVenue = new Venue("Timberline");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getVenueId());
    goTo(url);
    assertThat(pageSource()).contains("Timberline");
  }

  @Test
  public void venueIsAddedToBandTest() {
    Band testBand = new Band("U2");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getBandId());
    goTo(url);
    fill("#venue_input").with("Edgefield");
    submit("#add_venue");
    assertThat(pageSource()).contains("Edgefield");
  }

  @Test
  public void bandNameIsUpdatedTest() {
    Band testBand = new Band("U2");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getBandId());
    goTo(url);
    fill("#update_band").with("Pearl Jam");
    submit("#update_band_button");
    goTo(url);
    assertThat(pageSource()).contains("Pearl Jam");
  }

  @Test
  public void bandNameIsDeletedTest() {
    Band testBand = new Band("ABBA");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getBandId());
    goTo(url);
    click("a", withText("Warning: This will delete this band!!!"));
    assertThat(pageSource()).doesNotContain("ABBA").contains("Band and Venue Tracker");
  }

  @Test
  public void venueNameIsUpdatedTest() {
    Venue testVenue = new Venue("Coachella");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getVenueId());
    goTo(url);
    fill("#update_venue").with("Dodger Stadium");
    submit("#update_venue_button");
    goTo(url);
    assertThat(pageSource()).contains("Dodger Stadium");
  }

  @Test
  public void venueNameIsDeletedTest() {
    Venue testVenue = new Venue("Edgefield");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getVenueId());
    goTo(url);
    click("a", withText("Warning: This will delete this venue!!!"));
    assertThat(pageSource()).doesNotContain("Edgefield").contains("Band and Venue Tracker");
  }
}
