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
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author user
 */
public class Lifter implements Tickable {

    private final int speed = 1;
    private final Victor lift1, lift2;
    private final DigitalInput min, max;

    public Lifter(Victor lift1, Victor lift2, DigitalInput min, DigitalInput max) {
        this.lift1 = lift1;
        this.lift2 = lift2;
        this.min = min;
        this.max = max;
    }

    public void down() {// Goes down
        if (!min.get()) {// Onmly goes down if not at bottom
            lift1.set(speed);
            lift2.set(-speed);
        }
    }

    public void up() {// Goes up
        if (!min.get()) {//only goes up if not at top
            lift1.set(-speed);
            lift2.set(speed);
        }
    }

    public void tick() {

    }
}
