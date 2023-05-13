package model;

import java.io.Serializable;
import java.time.Clock;
import java.time.Duration;

public class TimeSimulation implements Serializable {
    private static Clock clock = Clock.systemDefaultZone();

    public static Clock getClock() {
        return clock;
    }

    public static void advanceTime(Duration duration) {
        clock = Clock.offset(clock, duration);
    }

    public static void loadTimeSimulationMemento(Clock clockMemento) {
        clock = clockMemento;
    }
}