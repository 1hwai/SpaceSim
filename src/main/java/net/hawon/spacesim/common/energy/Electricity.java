package net.hawon.spacesim.common.energy;

public class Electricity {

    public double current, voltage;
    public final double VOLTAGE_LN = 220;

    public final double VOLTAGE_LL = 380;
    public double resistance;

    public double power;

    public Electricity() {
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

    public double getCurrent() {
        return current;
    }

    public double getVoltageLN() {
        return VOLTAGE_LN;
    }

    public double getVoltageLL() {
        return VOLTAGE_LL;
    }

    public double getVoltage() {
        return voltage;
    }

    public double getResistance() {
        return resistance;
    }


    public double getPower() {
        return power;
    }


}
