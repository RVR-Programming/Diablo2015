package diablo2015;


import diablo2015.Tickable;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Lifter implements Tickable{
    private final int speed = 1;
    private final DigitalInput switchBot, switchMid, switchTop;
    private final Talon lift1, lift2;

    public Lifter(Talon lift1, Talon lift2, DigitalInput switchBot, DigitalInput switchMid, DigitalInput switchTop) {
        this.lift1 = lift1;
        this.lift2 = lift2;
        this.switchBot = switchBot;
        this.switchMid = switchMid;
        this.switchTop = switchTop;
    }
    
    public void bottom(){// Goes down until reaches bottom switch
        lift1.set(speed);
        lift2.set(-speed);
        if(switchBot.get()){
            lift1.set(0);
            lift2.set(0);
        }
    }
    public void middle(){
        if(switchBot.get()){ // If at bottom, goes up 
            lift1.set(-speed);
            lift2.set(speed);          
        }else{                //If at top, goes down
            lift1.set(speed);
            lift2.set(-speed);            
        }
        if(switchMid.get()){ // Stops at middle switch
                lift1.set(0);
                lift2.set(0);
            }
    }
    public void top(){ // Goes up until reaces top switch
        lift1.set(-speed);
        lift2.set(speed);
        if(switchTop.get()){
            lift1.set(0);
            lift2.set(0);
        }
    }
    public void tick(){
        
    }

}
