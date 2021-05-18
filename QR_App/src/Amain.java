import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class Amain implements ActionListener{
	
	JFrame fr = new JFrame("QR generator");
	JTextField stringtext = new JTextField(100);
	JButton gen = new JButton("Generate");
	JLabel in4 = new JLabel("String to encode");
	
	static String qrCodeText;
	static String fileType = "png";
	static int size = 250;
	
	String filePath = "D:/QR_Save";
	//static File qrFile = new File("D:/file test java/qrSave/QRoutput.png");
	static File qrFile = new File("D:/QR_Save/QRoutput.png");
	String readPath = "D:/QR_Save/QRoutput.png";
	
	public Amain() {
		stringtext.setBounds(135, 14, 250, 20);
		stringtext.setToolTipText("String to encode");
		
		in4.setBounds(25, 10, 150, 25);
		
		gen.setBounds(140, 50, 150, 25);
		gen.addActionListener(this);
		
		fr.setLayout(null);
		fr.add(stringtext);
		fr.add(in4);
		fr.add(gen);
		fr.setBackground(Color.BLACK);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(450, 130);
		fr.setLocationRelativeTo(null);
		fr.setResizable(false);
		fr.setVisible(true);
		writeFile(filePath);
	}
	
	public static void main(String[] args) throws WriterException, IOException {
		Amain a = new Amain();
	}
	
	public static void creQRI(File qrFile, String qrCodeText, int size, String fileType) throws WriterException, IOException {
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix Matrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		int matrixW = Matrix.getWidth();
		BufferedImage image = new BufferedImage(matrixW, matrixW, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		
		Graphics2D grp2 = (Graphics2D) image.getGraphics();
		grp2.setColor(Color.WHITE);
		grp2.fillRect(0, 0, matrixW, matrixW);
		grp2.setColor(Color.BLACK);
		
		for (int i = 0; i < matrixW; i++) {
			for (int j = 0; j < matrixW; j++) {
				if (Matrix.get(i, j)) {
					grp2.fillRect(i, j, 1, 1);
				}
			}
		}
		try {
			ImageIO.write(image, fileType, qrFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
		
	}
	
	public void writeFile(String value) {
		File savePos = new File(value);
		if (savePos.exists()) {
			System.out.println("Save Location Checked!");
		}else {
			File savePosition = new File(value); 
			System.out.print("created folder " + "QR_Save");

		}
	}

	public void createAndShow(ActionEvent e) {
		if(e.getSource() == gen) {
			qrCodeText = stringtext.getText();
			if(stringtext.getText() == null) {
				JOptionPane.showMessageDialog(null, "TEXT ENTERED ERROR");
			}
			try {
				creQRI(qrFile, qrCodeText, size, fileType);
				System.out.println(qrCodeText);
			} catch (WriterException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "TEXT ENTERED ERROR");
			}
			catch(IOException e2) {
				e2.printStackTrace();
				JOptionPane.showConfirmDialog(null, "CAN'T WRITE FILE");
			}
			
			
		JFrame show = new JFrame("QR Code");
		JPanel pn = new JPanel();
		try {
			final BufferedImage img = ImageIO.read(new File(readPath));
			JLabel lb = new JLabel(new ImageIcon(img));
			pn.add(lb);
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "QR CODE GENERATED NOT FOUND");
		}
		
		show.getRootPane().setBackground(Color.BLACK);
		show.add(pn);
		show.pack();  
		show.setBackground(Color.BLACK);
		show.setLocationRelativeTo(fr);
		show.setVisible(true);
		
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			createAndShow(e);
		
	}


}
