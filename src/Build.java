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

import java.io.File;
import java.io.IOException;
/**
 * This is a hack to get the big green button on NetBeans to deploy the project.
 * <p>
 * The big green button on the top of NetBeans is typically configured to run a
 * project. It does this by running a single file it recognizes as the 'main'
 * class. However, we want that button to deploy the project to the robot. The
 * deployment of the Robot project is done through an ANT build script, under
 * the target 'deploy'. NetBeans, sadly, does not allow configuration of that 
 * button to run an arbitrary build target. To get around that, this file is
 * designated as the 'main class', and, in a round-about way, runs the ANT build
 * target 'deploy'.
 * </p>
 * 
 * <p>
 * Unfortunately, the build.xml file provided by FIRST is rather rigid, and
 * does not contain any empty targets that may be safely overridden. Thus, there
 * are three solutions:
 * </p>
 * <ul>
 * <li>Modify FIRST's build.xml to ignore this file
 *      <p>
 *      This would work, but it would break as soon as that file was overwritten
 *      like in a wpilib update. It also does not port well</p></li>
 * <li>Copy the entire 'jar' target, with the one added exclude, to the overriding build
 *  file
 *      <p>
 *      Again, this would work, and it would port a bit better, however, while
 *      it is unlikely that the 'jar' logic would change, this method fails to
 *      compensate for wpilib updates which do change it.</p></li>
 * <li>Let the file compile and be deployed to the robot
 *      <p>
 *      This is not the cleanest solution, but it is the simplest and doesn't
 *      really do any harm. This is probably the best solution since it doesn't
 *      add any complexity or modify anything people might forget about.</p></li>
 * </ul>
 *
 * @author Dylan Frese
 */
public class Build {

    private static final String EXPLAINATION = "Cannot automatically deploy."
            + "\nPlease go to the files tab in Netbeans, navigate to the project"
            + " directory, and expand build.xml."
            + "\nThen, find the target 'deploy', in bold, and right click on it."
            + "\nSelect Run Target, and the code should start deployment."
            + "\nAlternatively, navigate to the build directory in a terminal,"
            + "\nand run 'ant deploy'.";    

    //Big green button will run this method. All we have to do is call ant.
    public static void main(String[] args) {
        //Get the working directory. Netbeans sets this to the project directory.
        //This saves us from having to hardcode paths.
        String projectPath = System.getProperty("user.dir");
        try {
            File projectDir = new File(projectPath);
            if (!projectDir.exists()) {
                throw new IOException("Working directory does not exist. Are you running from Netbeans?");
            }
            
            //We want to run the ant target 'deploy'
            Process ant = new ProcessBuilder("ant", "deploy")
                    .directory(projectDir)
                    //Pipe the streams to/from the parent process's streams
                    .inheritIO()
                    .start();
            ant.waitFor();
            
        } catch (IOException e) {
            System.err.println("IO Exception thrown with message: " + e.getMessage());
            giveUp();
        } catch (InterruptedException e) {
            System.err.println("Caught signal. Exiting.\n" + e.getMessage());
            giveUp();
        }
        System.out.println("done");
    }

    private static void giveUp() {
        System.err.println(EXPLAINATION);
        System.exit(1);
    }

}
