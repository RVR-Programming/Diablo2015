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
import edu.wpi.first.wpilibj.Relay;

/**
 * Controls the rollers that pull the totes in
 *
 * @author Erich Maas
 */
public class Roller implements Tickable {

    /**
     * Counter for the tick method.
     */
    private int tickCount = 0;
    /**
     * Left Spike for the roller.
     */
    private final Relay left;
    /**
     * Right Spike for the roller.
     */
    private final Relay right;
    /**
     * Limit switch that checks if tote is in robot
     */
    private final DigitalInput toteSwitch;
    /**
     * Sets if the rollers will go in, out, or be stopped
     */
    private int speed = 0;

    /**
     * Constructs the roller object
     *
     * @param left Spike for the left motor
     * @param right Spike for the right motor
     */
    public Roller(Relay left, Relay right, DigitalInput toteSwitch) {
        this.left = left;
        this.right = right;
        this.toteSwitch = toteSwitch;
    }

    /**
     * Both Rollers are set to pull in at max speed. Motors spin in opposite
     * directions
     */
    public void in() { //Both rollers go in max speed
        speed = 1;
    }

    /**
     * Both Rollers are set to push out at maximum speed. Motors spin in
     * opposite directions.
     */
    public void out() {//Both rollers go out max speed
        speed = -1;
    }

    /**
     * Controls direction of the rollers and time the rollers are activated.
     */
    public void tick() {

        if (speed < 0 && !toteSwitch.get()) {
            left.set(Relay.Value.kOn);
            right.set(Relay.Value.kReverse);
        } else if (speed > 0) {
            left.set(Relay.Value.kReverse);
            right.set(Relay.Value.kOn);
        } else {
           left.set(Relay.Value.kOff);
           right.set(Relay.Value.kOff);
        }
        if (tickCount > 31 || toteSwitch.get()) {
            speed = 0;
            tickCount = 0;
            left.set(Relay.Value.kOff);
            right.set(Relay.Value.kOff);
        } else {
            tickCount++;
        }
    }

}
