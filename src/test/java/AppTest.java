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
    fill("#band_input").with("U2");
    submit(".btn");
    assertThat(pageSource()).contains("U2");
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
    assertThat(pageSource()).doesNotContain("ABBA");
  }
}
