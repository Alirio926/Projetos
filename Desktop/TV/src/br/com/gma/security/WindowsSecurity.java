/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.security;

/**
 *
 * @author Alirio
 */
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import javax.swing.JFrame;


public class WindowsSecurity implements Runnable 
{
  private JFrame frame;
  private boolean running;
  static GraphicsDevice device = GraphicsEnvironment
        .getLocalGraphicsEnvironment().getScreenDevices()[0];

  public WindowsSecurity(JFrame yourFrame)
  {
    this.frame = yourFrame;
    new Thread(this).start();
  }

  public void stop()
  {
     this.running = false;
  }

  @Override
  public void run() {
    try {
      device.setFullScreenWindow(frame);
      this.frame.setAlwaysOnTop(true);
      this.frame.setDefaultCloseOperation(0);
      //kill("explorer.exe"); // Kill explorer
      Robot robot = new Robot();
      int i = 0;
      running = false;
      while (running) {
         sleep(30L);
         focus();
         releaseKeys(robot);
         sleep(15L);
         focus();
         if (i++ % 10 == 0) {
             kill("taskmgr.exe");
         }
         focus();
         releaseKeys(robot);
      }
      //Runtime.getRuntime().exec("explorer.exe"); // Restart explorer
    } catch (Exception e) {

    }
  }

  private void releaseKeys(Robot robot) {
    robot.keyRelease(17);
    robot.keyRelease(18);
    robot.keyRelease(127);
    robot.keyRelease(524);
    robot.keyRelease(9);
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception e) {

    }
  }

  private void kill(String string) {
    try {
      Runtime.getRuntime().exec("taskkill /F /IM " + string).waitFor();
    } catch (Exception e) {
    }
  }

  private void focus() {
    this.frame.requestFocusInWindow();
    this.frame.requestFocus();
  }
}
