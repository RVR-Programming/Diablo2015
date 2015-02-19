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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Extends and retracts pistons to grab or release the totes.
 *
 *
 * @author Erich
 */
public class Grabber implements Tickable {

    /**
     * The solenoid that extends the left flap of the grabber.
     */
    private final Solenoid leftExtend;
    /**
     * The solenoid that retracts the left flap of the grabber.
     */
    private final Solenoid leftRetract;
    /**
     * The solenoid that extends the right flap of the grabber.
     */
    private final Solenoid rightExtend;
    /**
     * The solenoid that retracts the right flap of the grabber.
     */
    private final Solenoid rightRetract;
    /**
     * The limit switch for the bottom of the left side of the elevator.
     */
    private final DigitalInput leftMin;
    /**
     * The limit switch for the bottom of the right side of the elevator.
     */
    private final DigitalInput rightMin;
    /**
     * It says if the elevator is fully lowered.
     */
    private boolean lowered;

    /**
     *
     * @param leftExtend The solenoid for extending the left flap
     * @param leftRetract The solenoid for retracting the left flap
     * @param rightExtend The solenoid for extending the right flap
     * @param rightRetract The solenoid that retracts the right flap
     * @param leftMin The left limit switch that tells if the lifter is lowered
     * @param rightMin The right limit switch that tells if the lifter is
     * lowered
     *
     */
    public Grabber(Solenoid leftExtend, Solenoid leftRetract, Solenoid rightExtend, Solenoid rightRetract, DigitalInput leftMin, DigitalInput rightMin) {//Initializes all pistons
        this.leftExtend = leftExtend;
        this.leftRetract = leftRetract;
        this.rightExtend = rightExtend;
        this.rightRetract = rightRetract;
        this.leftMin = leftMin;
        this.rightMin = rightMin;

        rightRetract.set(false);
        leftRetract.set(false);
        rightExtend.set(true);
        leftExtend.set(true);

    }

    /**
     * Sets both solenoids for the flaps to "true" to pick up the totes if
     * elevator is fully lowered.
     */
    public void grab() { //Extends all pistons to grab crate
        // if (lowered) { // Only works when lifter fully lowered
        leftRetract.set(false);
        rightRetract.set(false);
        leftExtend.set(true);
        rightExtend.set(true);
        //}
    }

    /**
     * Sets both solenoids for the flaps to "false" to pick up the totes if
     * elevator is fully lowered.
     */
    public void release() {//Retracts all pistons to release crate
        //if (lowered) {   // Only works when fully lowered
        leftRetract.set(true);
        rightRetract.set(true);
        leftExtend.set(false);
        rightExtend.set(false);
        //}
    }

    /**
     *
     * Check to see if both minimum limit switches have been activated.
     */
    public void tick() {
        lowered = rightMin.get() && leftMin.get();
    }
    @Override
    public String toString(){
        if(leftRetract.get() == true){
            return "UP";
        } else{
            return "DOWN";
        }
    }

}
