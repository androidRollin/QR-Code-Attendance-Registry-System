import java.io.*;
public class QRCamera {
	QRCamera() throws IOException, InterruptedException {
		//String command = "cmd /c python \"C:\\Users\\ROLINE JOHN AGUILAR\\PycharmProject\\QRCODE\\read_qrcode.py\" ";
		String command = "cmd /c python \"C:\\Users\\ROLINE JOHN AGUILAR\\eclipse-workspace\\QRCODE\\src\\runOffWebC.py\" ";
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String line;
		while ((line = bri.readLine()) != null) {
			System.out.println(line);
		}
		bri.close();
		while((line = bre.readLine()) != null) {
			System.out.println(line);
		}
		bre.close();
		p.waitFor();
		//System.out.println("Done. ");
		p.destroy();
	}

}
