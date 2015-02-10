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


import edu.wpi.first.wpilibj.Solenoid;
 
/**
 *
 * @author user
 */
public class Grabber implements Tickable{

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
    public void tick(){
        
    }

}
