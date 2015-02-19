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
     * Time that the roller will spin for.
     */
    private final int ROLLER_TIME = (int) (2500 / Robot.TICK_PERIOD);

    /**
     * Counter for the tick method.
     */
    private int tickCount;
    /**
     * Left Spike for the roller.
     */
    private final Relay left;
    /**
     * Right Spike for the roller.
     */
    private final Relay right;
    /**
     * Limit switch that checks if tote is in robot.
     */
    private final DigitalInput leftStat;
    /**
     * Limit switch that checks if tote is in robot.
     */
    private final DigitalInput rightStat;
    /**
     * Sets if the left roller will go in, out, or be stopped.
     */
    private int leftSpeed = 0;
    /**
     * Sets if the right roller will go in, out, or be stopped.
     */
    private int rightSpeed = 0;

    /**
     * Constructs the roller object
     *
     * @param left Spike for the left motor
     * @param right Spike for the right motor
     * @param leftStat Limit switch that checks if a tote is in the robot
     * @param rightStat Limit switch that checks if tote is in the robot
     */
    public Roller(Relay left, Relay right, DigitalInput leftStat, DigitalInput rightStat) {
        this.left = left;
        this.right = right;
        this.leftStat = leftStat;
        this.rightStat = rightStat;
    }

    /**
     * Both Rollers are set to pull in at max speed for 3 seconds. Motors spin
     * in opposite directions.
     */
    public void in() { //Both rollers go in max speed
        leftSpeed = 1;
        rightSpeed = 1;
        tickCount = 0;
    }

    /**
     * Both Rollers are set to push out at max speed for 3 seconds. Motors spin
     * in opposite directions.
     */
    public void out() {//Both rollers go out max speed
        leftSpeed = -1;
        rightSpeed = -1;
        tickCount = 0;
    }

    /**
     * Controls direction of the rollers and time the rollers are activated.
     */
    @Override
    public void tick() {
        if (leftSpeed < 0) {
            left.set(Relay.Value.kReverse);
        } else if (leftSpeed > 0 && leftStat.get()) {
            left.set(Relay.Value.kForward);
        } else {
            left.set(Relay.Value.kOff);
        }

        if (rightSpeed < 0) {
            right.set(Relay.Value.kForward);
        } else if (rightSpeed > 0 && rightStat.get()) {
            right.set(Relay.Value.kReverse);
        } else {
            right.set(Relay.Value.kOff);
        }

        if (leftSpeed != 0 || rightSpeed != 0) {
            tickCount++;
            if (tickCount > ROLLER_TIME) {//Turns Rollers off when tickCOunt goes above limit
                leftSpeed = 0;
                rightSpeed = 0;
                tickCount = 0;
                left.set(Relay.Value.kOff);
                right.set(Relay.Value.kOff);
            }
        }
    }

    @Override
    public String toString() {
        if (leftSpeed < 0) {
            return "OUT";
        } else if (leftSpeed > 0) {
            return "IN";
        } else {
            return "OFF";
        }
    }
}
