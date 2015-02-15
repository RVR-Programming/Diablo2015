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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;

/**
 *Extends GenericHID to have two analog sticks, bumpers, and triggers
 * 
 * @author Erich Maas
 */
public class DualStickController extends GenericHID {
    /**
     * Instance of driver station
     */
    private final DriverStation ds = DriverStation.getInstance();
    /**
     * Port that controller will be plugged in to
     */
    private int port;
    /**
     * Initializes controller 
     * @param port The port the controller is plugged in to 
     */
    public DualStickController(int port) {
        this.port = port;
    }
    /**
     * Gets x axis of analog stick of given hand.
     * @param hand The side of the controller is being checked
     * @return the value of the axis
     */
    public double getX(Hand hand) {//Returns x axis of left or right stick
        if (hand == Hand.kLeft) {//Checks for left or right 
            return getRawAxis(0);
        } else {
            return getRawAxis(2);
        }
    }
    /**
     * Gets y axis of given hand.
     * @param hand The side of the controller being tested
     * @return value of the axis
     */
    public double getY(Hand hand) {//Returns y axis left or right stick
        if (hand == Hand.kLeft) {//Checcks left or right
            return getRawAxis(1);
        } else {
            return getRawAxis(3);
        }
    }
    /**
     * Gets z axis of analog stick, which will always be 0.
     * @param hand Side of controller being checked
     * @return Will always be 0
     */
    public double getZ(Hand hand) {// No z axis
        return 0;
    }
    /**
     * 
     * @return Will always return 0
     */
    public double getTwist() {//No twist
        return 0;
    }
    /**
     * 
     * @return Will always return 0 because their is no throttle 
     */
    public double getThrottle() {//No throttle
        return 0;
    }
    /**
     * Gives value of axis
     * @param axis Axis being returned
     * @return  value of axis
     */
    public double getRawAxis(int axis) {
        return ds.getStickAxis(port, axis);//Returns given axis of stick
    }
    /**
     * Gives value of left and right triggers
     * @param hand Tests left or right trigger
     * @return if trigger is pulled or released
     */
    public boolean getTrigger(Hand hand) {// Returns left or right trigger
        if (hand == Hand.kLeft) {
            return getTrigger(Hand.kLeft);
        } else {
            return getTrigger(Hand.kRight);
        }
    }
    /**
     * Tests if left or right analog stick is pushed in
     * @param hand Analog stick being tested
     * @return value of top button
     */
    public boolean getTop(Hand hand) {//Returns pushing in analog sticks
        if (hand == Hand.kLeft) {//Checks left or right
            return getRawButton(11);
        } else {
            return getRawButton(12);
        }
    }
    /**
     * Tests if left or right bumper is pushed
     * @param hand Bumper being tested
     * @return value of bumper
     */
    public boolean getBumper(Hand hand) {//Returns bumper
        if (hand == Hand.kLeft) {//Checks left or right
            return getBumper(Hand.kLeft);
        } else {
            return getBumper(Hand.kRight);
        }
    }
    /**
     * Tests is a given button is pushed
     * @param button Button being tested
     * @return if given button is pressed
     */
    public boolean getRawButton(int button) {// Returns the rest of the buttons
        return ds.getStickButton(port, (byte) button); //New method from FIRST
    }
    /**
     * Always return 0
     * @param pov
     * @return 0
     */
    @Override
    public int getPOV(int pov) {// Not sure what this is for, returning zero
        return 0;
    }

}
