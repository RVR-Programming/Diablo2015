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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Our 2015 Recycle Rush Robot.
 *
 * This class represents our Robot and all of the physical mechanisms that go on
 * it. This class extends SampleRobot, provided by FIRST. There should
 * reasonably only be one instance of this class per session.
 *
 * @author Erich Maas
 */
public class Robot extends SampleRobot {

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
    public static final long TICK_PERIOD = 50;

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
    DigitalInput leftMin;
    /**
     * Limit switch at bottom right of elevator.
     */
    DigitalInput rightMin;
    /**
     * Limit switch at top left of elevator.
     */
    DigitalInput leftMax;
    /**
     *Limit switch at top right of elevator.
     */
    DigitalInput rightMax;
    /**
     * Lift switch that checks if a tote is in the robot.
     */
    DigitalInput toteStat;
    /**
     * The solenoid for the piston that powers the left-flap of the grabber.
     */
    Solenoid leftExtend;
    /**
     * The solenoid for the piston that powers the right-flap of the grabber.
     */
    Solenoid leftRetract;
    Solenoid rightExtend;
    Solenoid rightRetract;
    /**
     * The flight-stick that we use to control the manipulators on our robot.
     */
    Joystick joy;
    /**
     * The roller we use to pull totes into our robot.
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
     * A set of objects to be periodically ticked.
     */
    private final HashSet<Tickable> tickables = new HashSet<>();

    /**
     * The main point of entry for the program.
     *
     * The FIRST library will call this method upon the initialization of the
     * Robot, that is, when the Robot turns on and the roboRIO is booted.
     */
    @Override
    public void robotMain() {
        //TODO:
        ///////////////////////////////////
        // ALL PORTS NEED TO BE ASSIGNED //
        ///////////////////////////////////
        //THE ROLLERS ARE SPIKES, THE LIFTER IS VICTORS, AND THE TANK DRIVE IS TALONS
        rightLift = new Victor(2);
        leftLift = new Victor(7);

        leftRoll = new Relay(0);
        rightRoll = new Relay(1);

        leftMax = new DigitalInput(8);
        rightMax = new DigitalInput(0);
        leftMin = new DigitalInput(7);
        rightMin = new DigitalInput(1);
        toteStat = new DigitalInput(9);

        leftExtend = new Solenoid(5);
        leftRetract = new Solenoid(2);
        
        rightExtend = new Solenoid(3);
        rightRetract = new Solenoid(4);
        
        robotDrive = new RobotDrive(new Talon(9), new Talon(8), new Talon(0), new Talon(1));
        dualstick = new DualStickController(0); //Creates dualstick controller
        joy = new Joystick(1);//Create sjoystick
        lifter = new Lifter(leftLift, rightLift, leftMin, leftMax, rightMin, rightMax);//Creates lifter with Speed controllers and limit switches
        grabber = new Grabber(leftRetract, leftExtend, rightRetract, rightExtend, leftMin, rightMin);//Creates grabber with solenoids
        roller = new Roller(leftRoll, rightRoll, toteStat, 0);//Creates roller with speed controllers

        Teleop teleop = new Teleop(dualstick, joy); //Creates teleop with two controllers
        teleop.init(robotDrive, lifter, grabber, roller);//Initializes teleop

        addTickable(lifter);
        addTickable(grabber);
        addTickable(roller);
        addTickable(teleop);

        while (true) {
            Iterator<Tickable> it = tickables.iterator();
            while (it.hasNext()) {
                Tickable t = it.next();//Ticks all tickable things
                t.tick();
            }
            try {
                Thread.sleep(TICK_PERIOD);//Waits 50 milisecs
            } catch (Exception x) {
            }
        }

    }

    /**
     * Adds a Tickable object to the list of Tickable objects. These objects'
     * tick methods will be, synchronously, and in no particular order, invoked
     * periodically, with a 50 millisecond gap in between each iteration.
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
}
