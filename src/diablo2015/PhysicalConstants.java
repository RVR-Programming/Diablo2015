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

/**
 * This class holds several constants that represent physical constants of the
 * Robot. These can be PWM channels, Digital IO channels, dimensions, diametres,
 * among other things.
 * @author Dylan Frese
 */
public class PhysicalConstants {
    
    /**
     * A PWM channel corresponding to a speed controller for the drive train.
     * At the time that this was written, the speed controllers used here were
     * Talons.
     */
    public static final int DRIVE_FRONT_LEFT  = 9,
                            DRIVE_REAR_LEFT   = 8,
                            DRIVE_FRONT_RIGHT = 0,
                            DRIVE_REAR_RIGHT  = 1;
    /**
     * A PWM channel corresponding to a speed controller for the Lifter.
     * At the time that this was written, the speed controllers used here were
     * Victors.
     */
    public static final int LIFT_LEFT  = 7,
                            LIFT_RIGHT = 2;
    /**
     * A Digital IO channel corresponding to a limit switch for the Lifter.
     */
    public static final int LIFT_LEFT_MAX  = 8,
                            LIFT_LEFT_MIN  = 9,
                            LIFT_RIGHT_MAX = 0,
                            LIFT_RIGHT_MIN = 1;
    /**
     * A Digital IO channel corresponding to a relay for the Roller.
     */
    public static final int ROLLER_LEFT  = 0,
                            ROLLER_RIGHT = 1;
    /**
     * A Digital IO channel corresponding to a limit switch for the Roller.
     */
    public static final int ROLLER_LEFT_LIMIT  = 7,
                            ROLLER_RIGHT_LIMIT = 2;
    /**
     * A solenoid port corresponding to a solenoid used in the Grabber.
     */
    public static final int GRABBER_LEFT_EXTEND   = 5,
                            GRABBER_LEFT_RETRACT  = 2,
                            GRABBER_RIGHT_EXTEND  = 3,
                            GRABBER_RIGHT_RETRACT = 4;
    /**
     * A virtual port corresponding to the location of this controller in the
     * DriverStation. This can be reordered in the driver's station.
     */
    public static final int PORT_DUALSTICK   = 0,
                            PORT_FLIGHTSTICK = 1;
    
    private PhysicalConstants() {
    }

}
