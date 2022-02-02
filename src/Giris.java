import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Giris extends JFrame {

	private JPanel contentPane;
	private JTextField Gad;
	private JTextField Gsifre;
	private JTextField Kad;
	private JTextField Ksifre;
	private JTextField Kdogrula;
	private Baglanti baglan = new Baglanti();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Giris frame = new Giris();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Giris() {
		setTitle("G\u0130R\u0130S");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		JLabel LKullan�c� = new JLabel("        KULLANICI  G\u0130R\u0130\u015E\u0130");
		LKullan�c�.setFont(new Font("Tahoma", Font.BOLD, 11));
		LKullan�c�.setForeground(Color.WHITE);
		LKullan�c�.setBounds(25, 97, 148, 14);
		contentPane.add(LKullan�c�);

		JLabel LYkullan�c� = new JLabel("    YEN\u0130 KULLANICI");
		LYkullan�c�.setFont(new Font("Tahoma", Font.BOLD, 11));
		LYkullan�c�.setForeground(Color.WHITE);
		LYkullan�c�.setBounds(312, 97, 141, 14);
		contentPane.add(LYkullan�c�);

		Panel panel1 = new Panel();
		panel1.setBackground(Color.BLACK);
		panel1.setBounds(25, 117, 249, 281);
		contentPane.add(panel1);
		panel1.setLayout(null);

		JLabel emailable = new JLabel("       Kullan\u0131c\u0131 ad\u0131");
		emailable.setForeground(Color.WHITE);
		emailable.setBackground(Color.WHITE);
		emailable.setBounds(84, 46, 107, 14);
		panel1.add(emailable);

		Gad = new JTextField();
		Gad.setBounds(84, 71, 86, 20);
		panel1.add(Gad);
		Gad.setColumns(10);

		JLabel sifrelable = new JLabel("   \u015Eifre");
		sifrelable.setForeground(Color.WHITE);
		sifrelable.setBounds(105, 102, 46, 14);
		panel1.add(sifrelable);

		Gsifre = new JTextField();
		Gsifre.setBounds(84, 127, 86, 20);
		panel1.add(Gsifre);
		Gsifre.setColumns(10);

		JButton kullan�c�butonu = new JButton("G\u0130R\u0130\u015E YAP");
		kullan�c�butonu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onay();
			}
		});
		kullan�c�butonu.setForeground(Color.WHITE);
		kullan�c�butonu.setBackground(Color.BLACK);
		kullan�c�butonu.setBounds(59, 173, 132, 23);
		panel1.add(kullan�c�butonu);

		Panel panel2 = new Panel();
		panel2.setBackground(Color.BLACK);
		panel2.setBounds(312, 117, 238, 281);
		contentPane.add(panel2);
		panel2.setLayout(null);

		JLabel emailab = new JLabel("       Kullan\u0131c\u0131 ad\u0131 :");
		emailab.setForeground(Color.WHITE);
		emailab.setBounds(65, 23, 103, 14);
		panel2.add(emailab);

		JLabel sifrelab = new JLabel("           \u015Eifre :");
		sifrelab.setForeground(Color.WHITE);
		sifrelab.setBounds(65, 89, 93, 14);
		panel2.add(sifrelab);

		JLabel sifretekrarlab = new JLabel("\u015Eifreyi Do\u011Frula : ");
		sifretekrarlab.setForeground(Color.WHITE);
		sifretekrarlab.setBounds(75, 159, 93, 14);
		panel2.add(sifretekrarlab);

		Kad = new JTextField();
		Kad.setBounds(75, 48, 86, 20);
		panel2.add(Kad);
		Kad.setColumns(10);

		Ksifre = new JTextField();
		Ksifre.setBounds(75, 114, 86, 20);
		panel2.add(Ksifre);
		Ksifre.setColumns(10);

		Kdogrula = new JTextField();
		Kdogrula.setBounds(75, 191, 93, 20);
		panel2.add(Kdogrula);
		Kdogrula.setColumns(10);

		JButton kaydolbuton = new JButton("KAYDOL");
		kaydolbuton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HesapEkle();
			}
		});
		kaydolbuton.setBackground(Color.BLACK);
		kaydolbuton.setForeground(Color.WHITE);
		kaydolbuton.setBounds(65, 234, 120, 23);
		panel2.add(kaydolbuton);

		ImageIcon Arkaplan = new ImageIcon("image/arkaplan-.jpg");// ayn� arkaplan� kulland�k

		JLabel kullan�c�arkaplan = new JLabel(Arkaplan); // �ift t�rnak i�inde yaz�lm�yo
		kullan�c�arkaplan.setBounds(0, 0, 609, 408);
		contentPane.add(kullan�c�arkaplan);
	}

	private void HesapEkle() {
		kullan�c� h = veriCek();
		baglan.HesapEkle(h);
	}

	private kullan�c� veriCek() {
		kullan�c� h = new kullan�c�();
		if (!Kad.getText().toString().equals("") && !Ksifre.getText().toString().equals("")
				&& !Kdogrula.getText().toString().equals("")) {

			String gad = Kad.getText().toString();
			String gsifre = Ksifre.getText().toString();
			String gdogrula = Kdogrula.getText().toString();
			System.out.println(gad + gsifre + gdogrula);

			if (gdogrula.equals(gsifre)) {
				h.Kad = gad;
				h.Ksifre = gsifre;

				Kad.setText("");
				Ksifre.setText("");
				Kdogrula.setText("");
			}

		}

		return h;

	}

	private kullan�c� gvCek() {
		kullan�c� h = new kullan�c�();
		if (!Gad.getText().toString().equals("") && !Gsifre.getText().toString().equals("")) {
			String ad = Gad.getText().toString();
			String sifre = Gsifre.getText().toString();
			h.Kad = ad;
			h.Ksifre = sifre;
		}
		return h;
	}

	private void onay() {
		kullan�c� h = gvCek();
		kullan�c� onaylananHesap = baglan.girisOnayi(h);
		
		if(onaylananHesap!=null) {
			Anasayfa frame = new Anasayfa(onaylananHesap);
			frame.setVisible(true);
			dispose();
		}
	}

}
