package ntlistener;

import java.io.IOException;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

class PiNetworkTableListener {
  private final NetworkTableInstance ntInst;

  public PiNetworkTableListener() {
    ntInst = NetworkTableInstance.getDefault();
    ntInst.startClientTeam(6763);
  }

  public static void main(final String[] args) {
    final PiNetworkTableListener piNetworkTableListener = new PiNetworkTableListener();
    piNetworkTableListener.run();
  }

  public void run() {
    final Runtime rt = Runtime.getRuntime();

    try {
      rt.exec("sudo java -jar -jar chameleon-vision.jar");
    } catch (final SecurityException | IOException | NullPointerException | IndexOutOfBoundsException e) {
      System.out.println("Starting Chameleon Vision failed: " + e.getMessage());
      return;
    }

    final NetworkTable cameraTable = ntInst.getTable("chameleon-vision").getSubTable("BallTracker");
    final NetworkTableEntry driverModeEntry = cameraTable.getEntry("driver_mode");
    final NetworkTable piTable = ntInst.getTable("pi-table");
    final NetworkTableEntry processingOnEntry = piTable.getEntry("on");

    while (true) {
      try {
        Thread.sleep(500);
      } catch (final InterruptedException ex) {
        System.out.println("interrupted");
        return;
      }

      // Get the newest 'processing on' value.
      final boolean on = processingOnEntry.getBoolean(false);
      System.out.println("Vision Processing Enabled: " + on);

      // Set the driver mode value.
      driverModeEntry.setBoolean(!on);
    }
  }
}
