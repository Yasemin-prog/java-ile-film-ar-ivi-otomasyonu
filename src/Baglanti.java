
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Baglanti {
	
	final  String url = "jdbc:sqlite:C://sqliteodev//filmarsivi.db";//dosya yolunu gösterdik
	
	
	 private Connection connect() {
	        
	        Connection conn = null;//veritabanýna baðlanma iþlemi
	        try {
	            conn = DriverManager.getConnection(url);
	            System.out.println("baglandým");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	    }
	
	public  List<film> listele() {//film modeli oluþturduk listele metodu
		 Connection conn=connect();
		String sorgu = "select * from Film, Sureler,Turler,yonetmenler where Film.tur =Turler.id and Film.yonetmen = yonetmenler.id and Film.sure = Sureler.id";
		// * bütün verileri getirir ,tablolardaki deðerlerin tamamanýný yazdýk
		List<film> liste = new ArrayList<film>();//arraylist oluþturduk 
		try {
			Statement st =  conn.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			while (rs.next()) {
				liste.add(new film(
						rs.getInt("id"),// film id
						rs.getString("ad"),//film adý
						rs.getString("Ttur"),//turler tablosundan tur için
						rs.getString("Ssure"),//sureler tablosundan sure için
						rs.getString("Yad"),// yonetmenler tablosundan yonetmen adý için
						rs.getString("Konu")//film konusu
						));
				
			}
			return liste;
		} catch (Exception e) {
			System.out.println("listeleme hatasý:"+e.getMessage());
			return null;
		}
		
	}
	 public void ekle(film f) {
	        String sorgu = "INSERT INTO Film(ad,tur,sure,yonetmen,konu) VALUES(?,?,?,?,?)";//soru iþaretlerine karþýlýk gelmesi ? boþluk demek
	        		//ekleme iþlemi
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
	            pstmt.setString(1, f.ad);
	            pstmt.setInt(2, turicin(f.tur));//turun idsi
	            pstmt.setInt(3, sureicin(f.sure));//surenin id,si olduðu için int
	            pstmt.setInt(4,yonet(f.yonetmen));//yönetmenin idsi
	            pstmt.setString(5, f.konu);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	
	 private int yonet(String ad) {//YONET YÖNETMENLER ÝÇÝN 


			String sorgu = "SELECT * FROM yonetmenler WHERE Yad=?";//(ekleme altýnda)yönetmenler tablosu için yonetmen adý 
			int id = 0;

			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {

				pstmt.setString(1, ad);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {//varsa
					id = rs.getInt("id");//yönetmen varsa id yi id ye eþitlenecek
				}

			} catch (SQLException e) {
				System.out.println("yonetmen hatasý"+e.getMessage());

			}

			if (id == 0) {//eðer yonetmen tablomuzda yoksa yonetmený eklýyoruz
				String sorgu1 = "INSERT INTO yonetmenler(Yad) VALUES(?)";//yonetmen yonetmenler tablosuna eklendi
				try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu1)) {
					pstmt.setString(1, ad);
					pstmt.executeUpdate();
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}

				try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
					pstmt.setString(1, ad);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						id = rs.getInt("id");
					}

				} catch (SQLException e) {
					System.out.println(e.getMessage());

				}
			}
			return id;
		}
	 
	 
	 private int turicin(String tur) {//TURLER ÝÇÝN


			String sorgu = "SELECT * FROM Turler WHERE Ttur=?";//TURLER LÝSTESÝNÝN TARADIK VE YAZILAN TUR VAR ÝSE ÝDÝ ALIDI
			int id = 0;//TURUN ÝDSÝ 

			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
				
				
				pstmt.setString(1, tur);//1=?
				
				ResultSet rs = pstmt.executeQuery();

				
				while (rs.next()) {
					id = rs.getInt("id");//TABLODA MEVCUTSA ,ÝDSÝNÝ ALDIK
				}

			} catch (SQLException e) {
				System.out.println( "tur hatasý"    + e.getMessage());

			}

			if (id == 0) {//YOKSA ÝD DEÐERÝ 0 KALIR VE BURASI ÇALIÞIR 
				String sorgu1 = "INSERT INTO Turler(Ttur) VALUES(?)";// YOKSA EKLE, TURLER TABLOSUNA TUR EKLE 
				try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu1)) {
					pstmt.setString(1, tur);
					pstmt.executeUpdate();
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
				
				try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
					pstmt.setString(1, tur);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						id = rs.getInt("id");
					}

				} catch (SQLException e) {
					System.out.println(e.getMessage());

				}
			}
			return id;
		}
	 private int sureicin(String sure) { //sure için de ayný iþlemler yapýldý
			String sorgu = "SELECT * FROM Sureler WHERE Ssure=?";
			int id = 0;

			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {

				pstmt.setString(1, sure);
				ResultSet rs = pstmt.executeQuery();

				
				while (rs.next()) {
					id = rs.getInt("id");
				}

			} catch (SQLException e) {
				System.out.println("sure hatasý"+e.getMessage());

			}

			if (id == 0) {
				String sorgu1 = "INSERT INTO Sureler(Ssure) VALUES(?)";
				try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu1)) {
					pstmt.setString(1, sure);
					pstmt.executeUpdate();
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}

				try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
					pstmt.setString(1, sure);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						id = rs.getInt("id");
					}

				} catch (SQLException e) {
					System.out.println(e.getMessage());

				}
			}
			return id;
		}
	 
	 
	 public void guncelle(film f) {
	        String sorgu = "UPDATE Film SET "
	        		+ "ad = ? , "
	                + "tur = ? ,"
	                + "sure = ? ,"
	                + "yonetmen = ? , "
	                + "konu = ? "
	                + "WHERE id = ?";  // id si buysa diðer bilgileri þu þekilde  guncelle 219/225
	        
	        
	       // System.out.println(f.id+f.ad+f.konu);// denemek için 

	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
	            pstmt.setString(1, f.ad);
	            pstmt.setInt(2, turicin(f.tur));
	            pstmt.setInt(3,sureicin(f.sure));
	            pstmt.setInt(4,yonet(f.yonetmen));
	            pstmt.setString(5, f.konu);
	            pstmt.setInt(6, f.id);
	          
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	 
	   public void sil(int id) {
	        String sorgu = "DELETE FROM Film WHERE id = ?";

	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
	            pstmt.setInt(1, id);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }

	   
	   public void HesapEkle(kullanýcý h) {
	        String sorgu = "INSERT INTO giris(Kadý,Ksifre) VALUES(?,?)";//soru iþaretlerine karþýlýk gelmesi ? boþluk demek
	        		//ekleme iþlemi
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
	            pstmt.setString(1,h.Kad );
	       
	            pstmt.setString(2, h.Ksifre);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	   public kullanýcý girisOnayi(kullanýcý h) {


			String sorgu = "select * from giris WHERE Kadý=? AND Ksifre=? ";
			kullanýcý onay = null;
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {

				// set the value
				pstmt.setString(1, h.Kad);
				pstmt.setString(2, h.Ksifre);
				//
				ResultSet rs = pstmt.executeQuery();

				// loop through the result set
				while (rs.next()) {
					onay = new kullanýcý(rs.getString("Kadý"),rs.getString("Ksifre"));
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			return onay;
		}

	   
}









