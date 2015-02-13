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

import diablo2015.Roller;
import diablo2015.Tickable;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author user
 */
public class Teleop implements Tickable {

    private final DualStickController dualStick;
    private final Joystick joy;
    RobotDrive rd;
    Lifter lifter;
    Grabber grabber;
    Roller roller;

    public void init(RobotDrive rd, Lifter lifter, Grabber grabber, Roller roller) {//Initializes necessary things
        this.rd = rd;
        this.lifter = lifter;
        this.grabber = grabber;
        this.roller = roller;
    }

    public void tick() {//THE ROLLERS ARE SPIKES, THE LIFTER IS VICTORS, AND THE TANK DRIVE IS TALONS 
        double leftValue, rightValue;
        leftValue = dualStick.getY(GenericHID.Hand.kLeft);//Gets left stick
        rightValue = dualStick.getY(GenericHID.Hand.kRight);// Gets right stick 
        rd.tankDrive(leftValue, rightValue);// Sets up tank drive for robot

        //Set up all buttons for lifter, grabber, and roller
        if(dualStick.getBumper(GenericHID.Hand.kLeft) && dualStick.getBumper(GenericHID.Hand.kRight)){
            rd.tankDrive(leftValue / 2, rightValue / 2);//On both bumpers, go half speed
        }
        
        //BUTTONS MAY NEED TO BE CHANGED
        boolean grabbing = false;
        if (joy.getTrigger(GenericHID.Hand.kLeft)){//On joystick trigger
            if(grabbing){
                grabber.release();//If grabbing, release
            } else{
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
        if(joy.getX() < -.5){// May need to change variable
            lifter.up();     // If joy is forward, lift elevator
        }
        if(joy.getY() > .5)//May need to change variable
            lifter.down();// If joy is back, lower elevator

    }

    public Teleop(DualStickController dualStick, Joystick joy) {//Requires a controller and joystick
        this.dualStick = dualStick;
        this.joy = joy;
    }

}
