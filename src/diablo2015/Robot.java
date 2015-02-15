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
 *
 * @author user
 */
public class Robot extends SampleRobot{

    DigitalInput min = new DigitalInput(99), max = new DigitalInput(99);//Sets up spikes
    RobotDrive robotDrive;
    DualStickController dualstick;
    Solenoid left = new Solenoid(99), right = new Solenoid(99);// Sets up solenoids
    Joystick joy;
    HashSet<Tickable> tickables = new HashSet<>();
    Roller roller;
    Lifter lifter;
    Grabber grabber;
    Victor lift1 = new Victor(2), lift2 = new Victor(3);// Sets up victors in ports 3 and 4
    Relay leftRoll = new Relay(4), rightRoll = new Relay(5);// Sets up 

    @Override
    public void robotMain() {

        //TODO:
        ///////////////////////////////////
        // ALL PORTS NEED TO BE ASSIGNED //
        ///////////////////////////////////
        robotDrive = new RobotDrive(new Talon(9), new Talon(8), new Talon(7), new Talon(6));
        //robotDrive = new RobotDrive(new Talon(99), new Talon(99)); //Creates robot drive
        dualstick = new DualStickController(1); //Creates dualstick controller
        joy = new Joystick(2);//Create sjoystick
        lifter = new Lifter(lift1, lift2, min, max);//Creates lifter with Speed controllers and limit switches
        add(lifter);
        grabber = new Grabber(left, right, min);//Creates grabber with solenoids
        add(grabber);
        roller = new Roller(leftRoll, rightRoll);//Creates roller with speed controllers
        add(roller);
        Teleop teleop = new Teleop(dualstick, joy); //Creates teleop with two controllers
        teleop.init(robotDrive, lifter, grabber, roller);//Initializes teleop
        add(teleop);

        while (true) {
            Iterator<Tickable> it = tickables.iterator();
            while (it.hasNext()) {
                Tickable t = it.next();//Ticks all tickable things
                t.tick();
            }
            try {
                Thread.sleep(50);//Waits 50 milisecs
            } catch (Exception x) {
            }
        }

    }

    public void add(Tickable obj) {//Adds an object to a list of tickables
        tickables.add(obj);
    }

    public void remove(Tickable obj) {//Removes object from list
        tickables.remove(obj);
    }
}
