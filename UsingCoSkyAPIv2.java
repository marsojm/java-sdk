import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fi.cosky.sdk.*;

/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

/**
 * Example program to demonstrate how to use the CO-SKY RESTful API
 * For reporting bugs in the SDK, please contact dev-support (at) co-sky.fi
 */
public class UsingCoSkyAPIv2 {

    @SuppressWarnings("deprecation")
	public static void main (String[] args) {
        API api = new API("");
        boolean success = api.authenticate("", "");
        
        if (success) {
        	try {
        		ApiData data = api.navigate(ApiData.class, api.getRoot());
                System.out.println(data);
                
                ResponseData datas = api.navigate(ResponseData.class, data.getLink("create-user"), new UserData());
                System.out.println(datas);
                UserData user = api.navigate(UserData.class, datas.getLocation());
                
                UserDataSet users = api.navigate(UserDataSet.class, data.getLink("list-users"));
                
                for (UserData u : users.getItems()) {
                	System.out.println(u);
                }
                       
                if (user != null) {
                
                    RoutingProblemDataSet problems = api.navigate(RoutingProblemDataSet.class, user.getLink("list-problems"));
                    	
                    RoutingProblemUpdateRequest newProblem = new RoutingProblemUpdateRequest("exampleProblem");
                    ResponseData result = api.navigate(ResponseData.class, user.getLink("create-problem"), newProblem );
                    System.out.println(result);
                    problems = api.navigate(RoutingProblemDataSet.class, user.getLink("list-problems"));
                    RoutingProblemData problem1 = api.navigate(RoutingProblemData.class, problems.getItems().get(0).getLink("self"));
                    
                    System.out.println(problem1);
                    CoordinateData coordinateData = new CoordinateData();
                    
                    coordinateData.setLatitude(62.244588);
                    coordinateData.setLongitude(25.742683);

                    //Saksa
                    //coordinateData.setLatitude(54.130888);
                    //coordinateData.setLongitude(12.00938);
                    coordinateData.setSystem(CoordinateData.CoordinateSystem.WGS84);
                    LocationData locationData = new LocationData();
                    locationData.setCoordinatesData(coordinateData);

                    CoordinateData pickup = new CoordinateData();


                    pickup.setLatitude(62.247906);
                    pickup.setLongitude(25.867395);

                    //Saksa
                    //pickup.setLatitude(54.14454);
                    //pickup.setLongitude(12.108808);
                    pickup.setSystem(CoordinateData.CoordinateSystem.WGS84);
                    LocationData pickupLocation = new LocationData();
                    pickupLocation.setCoordinatesData(pickup);

                    CoordinateData delivery = new CoordinateData();

                    delivery.setLatitude(61.386909);
                    delivery.setLongitude(24.654106);

                    //Saksa
                    //delivery.setLatitude(53.545867);
                    //delivery.setLongitude(10.276409);
                    delivery.setSystem(CoordinateData.CoordinateSystem.WGS84);
                    LocationData deliveryLocation = new LocationData();
                    deliveryLocation.setCoordinatesData(delivery);

                    ArrayList<CapacityData> capacities = new ArrayList<CapacityData>();
                    capacities.add(new CapacityData("Weight", 100000));
                    ArrayList<TimeWindowData> timeWindows = new ArrayList<TimeWindowData>();
                    Date morning = new Date();
                    morning.setHours(7);
                    Date evening = new Date();
                    evening.setHours(16);
                    timeWindows.add(new TimeWindowData(morning, evening));

                    VehicleUpdateRequest vehicleRequest = new VehicleUpdateRequest("demoVehicle",capacities, locationData, locationData);
                    VehicleUpdateRequest vehicle2 = new VehicleUpdateRequest("demoVehicle2",capacities, locationData, locationData);
                    vehicleRequest.setTimeWindows(timeWindows);
                    vehicle2.setTimeWindows(timeWindows);
                    
                    //New way to add many vehicles at the same time, is WAY faster than doing it one by one.
                    VehicleSetImportRequest vehicles = new VehicleSetImportRequest();
                    ArrayList<VehicleUpdateRequest> set = new ArrayList<VehicleUpdateRequest>();
                    set.add(vehicle2);
                    set.add(vehicleRequest);
                    vehicles.setItems(set);
                    result = api.navigate(ResponseData.class, problem1.getLink("import-vehicles"), vehicles);
                     
                                        
                    ArrayList<CapacityData> taskCapacity = new ArrayList<CapacityData>();
                    taskCapacity.add(new CapacityData("Weight", 1));
                    ArrayList<TaskUpdateRequest> tasks = new ArrayList<TaskUpdateRequest>();
                    for (int i = 0 ; i < 4; i++) {
                    	ArrayList<TaskEventUpdateRequest> taskEvents = new ArrayList<TaskEventUpdateRequest>();
                        taskEvents.add(new TaskEventUpdateRequest(Type.Pickup, pickupLocation, taskCapacity));
                        taskEvents.add(new TaskEventUpdateRequest(Type.Delivery, deliveryLocation, taskCapacity));
                        TaskUpdateRequest task = new TaskUpdateRequest(taskEvents);
                        task.setName("testTask" + i);
                        taskEvents.get(0).setTimeWindows(timeWindows);
                        taskEvents.get(1).setTimeWindows(timeWindows);
                        taskEvents.get(0).setServiceTime(10);
                        taskEvents.get(1).setServiceTime(10);
                        tasks.add(task);
                    }
                    
                    //New way to add many tasks in one request, is WAY faster than doing it one by one.
                    TaskSetImportRequest taskeSetImport = new TaskSetImportRequest();
                    taskeSetImport.setItems(tasks);
                    result = api.navigate(ResponseData.class, problem1.getLink("import-tasks"), taskeSetImport);
                                        
                    ArrayList<TaskEventData> taskEvents = new ArrayList<TaskEventData>();
                    taskEvents.add(new TaskEventData(Type.Pickup, pickupLocation, taskCapacity));
                    taskEvents.add(new TaskEventData(Type.Delivery, deliveryLocation, taskCapacity));
                  
                    problem1 = api.navigate(RoutingProblemData.class, problem1.getLink("self"));
                    System.out.println(problem1);
                    //starting optimization
                    problem1.setState("Running");
                    result = api.navigate(ResponseData.class, problem1.getLink("toggle-optimization"), problem1.toRequest());
                    System.out.println(result);
                    //stopping optimization would be the same but set state to "Stopped"
                    ObjectiveValueDataSet objectiveValues = null;
                    
                    HashMap<String, String> qp = new HashMap<String, String>();
                    qp.put("start", "0");
                    qp.put("end", "10");
                    while (true) {
                        try {
                            Thread.sleep(1500);
                            problem1 = api.navigate(RoutingProblemData.class, problem1.getLink("self"));
                            System.out.println("Optimization is " + problem1.getState() +" at percentage " + problem1.getProgress());
                            /*
                            objectiveValues = api.navigate(ObjectiveValueDataSet.class, problem1.getLink("objective-values"), qp);

                            if (objectiveValues != null && !objectiveValues.getItems().isEmpty()) {
                                for (ObjectiveValueData item : objectiveValues.getItems())    {
                                    System.out.println( "Objective values from " + qp.get("start") + " to " + qp.get("end") + ": [" + item.getTimeStamp() + "] " + item.getValue() );
                                }
                            }
							*/
                            if (problem1.getState().equals("Stopped")) break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    PlanData v = api.navigate(PlanData.class, problem1.getLink("plan"));

                    //Go through the plan.
                    for (FieldsItem el : v.getItems()) {
                    	System.out.println(el);                      
                    }
                }
			
        	} catch (NFleetRequestException e) {
        		System.out.println("Something went wrong");
        	} catch (IOException e) {
        		System.out.println(e);
        	}
            } else {
            System.out.println("Credentials were wrong, or the service is unavailable");
        }

    }


}
