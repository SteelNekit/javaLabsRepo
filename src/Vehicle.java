import Utils.Utils;

import java.util.Objects;

public final class Vehicle implements Cloneable{
    private String stateNumber;
    private String fabricator;
    private String model;
    private VehicleTypes type;

    public static final Vehicle NO_VEHICLE = new Vehicle(VehicleTypes.NONE);

    public Vehicle(VehicleTypes type) {
        this("","","",type);
    }

    public Vehicle(String stateNumber, String fabricator, String model,VehicleTypes type) {
        this.stateNumber = Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber));
        this.fabricator = Objects.requireNonNull(fabricator);
        this.model = Objects.requireNonNull(model);
        this.type = type;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public String getFabricator() {
        return fabricator;
    }

    public String getModel() {
        return model;
    }

    public VehicleTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        if(getType() == VehicleTypes.NONE) return "NONE";
        final StringBuilder sb = new StringBuilder("<");
        sb.append(fabricator).append("> <");
        sb.append(model).append("> ");
        sb.append("(<").append(type).append(">) ");
        sb.append("Гос. номер - <").append(stateNumber).append('>');
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Vehicle vehicle = (Vehicle) object;
        return stateNumber.equals(vehicle.stateNumber) &&
                fabricator.equals(vehicle.fabricator) &&
                model.equals(vehicle.model) &&
                type == vehicle.type;
    }

    @Override
    public int hashCode() {
        return stateNumber.hashCode() ^
                fabricator.hashCode() ^
                model.hashCode() ^
                type.hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
