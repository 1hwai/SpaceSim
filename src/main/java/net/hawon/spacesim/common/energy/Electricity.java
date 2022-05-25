package net.hawon.spacesim.common.energy;

public class Electricity {

    public double current, voltage;
    public double resistance;
    public double power;

    public Electricity() {
    }

    public Electricity(double current, double voltage) {
        this.current = current;
        this.voltage = voltage;
    }

    public void reset() {
        current = 0;
        voltage = 0;
        power = 0;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getPower() {
        power = current * voltage;
        return power;
    }

    public boolean isVoltageValid() {
        return voltage >= 0;
    }


}
