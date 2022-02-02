import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Anasayfa extends JFrame {

	private JPanel contentPane;
	private JTextField girdiad;
	private JTextField girditur;
	private JTextField girdisure;
	private JTextField girdiyonetmen;
	private JTextField girdikonu;
	private JLabel L2Fkonusu,FilmEkle;
	Baglanti baglan = new Baglanti();//
	private JLabel Filminadi, Filminturu, Filminsuresi, Filminyonetmeni, Filminkonusu;
	private List<film> Filmliste;// her yerde kullanabiliriz
	private int mevcutfilm = 0;// yansýttýðýmýz filmin indisini öðrenmek için tanýmladýk
	private Panel Filmeklepaneli;
	private JButton veriguncelle;// VERÝTABANI BUTONUNUN ÜSTÜNE YENÝ BÝR GÜNCELLE BUTONU KOYULDU,VAR OLAN FÝLMÝN
									// VERÝSÝNÝ GUNCELLEME ÝÞLEMÝ ÝÇÝN
	// BU BUTON GÜNCELLEME ÝÞLEMÝ YAPILDIÐI ZAMAN GÖZÜKÜCEK YOKSA VERÝTABANI
	// GÖZÜKÜCEK
	private JButton veritabanýButon;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Anasayfa frame = new Anasayfa(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Anasayfa(kullanýcý h) {
		setResizable(false);
		setTitle("FILM ARSIVI");// title isim
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 656, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		FilmEkle = new JLabel("F\u0130LM EKLE ");
		FilmEkle.setBackground(Color.BLACK);
		FilmEkle.setForeground(Color.WHITE);
		FilmEkle.setFont(new Font("Tahoma", Font.BOLD, 11));
		FilmEkle.setBounds(27, 66, 90, 14);
		

		Filmeklepaneli = new Panel();
		Filmeklepaneli.setBackground(Color.BLACK);
		Filmeklepaneli.setForeground(Color.DARK_GRAY);
		Filmeklepaneli.setBounds(27, 81, 255, 283);

		Filmeklepaneli.setLayout(null);
		if (h!=null&&h.Kad.equals("admin")) {// admin kullanýcýnýn eriþimine açýk olan özellikler
			contentPane.add(Filmeklepaneli);
			contentPane.add(FilmEkle);
		}


		JLabel L1Fadý = new JLabel("Filmin Ad\u0131 : ");
		L1Fadý.setForeground(Color.WHITE);
		L1Fadý.setBounds(10, 26, 71, 14);
		Filmeklepaneli.add(L1Fadý);

		JLabel L1Fturu = new JLabel("Filmin T\u00FCr\u00FC :");
		L1Fturu.setForeground(Color.WHITE);
		L1Fturu.setBounds(10, 51, 71, 14);
		Filmeklepaneli.add(L1Fturu);

		JLabel L1Fsuresi = new JLabel("Filmin S\u00FCresi : ");
		L1Fsuresi.setForeground(Color.WHITE);
		L1Fsuresi.setBounds(10, 76, 91, 14);
		Filmeklepaneli.add(L1Fsuresi);

		JLabel L1Fyonetmeni = new JLabel("Filmin Y\u00F6netmeni :");
		L1Fyonetmeni.setForeground(Color.WHITE);
		L1Fyonetmeni.setBounds(10, 104, 106, 14);
		Filmeklepaneli.add(L1Fyonetmeni);

		JLabel L1Fkonusu = new JLabel("Konusu :");
		L1Fkonusu.setForeground(Color.WHITE);
		L1Fkonusu.setBounds(10, 138, 71, 14);
		Filmeklepaneli.add(L1Fkonusu);

		girdiad = new JTextField();
		girdiad.setBounds(119, 23, 125, 20);
		Filmeklepaneli.add(girdiad);
		girdiad.setColumns(10);

		girditur = new JTextField();
		girditur.setColumns(10);
		girditur.setBounds(119, 48, 125, 20);
		Filmeklepaneli.add(girditur);

		girdisure = new JTextField();
		girdisure.setColumns(10);
		girdisure.setBounds(119, 73, 125, 20);
		Filmeklepaneli.add(girdisure);

		girdiyonetmen = new JTextField();
		girdiyonetmen.setColumns(10);
		girdiyonetmen.setBounds(119, 101, 125, 20);
		Filmeklepaneli.add(girdiyonetmen);

		girdikonu = new JTextField();
		girdikonu.setColumns(10);
		girdikonu.setBounds(119, 135, 125, 63);
		Filmeklepaneli.add(girdikonu);

		veritabanýButon = new JButton("VER\u0130TABANNA EKLE");
		veritabanýButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filmekle();
			}
		});
		veritabanýButon.setBackground(Color.BLACK);
		veritabanýButon.setForeground(Color.WHITE);
		veritabanýButon.setBounds(21, 229, 205, 32);
		Filmeklepaneli.add(veritabanýButon);

		veriguncelle = new JButton("GÜNCELLE");
		veriguncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guncelle();
			}
		});
		veriguncelle.setBounds(23, 217, 200, 23);
		veriguncelle.setBackground(Color.BLACK);
		veriguncelle.setForeground(Color.WHITE);
		veriguncelle.setBounds(23, 212, 205, 32);

		JLabel L2filmlistele = new JLabel("F\u0130LM L\u0130STELE");
		L2filmlistele.setForeground(Color.WHITE);
		L2filmlistele.setFont(new Font("Tahoma", Font.BOLD, 11));
		L2filmlistele.setBounds(335, 66, 83, 14);
		contentPane.add(L2filmlistele);

		Panel Filmlistelepaneli = new Panel();
		Filmlistelepaneli.setBackground(Color.BLACK);
		Filmlistelepaneli.setForeground(Color.DARK_GRAY);
		Filmlistelepaneli.setBounds(335, 81, 269, 283);
		contentPane.add(Filmlistelepaneli);
		Filmlistelepaneli.setLayout(null);

		JLabel L2Fadý = new JLabel("Filmin Ad\u0131 :");
		L2Fadý.setForeground(Color.WHITE);
		L2Fadý.setBounds(23, 27, 90, 14);
		Filmlistelepaneli.add(L2Fadý);

		JLabel L2Fturu = new JLabel("Filmin T\u00FCr\u00FC :");
		L2Fturu.setForeground(Color.WHITE);
		L2Fturu.setBounds(23, 52, 84, 14);
		Filmlistelepaneli.add(L2Fturu);

		JLabel L2Fsuresi = new JLabel("Filmin S\u00FCresi :");
		L2Fsuresi.setForeground(Color.WHITE);
		L2Fsuresi.setBounds(23, 77, 90, 14);
		Filmlistelepaneli.add(L2Fsuresi);

		JLabel L2Fyonetmeni = new JLabel("Filmin Y\u00F6netmeni :");
		L2Fyonetmeni.setForeground(Color.WHITE);
		L2Fyonetmeni.setBounds(23, 102, 109, 14);
		Filmlistelepaneli.add(L2Fyonetmeni);

		L2Fkonusu = new JLabel("Konusu :");
		L2Fkonusu.setForeground(Color.WHITE);
		L2Fkonusu.setBounds(23, 137, 84, 14);
		Filmlistelepaneli.add(L2Fkonusu);

		Filminadi = new JLabel("");
		Filminadi.setForeground(Color.WHITE);
		Filminadi.setBackground(Color.WHITE);
		Filminadi.setBounds(142, 27, 90, 14);
		Filmlistelepaneli.add(Filminadi);

		Filminturu = new JLabel("");
		Filminturu.setForeground(Color.WHITE);
		Filminturu.setBackground(Color.WHITE);
		Filminturu.setBounds(142, 52, 90, 14);
		Filmlistelepaneli.add(Filminturu);

		Filminsuresi = new JLabel("");
		Filminsuresi.setForeground(Color.WHITE);
		Filminsuresi.setBackground(Color.WHITE);
		Filminsuresi.setBounds(142, 77, 84, 14);
		Filmlistelepaneli.add(Filminsuresi);

		Filminyonetmeni = new JLabel("");
		Filminyonetmeni.setForeground(Color.WHITE);
		Filminyonetmeni.setBackground(Color.WHITE);
		Filminyonetmeni.setBounds(142, 102, 90, 14);
		Filmlistelepaneli.add(Filminyonetmeni);

		Filminkonusu = new JLabel("");
		Filminkonusu.setForeground(Color.WHITE);
		Filminkonusu.setBackground(Color.WHITE);
		Filminkonusu.setBounds(142, 137, 114, 71);
		Filmlistelepaneli.add(Filminkonusu);

		JButton ilkbuton = new JButton("<<ilk");
		ilkbuton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mevcutfilm = 0;
				yansit(Filmliste.get(mevcutfilm));
			}
		});
		ilkbuton.setBackground(Color.BLACK);
		ilkbuton.setForeground(Color.WHITE);
		ilkbuton.setBounds(10, 219, 65, 23);
		Filmlistelepaneli.add(ilkbuton);

		JButton butonsol = new JButton("<");
		butonsol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mevcutfilm--;
				if (mevcutfilm >= 0) {
					yansit(Filmliste.get(mevcutfilm));
				} else {
					mevcutfilm = Filmliste.size() - 1;
					yansit(Filmliste.get(mevcutfilm));
				}
			}
		});
		butonsol.setForeground(Color.WHITE);
		butonsol.setBackground(Color.BLACK);
		butonsol.setBounds(85, 219, 41, 23);
		Filmlistelepaneli.add(butonsol);

		JButton butonsag = new JButton(">");
		butonsag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mevcutfilm++;// diðer filme geçmek için 1 arttýkrýrk
				if (Filmliste.size() > mevcutfilm)

					yansit(Filmliste.get(mevcutfilm));
				else {
					mevcutfilm = 0;
					yansit(Filmliste.get(mevcutfilm));
				}

			}
		});
		butonsag.setForeground(Color.WHITE);
		butonsag.setBackground(Color.BLACK);
		butonsag.setBounds(128, 219, 41, 23);
		Filmlistelepaneli.add(butonsag);

		JButton sonbuton = new JButton("son>>");
		sonbuton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mevcutfilm = Filmliste.size() - 1;
				yansit(Filmliste.get(mevcutfilm));
			}
		});
		sonbuton.setForeground(Color.WHITE);
		sonbuton.setBackground(Color.BLACK);
		sonbuton.setBounds(179, 219, 77, 23);
		Filmlistelepaneli.add(sonbuton);
