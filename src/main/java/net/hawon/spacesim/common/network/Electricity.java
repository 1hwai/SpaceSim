package net.hawon.spacesim.common.network;

public class Electricity {
    public double current, voltage, resistance;
    public double power;

    public Electricity() {
        this(0, 0);
    }

    public Electricity(double current, double voltage) {
        this(current, voltage, 0);
    }

    public Electricity(double resistance) {
        this(0, 0, resistance);
    }

    public Electricity(double current, double voltage, double resistance) {
        this.current = current;
        this.voltage = voltage;
        this.resistance = resistance;

    }

    public void voltageDrop(Electricity e) {
        double preVoltage = e.voltage - e.current * e.resistance;
        if (preVoltage <= 0) {
            voltage = 0;
            current = 0;
            return;
        }
        voltage = preVoltage;
        current = e.current;
    }

}
