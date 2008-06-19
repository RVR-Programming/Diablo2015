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
 * An interface that defines an object that is able to be periodically invoked.
 * More precisely, this interface defines an object that has a method, 'tick'.
 * Such objects are able to be added to the list of Tickable objects in
 * {@link Robot} so that its tick method will be called periodically.
 * @author Dylan Frese
 */
public interface Tickable {
    /**
     * 'Ticks' this object. This method must be overrided in classes which
     * implement this interface to provide some method that would be reasonable
     * to periodically invoke. This method should not take a large amount of
     * time, such that it does not delay other tasks, and should not wait for
     * other tasks to be complete.
     */
    public void tick();
}
