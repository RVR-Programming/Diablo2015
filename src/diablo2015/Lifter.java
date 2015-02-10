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

import diablo2015.Tickable;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

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
