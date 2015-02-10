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
 *
 * @author user
 */
public class DualStickController extends GenericHID {

    private final DriverStation ds = DriverStation.getInstance();
    private int port;

    public DualStickController(int port) {
        this.port = port;
    }

    public double getX(Hand hand) {//Returns x axis of left or right stick
        if (hand == Hand.kLeft) {//Checks for left or right 
            return getRawAxis(0);
        } else {
            return getRawAxis(2);
        }
    }

    public double getY(Hand hand) {//Returns y axis left or right stick
        if (hand == Hand.kLeft) {//Checcks left or right
            return getRawAxis(1);
        } else {
            return getRawAxis(3);
        }
    }

    public double getZ(Hand hand) {// No z axis
        return 0;
    }

    public double getTwist() {//No twist
        return 0;
    }

    public double getThrottle() {//No throttle
        return 0;
    }

    public double getRawAxis(int which) {
        return ds.getStickAxis(port, which);//Returns given axis of stick
    }

    public boolean getTrigger(Hand hand) {// Returns left or right trigger
        if (hand == Hand.kLeft) {
            return getTrigger(Hand.kLeft);
        } else {
            return getTrigger(Hand.kRight);
        }
    }

    public boolean getTop(Hand hand) {//Returns pushing in analog sticks
        if (hand == Hand.kLeft) {//Checks left or right
            return getRawButton(11);
        } else {
            return getRawButton(12);
        }
    }

    public boolean getBumper(Hand hand) {//Returns bumper
        if (hand == Hand.kLeft) {//Checks left or right
            return getBumper(Hand.kLeft);
        } else {
            return getBumper(Hand.kRight);
        }
    }

    public boolean getRawButton(int button) {// Returns the rest of the buttons
        return ((ds.getStickButtons(port) >> (button - 1)) & 1) == 1;//BOOM, MAGIC
    }//Shifts binary number over "button" digits to see if a button is used or not

    @Override
    public int getPOV(int pov) {// Not sure what this is for, returning zero
        return 0;
    }

}
