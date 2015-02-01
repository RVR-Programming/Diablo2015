/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diablo2015;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import java.util.Enumeration;

/**
 *
 * @author user
 */
public class Robot {

    DigitalInput limitSwitchTop, limitSwitchMid, limitSwitchBot;
    RobotDrive robotDrive;
    DualStickController dualstick;
    Solenoid left1, left2, right1, right2;
    Joystick joy;
    Hashtable tickables = new Hashtable();
    Roller roller;
    Lifter lifter;
    Grabber grabber;
    Talon lift1, lift2;

    public void robotMain() {
        robotDrive = new RobotDrive(new Talon(99), new Talon(99)); //Creates robot drive
        dualstick = new DualStickController(99); //Creates dualstick controller
        joy = new Joystick(99);//Create sjoystick
        Teleop teleop = new Teleop(dualstick, joy); //Creates teleop with two controllers
        teleop.init(robotDrive, lifter, grabber, roller);//Initializes teleop
        add(teleop);
        lifter = new Lifter(lift1, lift2, limitSwitchBot, limitSwitchMid, limitSwitchTop);//Creates lifter with Speed controllers and limit switches
        add(lifter);
        grabber = new Grabber(left1, left2, right1, right2);//Creates grabber with solenoids
        add(grabber);
        roller = new Roller(lift1, lift1, 1);//Creates roller with speed controllers
        add(roller);

        while (true) {
            Enumeration e = tickables.elements();
            while (e.hasMoreElements()) {
                Tickable t = (Tickable) e.nextElement();//Ticks all tickable things
            }
            try {
                Thread.sleep(50);//Waits 50 milisecs
            } catch (Exception x) {
            }
        }

    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {

    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }

    public void add(Tickable obj) {//Adds an object to a list of tickables
        tickables.put(new Integer(obj.hashCode()), obj);
    }

    public void remove(Tickable obj) {//Removes object from list
        tickables.remove(new Integer(obj.hashCode()));
    }
}