//-----------------------------------------------------------
		JButton silbuton = new JButton("S\u0130L");
		silbuton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (h!=null&&h.Kad.equals("admin"))
					sil();

			}
		});

		silbuton.setForeground(Color.WHITE);
		silbuton.setBackground(Color.BLACK);
		silbuton.setBounds(10, 253, 56, 23);
		Filmlistelepaneli.add(silbuton);
//------------------------------------------------------------------
		JButton guncellebuton = new JButton("G\u00DCNCELLE");
		guncellebuton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guncelleyansit();
			}
		});

		guncellebuton.setForeground(Color.WHITE);
		guncellebuton.setBackground(Color.BLACK);
		guncellebuton.setBounds(147, 253, 109, 23);
		Filmlistelepaneli.add(guncellebuton);
//------------------------------------------------------------------	
		JButton btnNewButton = new JButton("YEN\u0130LE");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listele();
			}
		});
		btnNewButton.setBounds(67, 253, 77, 23);
		Filmlistelepaneli.add(btnNewButton);

		// bu kýsmý projenin en sonuna ekrana tam boyut label alýndý
		ImageIcon Arkaplan = new ImageIcon("image/arkaplan-.jpg"); // arka plan için oluþturulan resim için

		JLabel arkaplan = new JLabel(Arkaplan); // çalýþtýr arkaplan
		arkaplan.setBounds(0, 0, 640, 421);
		contentPane.add(arkaplan);

		listele();// veritabanýndaki filmleri ekrana yansýtmak için ,geri getir (film getirir)

		
	}

	private void listele() {
		mevcutfilm = 0;
		Filmliste = baglan.listele();

		// System.out.println(Filmliste.get(0));

		if (Filmliste.size() != 0) {
			yansit(Filmliste.get(mevcutfilm));

		}

	}

	private void yansit(film f) {
		Filminadi.setText(f.ad);
		Filminturu.setText(f.tur);
		Filminsuresi.setText(f.sure);
		Filminyonetmeni.setText(f.yonetmen);
		Filminkonusu.setText(f.konu);

	}

	private void filmekle() {
		film f = verigetir();
		baglan.ekle(f);
	}

	private film verigetir() {// kullanýcýnýn girdiði verilerle film nesnesi oluþturduk
		return new film(0, girdiad.getText().toString(), // bilgileri gönderdik nesne içi dolduruldu
				girditur.getText().toString(), girdisure.getText().toString(), girdiyonetmen.getText().toString(),
				girdikonu.getText().toString());

	}

	private void guncelleyansit() {
		film f = Filmliste.get(mevcutfilm);
		Filmeklepaneli.add(veriguncelle);// veritabaný butonunu üsdtünde
		veritabanýButon.setVisible(false);// GÖZÜKMEMESÝ ÝÇÝN

		girdiad.setText(f.ad);
		girditur.setText(f.tur);
		girdisure.setText(f.sure);
		girdiyonetmen.setText(f.yonetmen);
		girdikonu.setText(f.konu);

	}

	private void guncelle() {
		film f = verigetir();
		f.id = Filmliste.get(mevcutfilm).id;
		baglan.guncelle(f);

	}

	private void sil() {
		int id = Filmliste.get(mevcutfilm).id;
		baglan.sil(id);
	}
}
