/*
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package diablo2015;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.HashSet;

//IMPORTANT! This 'imports' the constants from the PhysicalConstants class into
//the current namespace. This means you can write LIFT_LEFT_MAX instead of
//PhysicalConstants.LIFT_LEFT_MAX. That is where the variables not defined in
//this file are coming from.
import static diablo2015.PhysicalConstants.*;

/**
 * Our 2015 Recycle Rush Robot.
 *
 * This class represents our Robot and all of the physical mechanisms that go on
 * it. This class extends SampleRobot, provided by FIRST. There should
 * reasonably only be one instance of this class per session.
 *
 * @author Erich Maas
 */
public class Robot extends RobotBase {

    /**
     * The amount of time, in milliseconds, between each successive periodic
     * 'tick'. A 'tick' is a call of the tick() method of every {@link Tickable}
     * object in the set maintained internally by the Robot class.
     * <p>
     * This constant allows us to change the resolution of the periodic updates
     * provided to various classes. A finer resolution, i.e., a lower
     * TICK_PERIOD, is more resource intensive, but is more responsive.
     * </p>
     */
    public static final long TICK_PERIOD = 20;

    /**
     * This field represents the drive-train of the Robot, and controls its main
     * wheels. It is initialized during {@link robotMain} with all of the speed
     * controllers that control the wheels of the drive-train.
     */
    RobotDrive robotDrive;
    /**
     * The dual-stick controller which we use to drive our robot. As of 2015,
     * this controller is the DualShock Logitech controller.
     */
    DualStickController dualstick;
    /**
     * Limit switch at bottom left of elevator.
     */
    Joystick joy;
    /**
     * The roller we use to pull totes into our robot.
     */
    DigitalInput lifterLeftMin;
    /**
     * Limit switch at bottom right of elevator.
     */
    DigitalInput lifterRightMin;
    /**
     * Limit switch at top left of elevator.
     */
    DigitalInput lifterLeftMax;
    /**
     * Limit switch at top right of elevator.
     */
    DigitalInput lifterRightMax;
    /**
     * Limit switch that checks if a tote is in the robot.
     */
    DigitalInput rollerLeftLimit;
    /**
     * Limit switch that checks if a tote is in the robot.
     */
    DigitalInput rollerRightLimit;
    /**
     * The solenoid for the piston that extends the left-flap of the grabber.
     */
    Solenoid grabberLeftExtend;
    /**
     * The solenoid for the piston that retracts the left-flap of the grabber.
     */
    Solenoid grabberLeftRetract;
    /**
     * The solenoid for the piston that extends the right-flap of the grabber.
     */
    Solenoid grabberRightExtend;
    /**
     * The solenoid for the piston that retracts the right-flap of the grabber.
     */
    Solenoid grabberRightRetract;
    /**
     * The flight-stick that we use to control the manipulators on our robot.
     */
    Roller roller;
    /**
     * The lifter that raises totes for stacking.
     */
    Lifter lifter;
    /**
     * The grabber that hooks on to the sides of totes.
     */
    Grabber grabber;
    /**
     * Speed controller that controls left motor of elevator.
     */
    Victor leftLift;
    /**
     * Speed controller that controls right motor of elevator.
     */
    Victor rightLift;
    /**
     * Spike that controls left roller.
     */
    Relay leftRoll;
    /**
     * Spike that controls right roller.
     */
    Relay rightRoll;

    /**
     * The current object that controls the robot. It should be an instance of
     * Autonomous or Teleop (or null if disabled), usually.
     */
    private Tickable currentControl;

    private Mode mode = Mode.DISABLED;

    /**
     * A set of objects to be periodically ticked.
     */
    private final HashSet<Tickable> tickables = new HashSet<>();

