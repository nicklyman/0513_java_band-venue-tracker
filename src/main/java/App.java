import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/band_form", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = new Band(request.queryParams("band_input"));
      if ((band.getBandName()).trim().length() != 0) {
        band.save();
      }
      Venue venue = new Venue(request.queryParams("venue_input"));
      if ((venue.getVenueName()).trim().length() != 0) {
        venue.save();
      }
      model.put("bands", Band.all());
      response.redirect("/");
      return null;
    });

    get("/bands/:band_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:band_id/venue_form", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      Venue newVenue = new Venue(request.queryParams("venue_input"));
      if ((newVenue.getVenueName()).trim().length() != 0) {
        newVenue.save();
      }
      band.addVenue(newVenue);
      model.put("band", band);
      response.redirect("/bands/" + band.getBandId());
      return null;
    });
  }
}
