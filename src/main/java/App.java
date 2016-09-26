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

    post("/stylists/:stylistId/clients/:clientId/appointments/add", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String date = request.queryParams("date");
      String time = request.queryParams("time");
      int clientId = Integer.parseInt(request.params(":clientId"));
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      Appointment newAppointment = new Appointment(date, time, clientId);
      if (!Appointment.conflictExists(newAppointment)) {
        newAppointment.save();
        model.put("appointmentConflict", true);
      }
      model.put("stylist", Stylist.findById(stylistId));
      model.put("client", Client.findById(clientId));
      model.put("template", "templates/client-display.vtl");
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
      model.put("template", "templates/appointment-display.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/add", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String clientName = request.queryParams("client-name");
      int stylistId = Integer.parseInt(request.params(":id"));
      if (Client.findByName(clientName) == null && !clientName.equals("")) {
        clientName = clientName.toLowerCase();
        Client newClient = new Client(clientName, stylistId);
        newClient.save();
      }
      model.put("stylist", Stylist.findById(stylistId));
      model.put("template", "templates/stylist-display.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylistId/clients/:clientId/edit", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int clientId = Integer.parseInt(request.params(":clientId"));
      model.put("stylists", Stylist.getAll());
      model.put("client", Client.findById(clientId));
      model.put("template", "templates/client-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylistId/clients/:clientId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int clientId = Integer.parseInt(request.params(":clientId"));
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      model.put("stylist", Stylist.findById(stylistId));
      model.put("client", Client.findById(clientId));
      model.put("template", "templates/client-display.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylistId/clients/:clientId", (request, response) -> {
      int newStylistId = Integer.parseInt(request.queryParams("edit-stylist-id"));
      String nameOrDelete = request.queryParams("edit-or-delete");
      nameOrDelete = nameOrDelete.toLowerCase();
      int clientId = Integer.parseInt(request.params(":clientId"));
      Client client = Client.findById(clientId);
      if (nameOrDelete.equals("delete")) {
        client.delete();
        return new ModelAndView(homepageModel(), layout);
      } else {
        client.setStylistId(newStylistId);
        if (!nameOrDelete.equals(""))
          client.setName(nameOrDelete);
        Map<String,Object> model = new HashMap<>();
        model.put("client", client);
        model.put("stylist", Stylist.findById(newStylistId));
        model.put("template", "templates/client-display.vtl");
        return new ModelAndView(model, layout);
      }
    }, new VelocityTemplateEngine());

    get("/stylists/:stylistId/edit", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      model.put("stylist", Stylist.findById(stylistId));
      model.put("template", "templates/stylist-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/update",  (request, response) -> {
      String direction = request.queryParams("hiddenStylistButton");
      String stylistName = request.queryParams("stylist-name");
      stylistName = stylistName.toLowerCase();
      if (direction.equals("Add") && Stylist.findByName(stylistName) == null && !stylistName.equals("")) {
        Stylist newStylist = new Stylist(stylistName);
        newStylist.save();
      } else {
        if(Stylist.findByName(stylistName) != null && !stylistName.equals(""))
          Stylist.findByName(stylistName).delete();
      }
      return new ModelAndView(homepageModel(), layout);
    },new VelocityTemplateEngine());

    get("/stylists/:stylistId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      model.put("stylist", Stylist.findById(stylistId));
      model.put("template", "templates/stylist-display.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylistId", (request, response) -> {
      String nameOrDelete = request.queryParams("edit-or-delete");
      nameOrDelete = nameOrDelete.toLowerCase();
      int stylistId = Integer.parseInt(request.params(":stylistId"));
      Stylist stylist = Stylist.findById(stylistId);
      if (nameOrDelete.equals("delete")) {
        stylist.delete();
        return new ModelAndView(homepageModel(), layout);
      } else {
        Map<String,Object> model = new HashMap<>();
        if (!nameOrDelete.equals(""))
          stylist.setName(nameOrDelete);
        model.put("stylist", stylist);
        model.put("template", "templates/stylist-display.vtl");
        return new ModelAndView(model, layout);
      }
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

    post("/clients/update",  (request, response) -> {
      String direction = request.queryParams("hiddenClientButton");
      String clientName = request.queryParams("client-name");
      String rawStylistId = request.queryParams("stylist-id");
      clientName = clientName.toLowerCase();
      if (direction.equals("Add") && Client.findByName(clientName) == null && !clientName.equals("") && !rawStylistId.equals("")) {
        int stylistId = Integer.parseInt(rawStylistId);
        Client newClient = new Client(clientName, stylistId);
        newClient.save();
      } else {
        if(Client.findByName(clientName) != null && !clientName.equals(""))
          Client.findByName(clientName).delete();
      }
      return new ModelAndView(homepageModel(), layout);
    },new VelocityTemplateEngine());

    post("/appointments/update", (request, response) -> {
      String date = request.queryParams("date");
      String time = request.queryParams("time");
      String rawClientId = request.queryParams("client-id");
      Map<String,Object> model = homepageModel();
      if (!rawClientId.equals("")) {
        int clientId = Integer.parseInt(rawClientId);
        Appointment newAppointment = new Appointment(date, time, clientId);
        if (!Appointment.conflictExists(newAppointment)) {
          newAppointment.save();
          model = homepageModel();
        } else {
          model.put("appointmentConflict", true);
        }
      }
      return new ModelAndView(model, layout);
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
    model.put("appointmentConflict", false);
    model.put("template", "templates/index.vtl");
    return model;
  }
}