    /**
     * Construct a new Robot. This initializes all of the hardware on the Robot.
     */
    public Robot() {
        //Initialize the mechanisms
        rightLift = new Victor(LIFT_RIGHT);
        leftLift = new Victor(LIFT_LEFT);

        leftRoll = new Relay(ROLLER_LEFT);
        rightRoll = new Relay(ROLLER_RIGHT);

        lifterLeftMax = new DigitalInput(LIFT_LEFT_MAX);
        lifterLeftMin = new DigitalInput(LIFT_LEFT_MIN);
        lifterRightMax = new DigitalInput(LIFT_RIGHT_MAX);
        lifterRightMin = new DigitalInput(LIFT_RIGHT_MIN);
        rollerLeftLimit = new DigitalInput(ROLLER_LEFT_LIMIT);
        rollerRightLimit = new DigitalInput(ROLLER_RIGHT_LIMIT);

        grabberLeftExtend = new Solenoid(GRABBER_LEFT_EXTEND);
        grabberLeftRetract = new Solenoid(GRABBER_LEFT_RETRACT);
        grabberRightExtend = new Solenoid(GRABBER_RIGHT_EXTEND);
        grabberRightRetract = new Solenoid(GRABBER_RIGHT_RETRACT);

        robotDrive = new RobotDrive(new Talon(DRIVE_REAR_LEFT),
                new Talon(DRIVE_FRONT_LEFT),
                new Talon(DRIVE_REAR_RIGHT),
                new Talon(DRIVE_FRONT_RIGHT));

        dualstick = new DualStickController(PORT_DUALSTICK);
        joy = new Joystick(PORT_FLIGHTSTICK);

        lifter = new Lifter(leftLift, rightLift, lifterLeftMin, lifterLeftMax, lifterRightMin, lifterRightMax);
        grabber = new Grabber(grabberLeftRetract, grabberLeftExtend, grabberRightRetract, grabberRightExtend, lifterLeftMin, lifterRightMin);
        roller = new Roller(leftRoll, rightRoll, rollerLeftLimit, rollerRightLimit);
    }

    @Override
    public void startCompetition() {
        Tickable dashboardUpdater = this::updateDashboard;
        Tickable modeUpdater = this::updateMode;
        addTickable(dashboardUpdater);
        addTickable(modeUpdater);
        addTickable(lifter);
        addTickable(grabber);
        addTickable(roller);
        while (true) {
            try {
                tick();
                Thread.sleep(TICK_PERIOD);
            } catch (Exception x) {
                x.printStackTrace(System.err);
            }
        }
    }

    /**
     * Ticks each tickable in the list of tickables.
     */
    public void tick() {
        //Tick each tickable
        tickables.stream().forEach(Tickable::tick);
    }

    /**
     * Update the robot mode to the current mode dictated by FMS/the Driver
     * Station.
     */
    private void updateMode() {
        Mode previousMode = mode;
        mode = getCurrentMode();
        if (mode != previousMode) {
            switchToMode(mode);
        }
    }

    private Mode getCurrentMode() {
        if (isDisabled()) {
            return Mode.DISABLED;
        } else if (isAutonomous()) {
            return Mode.AUTO;
        } else if (isOperatorControl()) {
            return Mode.TELEOP;
        } else {
            return Mode.DISABLED;
        }
    }

    private void switchToMode(Mode mode) {
        switch (mode) {
            case AUTO:
                switchToAuto();
                break;
            case TELEOP:
                switchToTeleop();
                break;
            case DISABLED:
                disable();
                break;
        }
    }

    /**
     * Set the robot mode to teleop.
     */
    private void switchToTeleop() {
        removeCurrentControl();
        Teleop teleop = new Teleop(dualstick, joy);
        teleop.init(robotDrive, lifter, grabber, roller);
        currentControl = teleop;
        addTickable(currentControl);
    }

    /**
     * Set the robot mode to autonomous.
     */
    private void switchToAuto() {
        removeCurrentControl();
        Autonomous auto = new Autonomous();
        currentControl = auto;
        addTickable(currentControl);
    }

    /**
     * Switch the robot mode to disabled.
     */
    private void disable() {
        removeCurrentControl();
        currentControl = null;
    }

    /**
     * Remove the currentControl from the list of tickables if it is not null.
     */
    private void removeCurrentControl() {
        if (currentControl != null) {
            removeTickable(currentControl);
        }
    }

    /**
     * Updates the information on the Smart Dashboard.
     */
    private void updateDashboard() {
        SmartDashboard.putString("Flaps", grabber.toString());
        SmartDashboard.putString("Elevator Status", lifter.toString());
        SmartDashboard.putString("Roller Direction", roller.toString());
        SmartDashboard.putBoolean("Crate in Loader", 
                !rollerLeftLimit.get() && !rollerRightLimit.get());
    }

    /**
     * Adds a Tickable object to the list of Tickable objects. These objects'
     * tick methods will be, synchronously, and in no particular order, invoked
     * periodically, with a TICK_PERIOD millisecond gap in between each
     * iteration.
     *
     * @param tickable the object that should be added to the set
     */
    public void addTickable(Tickable tickable) {//Adds an object to a list of tickables
        tickables.add(tickable);
    }

    /**
     * Removes a Tickable object from the list of Tickable objects.
     *
     * @param tickable the object to remove
     */
    public void removeTickable(Tickable tickable) {//Removes object from list
        tickables.remove(tickable);
    }

    private enum Mode {

        AUTO, TELEOP, DISABLED;
    }

}
