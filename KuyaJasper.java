import java.awt.Image;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class KuyaJasper {
	static Connection connection = null;
	public static void main(String[] args) throws JRException {

		connection = db.dbConnector();
		String reportUrl = "/reports/Courses.jasper"; //path of your report source.
		InputStream reportFile = null;
		reportFile = KuyaJasper.class.getResourceAsStream(reportUrl);

		Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

		JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
		JasperViewer Jviewer = new JasperViewer(print, false);
		Jviewer.setVisible(true);

	}

}
