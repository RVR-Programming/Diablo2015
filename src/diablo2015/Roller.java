/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diablo2015;

import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author user
 */
public class Roller {
    private final Talon left, right;
    private int rollerSpeed;
    public Roller(Talon left, Talon right, int rollerSpeed){
        this.left = left;
        this.right = right;
        this.rollerSpeed = rollerSpeed;
    }
    public void in(){ //Both rollers go in max speed
        left.set(1);
        right.set(-1);
    }
    public void out(){//Both rollers go out max speed
        left.set(-1);
        right.set(1);
    }
    public void roll(){//Both rollers go according to input
        left.set(-rollerSpeed);
        right.set(rollerSpeed);
    }
    
   
}
