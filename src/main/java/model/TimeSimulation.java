package model;

import java.time.Clock;
import java.time.Duration;

public class TimeSimulation {
    private static Clock clock = Clock.systemDefaultZone();

    public static Clock getClock() {
        return clock;
    }

    public static void advanceTime(Duration duration) {
        clock = Clock.offset(clock, duration);
    }
}