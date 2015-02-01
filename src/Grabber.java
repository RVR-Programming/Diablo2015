
import edu.wpi.first.wpilibj.Solenoid;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Grabber {

    private final Solenoid left1, left2, right1, right2;

    public Grabber(Solenoid left1, Solenoid left2, Solenoid right1, Solenoid right2) {//Initializes all pistons
        this.left1 = left1;
        this.left2 = left2;
        this.right1 = right1;
        this.right2 = right2;
    }

    public void grab() { //Extends all pistons to grab crate
        left1.set(true);
        left2.set(true);
        right1.set(true);
        right2.set(true);
    }

    public void release() {//Retracts all pistons to release crate
        left1.set(false);
        left2.set(false);
        right1.set(false);
        right2.set(false);
    }

}
