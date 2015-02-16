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

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * This class handles interaction between the drivers, the controllers, and the
 * mechanisms on the robot.
 * 
 * It is initialized with every mechanism involved in the Teleop period of the 
 * competition.
 * @author Erich Maas
 */
public class Teleop implements Tickable {

    /**
     * An instance of the DualStickController class representing one of the
     * controllers.
     */
    private final DualStickController dualStick;
    /**
     * An instance of the Joystick class representing the other controller.
     */
    private final Joystick joy;
    //See individual class documentation for more information
    /**
     * The drive-train of the robot.
     */
    RobotDrive rd;
    /**
     * The Lifter subassembly of the robot.
     */
    Lifter lifter;
    /**
     * The Grabber subassembly of the robot.
     */
    Grabber grabber;
    /**
     * The Roller subassembly of the robot.
     */
    Roller roller;
    
    private boolean initialized;

    /**
     * Initialize the Teleop instance with the mechanisms on the Robot that
     * Teleop will control.
     * @param drive the drive-train of the robot
     * @param lifter the Lifter subassembly of the robot
     * @param grabber the Grabber subassembly of the robot
     * @param roller the Roller subassembly of the robot
     */
    public void init(RobotDrive drive, Lifter lifter, Grabber grabber, Roller roller) {//Initializes necessary things
        initialized = true;
        this.rd = drive;
        this.lifter = lifter;
        this.grabber = grabber;
        this.roller = roller;
    }

    /**
     * Process teleop events. This method reads input from the controllers and
     * responds by manipulating various mechanisms. This needs to be called
     * periodically in order to continuously respond to input from the Driver's
     * Station.
     */
    @Override
    public void tick() {
        if(!initialized){
            throw new IllegalStateException("Init method not called! "
                    + "Please call Teleop.init before using this object.");
        }
        
        double leftValue, rightValue;
        leftValue = (dualStick.getY(GenericHID.Hand.kLeft) * -1);//Gets left stick
        rightValue = (dualStick.getY(GenericHID.Hand.kRight)*-1);// Gets right stick 
        rd.tankDrive(leftValue, rightValue);// Sets up tank drive for robot

        //Set up all buttons for lifter, grabber, and roller
        if (dualStick.getBumper(GenericHID.Hand.kLeft) && dualStick.getBumper(GenericHID.Hand.kRight)) {
            rd.tankDrive(leftValue / 2, rightValue / 2);//On both bumpers, go half speed
        }

        //BUTTONS WILL NEED TO BE CHANGED
        boolean grabbing = false;
        if (joy.getTrigger()) {//On joystick trigger
            if (grabbing) {
                grabber.release();//If grabbing, release
            } else {
                grabber.grab();//If released, grab
            }
            grabbing = !grabbing;//Change booleean
        }

        if (joy.getRawButton(2)) {//Roll in on joy button 12
            roller.in();
        }
        if (joy.getRawButton(11)) {//Roll out on joy button 11
            roller.out();
        }
        if (joy.getY() > -.5) {// May need to change variable
            lifter.up();     // If joy is forward, lift elevator
        }
        if (joy.getY() < .5)//May need to change variable
        {
            lifter.down();// If joy is back, lower elevator
        }
    }

    /**
     * Creates a new Teleop instance attached to the provided controllers.
     * @param dualStick instance of the dual analog stick controller to use
     * @param joy instance of the flight stick controller to use
     */
    public Teleop(DualStickController dualStick, Joystick joy) {//Requires a controller and joystick
        this.dualStick = dualStick;
        this.joy = joy;
    }

}
