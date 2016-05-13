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
  }
}
