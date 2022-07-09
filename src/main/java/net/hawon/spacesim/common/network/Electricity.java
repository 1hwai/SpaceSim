package net.hawon.spacesim.common.network;

public class Electricity {
    public double current, voltage, resistance;
    public double power;

    public Electricity input, output, regular;

    public Electricity() {
        this(0, 0);
    }

    public Electricity(double current, double voltage) {
        this(current, voltage, 0);
    }

    public Electricity(double current, double voltage, double resistance) {
        this.current = current;
        this.voltage = voltage;
        this.resistance = resistance;
    }
}
