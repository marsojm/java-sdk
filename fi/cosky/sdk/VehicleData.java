package fi.cosky.sdk;
import java.util.ArrayList;

/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

public class VehicleData extends BaseData {
	public static final String MimeType = "application/vnd.jyu.nfleet.vehicle+json";
    public static final double MimeVersion = 2.1;
	
    private int Id;
    private String Name;
    private ArrayList<CapacityData> Capacities;
    private LocationData StartLocation;
    private LocationData EndLocation;
    private ArrayList<TimeWindowData> TimeWindows;
    private RouteData Route;
    private int VersionNumber;
    private String VehicleType;
    private String SpeedProfile;
    private double SpeedFactor;
    private String CanBeRelocated;
    
    private double FixedCost;
    private double KilometerCost;
    private double HourCost;

    public RouteData getRoute() {
        return Route;
    }

    public void setRoute(RouteData route) {
        Route = route;
    }

    public VehicleData(String name, ArrayList<CapacityData> capacities, LocationData startLoc, LocationData endLoc){
        this.Capacities = capacities;
        this.EndLocation = endLoc;
        this.StartLocation = startLoc;
        this.Name = name;
        this.CanBeRelocated = "None";
    }

    public int getId() {
        return this.Id;
    }

    public void setCapacities(ArrayList<CapacityData> capa) {
        this.Capacities = capa;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setTimeWindows(ArrayList<TimeWindowData> timeWindows) {
        this.TimeWindows = timeWindows;
    }


    public LocationData getEndLocation() {
        return EndLocation;
    }

    public void setEndLocation(LocationData endLocation) {
        EndLocation = endLocation;
    }

    public LocationData getStartLocation() {
        return StartLocation;
    }

    public void setStartLocation(LocationData startLocation) {
        StartLocation = startLocation;
    }

	int getVersionNumber() {
		return VersionNumber;
	}

	void setVersionNumber(int versionNumber) {
		VersionNumber = versionNumber;
	}
	
	public String getName() {
		return Name;
	}

	public ArrayList<CapacityData> getCapacities() {
		return Capacities;
	}

	public String getVehicleType() {
		return VehicleType;
	}

	public void setVehicleType(String vehicleType) {
		VehicleType = vehicleType;
	}

	public String getSpeedProfile() {
		return SpeedProfile;
	}

	public void setSpeedProfile(String vehicleSpeedProfile) {
		SpeedProfile = vehicleSpeedProfile;
	}

	public double getSpeedFactor() {
		return SpeedFactor;
	}

	public void setSpeedFactor(double vehicleSpeedFactor) {
		SpeedFactor = vehicleSpeedFactor;
	}

	public String getCanBeRelocated() {
		return CanBeRelocated;
	}

	public void setCanBeRelocated(String canBeRelocated) {
		CanBeRelocated = canBeRelocated;
	}

	public ArrayList<TimeWindowData> getTimeWindows() {
		return TimeWindows;
	}

	public double getFixedCost() {
		return FixedCost;
	}

	public void setFixedCost(double fixedCost) {
		FixedCost = fixedCost;
	}

	public double getKilometerCost() {
		return KilometerCost;
	}

	public void setKilometerCost(double kilometerCost) {
		KilometerCost = kilometerCost;
	}

	public double getHourCost() {
		return HourCost;
	}

	public void setHourCost(double hourCost) {
		HourCost = hourCost;
	}

	public VehicleUpdateRequest toRequest() {
		VehicleUpdateRequest request = new VehicleUpdateRequest(Name, Capacities, StartLocation, EndLocation);
		request.setVersionNumber(VersionNumber);
		request.setId(Id);
		request.setName(Name);
		request.setTimeWindows(TimeWindows);
		request.setRoute(Route);
		request.setVehicleSpeedFactor(SpeedFactor);
		request.setVehicleSpeedProfile(SpeedProfile);
		request.setCanBeRelocated(CanBeRelocated);
		return request;
	}
}
