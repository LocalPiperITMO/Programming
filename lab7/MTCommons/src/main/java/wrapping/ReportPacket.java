package wrapping;

import java.io.Serializable;

public class ReportPacket implements Serializable {
    private final String receiverReport;

    public ReportPacket(String receiverReport) {
        this.receiverReport = receiverReport;
    }

    public String getReport() {
        return receiverReport;
    }


}
