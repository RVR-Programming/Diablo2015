
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
         if(hand == Hand.kLeft){//Checks left or right
            return getRawButton(11);
        }
        else{
            return getRawButton(12);
        }
    }

    public boolean getBumper(Hand hand) {//Returns bumper
         if(hand == Hand.kLeft){//Checks left or right
            return getBumper(Hand.kLeft);
        }
        else{
            return getBumper(Hand.kRight);
        }
    }
    public boolean getRawButton(int button) {// Returns the rest of the buttons
        return ((ds.getStickButtons(port) >> (button - 1)) & 1) == 1;//BOOM, MAGIC
    }


}
