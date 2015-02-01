package diablo2015;

import diablo2015.Roller;
import diablo2015.Tickable;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public void tick() {
        double leftValue, rightValue;
        leftValue = dualStick.getY(GenericHID.Hand.kLeft);//Gets left stick
        rightValue = dualStick.getY(GenericHID.Hand.kRight);// Gets right stick 
        rd.tankDrive(leftValue, rightValue);// Sets up tank drive for robot
        
        //Set up all buttons for lifter, grabber, and roller
    }
    public Teleop(DualStickController dualStick, Joystick joy){//Requires a controller and joystick
        this.dualStick = dualStick;
        this.joy = joy;
    }

    void init(RobotDrive robotDrive, Lifter lifter, Grabber grabber, Roller roller) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
