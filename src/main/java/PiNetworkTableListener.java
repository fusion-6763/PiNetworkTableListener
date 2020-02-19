package ntlistener;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

class PiNetworkTableListener {
  private NetworkTableInstance ntInst;

  public PiNetworkTableListener() {
    ntInst = NetworkTableInstance.getDefault();
    ntInst.startClientTeam(6763);
  }

  public static void main(String[] args) {
    final PiNetworkTableListener piNetworkTableListener = new PiNetworkTableListener();
    piNetworkTableListener.run();
  }

  public void run() {
    final NetworkTable table = ntInst.getTable("pi-table");
    final NetworkTableEntry processingOnEntry = table.getEntry("on");
    ntInst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
    while (true) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException ex) {
        System.out.println("interrupted");
        return;
      }
      boolean on = processingOnEntry.getBoolean(false);
      System.out.println("Vision Processing Enabled: " + on);
      // TODO: Toggle the chameleon vision command here.
    }
  }
}
