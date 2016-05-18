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
      model.put("venues", Venue.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/band_form", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = new Band(request.queryParams("band_input"));
      if ((band.getBandName()).trim().length() != 0) {
        band.save();
      }
      model.put("bands", Band.all());
      response.redirect("/");
      return null;
    });

    post("/venues/venue_form", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = new Venue(request.queryParams("venue_input"));
      if ((venue.getVenueName()).trim().length() != 0) {
        venue.save();
      }
      model.put("venues", Venue.all());
      response.redirect("/");
      return null;
    });

    get("/bands/:band_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      model.put("allVenues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:venue_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      model.put("venue", venue);
      model.put("allBands", Band.all());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:band_id/bandVenue_form", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      String[] checkedVenueId = request.queryParamsValues("venue_input");
      for (String eachVenueId : checkedVenueId) {
        Venue newVenue = Venue.find(Integer.parseInt(eachVenueId));
        band.addVenue(newVenue);
      }
      model.put("band", band);
      response.redirect("/bands/" + band.getBandId());
      return null;
    });

    post("/venues/:venue_id/venueBand_form", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      String[] checkedBandId = request.queryParamsValues("band_input");
      for (String eachBandId : checkedBandId) {
        Band newBand = Band.find(Integer.parseInt(eachBandId));
        venue.addBand(newBand);
      }
      model.put("venue", venue);
      response.redirect("/venues/" + venue.getVenueId());
      return null;
    });

    post("/bands/:band_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      String updateBandName = request.queryParams("update_band");
      band.update(updateBandName);
      model.put("band", band);
      response.redirect("/bands/" + band.getBandId());
      return null;
    });

    get("/band/:band_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      band.delete();
      response.redirect("/");
      return null;
    });

    get("/bands/:band_id/venues/:venue_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      model.put("venue", venue);
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:venue_id/bands/:band_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      // new
      model.put("venue", venue);
      // new
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/:venue_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      String updateVenueName = request.queryParams("update_venue");
      venue.update(updateVenueName);
      model.put("venue", venue);
      response.redirect("/venues/" + venue.getVenueId());
      return null;
    });

    get("/venue/:venue_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      venue.delete();
      response.redirect("/");
      return null;
    });
  }
}
