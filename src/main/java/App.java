import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";
    staticFileLocation("/public");

    get("/", (request, response) -> {
      return new ModelAndView(homepageModel(), layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylistId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      model.put("stylist", Stylist.findById(stylistId));
      model.put("template", "templates/stylist-display.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylistId/clients/:clientId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int clientId = Integer.parseInt(request.params(":clientId"));
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      model.put("stylist", Stylist.findById(stylistId));
      model.put("client", Client.findById(clientId));
      model.put("template", "templates/stylist-display.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylistId/clients/:clientId/appointments/:appointmentId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int appointmentId = Integer.parseInt(request.params(":appointmentId"));
      int clientId = Integer.parseInt(request.params(":clientId"));
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      model.put("stylist", Stylist.findById(stylistId));
      model.put("client", Client.findById(clientId));
      model.put("appointment", Appointment.findById(appointmentId));
      model.put("template", "templates/stylist-display.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sign-in",(request,response) -> {
      Map<String,Object> model = new HashMap<>();
      return new ModelAndView(model, "templates/sign-in.vtl");
    },new VelocityTemplateEngine());

    post("/sign-in", (request,response) -> { // Directs to home page if successful login
      String userName = request.queryParams("user-name");
      String password = request.queryParams("password");
      if (User.validUser(userName,password)) {
        User.setLogInStatus(true);
        User.setLoggedInUser(userName);
        return new ModelAndView(homepageModel(), layout);
      } else {
        Map<String,Object> model = new HashMap<>();
        model.put("invalidLogin", true);
        return new ModelAndView(model, "templates/sign-in.vtl");
      }
    },new VelocityTemplateEngine());

    post("/new-account", (request,response) -> {
      String userName = request.queryParams("create-user-name");
      String password = request.queryParams("create-password");
      if (!User.userAlreadyExists(userName)) { // If user name is valid, directs to home page
        User.setLogInStatus(true);
        User newUser = new User(userName, password);
        newUser.save();
        User.setLoggedInUser(userName);
        return new ModelAndView(homepageModel(), layout);
      } else {
        Map<String,Object> model = new HashMap<>();
        model.put("invalidUserName", true);
        return new ModelAndView(model, "templates/sign-in.vtl");
      }
    },new VelocityTemplateEngine());

    post("/sign-out", (request, response) -> {
      User.setLogInStatus(false);
      User.setLoggedInUser(null);
      return new ModelAndView(homepageModel(), layout);
    },new VelocityTemplateEngine());
  }

  //////////////////////////////////////////////////////////

  public static Map<String,Object> homepageModel() {
    Map<String,Object> model = new HashMap<>();
    model.put("stylists", Stylist.getAll());
    model.put("clients", Client.getAll());
    model.put("appointments", Appointment.getAll());
    model.put("loggedInUser", User.getLoggedInUser());
    model.put("signedIn", User.getLogInStatus());
    model.put("template", "templates/index.vtl");
    return model;
  }
}
