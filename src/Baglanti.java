
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Baglanti {
	
	final  String url = "jdbc:sqlite:C://sqliteodev//filmarsivi.db";//dosya yolunu g�sterdik
	
	
	 private Connection connect() {
	        
	        Connection conn = null;//veritaban�na ba�lanma i�lemi
	        try {
	            conn = DriverManager.getConnection(url);
	            System.out.println("bagland�m");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	    }
	
	public  List<film> listele() {//film modeli olu�turduk listele metodu
		 Connection conn=connect();
		String sorgu = "select * from Film, Sureler,Turler,yonetmenler where Film.tur =Turler.id and Film.yonetmen = yonetmenler.id and Film.sure = Sureler.id";
		// * b�t�n verileri getirir ,tablolardaki de�erlerin tamaman�n� yazd�k
		List<film> liste = new ArrayList<film>();//arraylist olu�turduk 
		try {
			Statement st =  conn.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			while (rs.next()) {
				liste.add(new film(
						rs.getInt("id"),// film id
						rs.getString("ad"),//film ad�
						rs.getString("Ttur"),//turler tablosundan tur i�in
						rs.getString("Ssure"),//sureler tablosundan sure i�in
						rs.getString("Yad"),// yonetmenler tablosundan yonetmen ad� i�in
						rs.getString("Konu")//film konusu
						));
				
			}
			return liste;
		} catch (Exception e) {
			System.out.println("listeleme hatas�:"+e.getMessage());
			return null;
		}
		
	}
	 public void ekle(film f) {
	        String sorgu = "INSERT INTO Film(ad,tur,sure,yonetmen,konu) VALUES(?,?,?,?,?)";//soru i�aretlerine kar��l�k gelmesi ? bo�luk demek
	        		//ekleme i�lemi
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
	            pstmt.setString(1, f.ad);
	            pstmt.setInt(2, turicin(f.tur));//turun idsi
	            pstmt.setInt(3, sureicin(f.sure));//surenin id,si oldu�u i�in int
	            pstmt.setInt(4,yonet(f.yonetmen));//y�netmenin idsi
	            pstmt.setString(5, f.konu);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	
	 private int yonet(String ad) {//YONET Y�NETMENLER ���N 


			String sorgu = "SELECT * FROM yonetmenler WHERE Yad=?";//(ekleme alt�nda)y�netmenler tablosu i�in yonetmen ad� 
			int id = 0;

			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {

				pstmt.setString(1, ad);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {//varsa
					id = rs.getInt("id");//y�netmen varsa id yi id ye e�itlenecek
				}

			} catch (SQLException e) {
				System.out.println("yonetmen hatas�"+e.getMessage());

			}

			if (id == 0) {//e�er yonetmen tablomuzda yoksa yonetmen� ekl�yoruz
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
	 
	 
	 private int turicin(String tur) {//TURLER ���N


			String sorgu = "SELECT * FROM Turler WHERE Ttur=?";//TURLER L�STES�N�N TARADIK VE YAZILAN TUR VAR �SE �D� ALIDI
			int id = 0;//TURUN �DS� 

			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
				
				
				pstmt.setString(1, tur);//1=?
				
				ResultSet rs = pstmt.executeQuery();

				
				while (rs.next()) {
					id = rs.getInt("id");//TABLODA MEVCUTSA ,�DS�N� ALDIK
				}

			} catch (SQLException e) {
				System.out.println( "tur hatas�"    + e.getMessage());

			}

			if (id == 0) {//YOKSA �D DE�ER� 0 KALIR VE BURASI �ALI�IR 
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
	 private int sureicin(String sure) { //sure i�in de ayn� i�lemler yap�ld�
			String sorgu = "SELECT * FROM Sureler WHERE Ssure=?";
			int id = 0;

			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {

				pstmt.setString(1, sure);
				ResultSet rs = pstmt.executeQuery();

				
				while (rs.next()) {
					id = rs.getInt("id");
				}

			} catch (SQLException e) {
				System.out.println("sure hatas�"+e.getMessage());

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
	                + "WHERE id = ?";  // id si buysa di�er bilgileri �u �ekilde  guncelle 219/225
	        
	        
	       // System.out.println(f.id+f.ad+f.konu);// denemek i�in 

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

	   
	   public void HesapEkle(kullan�c� h) {
	        String sorgu = "INSERT INTO giris(Kad�,Ksifre) VALUES(?,?)";//soru i�aretlerine kar��l�k gelmesi ? bo�luk demek
	        		//ekleme i�lemi
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sorgu)) {
	            pstmt.setString(1,h.Kad );
	       
	            pstmt.setString(2, h.Ksifre);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	   public kullan�c� girisOnayi(kullan�c� h) {


			String sorgu = "select * from giris WHERE Kad�=? AND Ksifre=? ";
			kullan�c� onay = null;
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sorgu)) {

				// set the value
				pstmt.setString(1, h.Kad);
				pstmt.setString(2, h.Ksifre);
				//
				ResultSet rs = pstmt.executeQuery();

				// loop through the result set
				while (rs.next()) {
					onay = new kullan�c�(rs.getString("Kad�"),rs.getString("Ksifre"));
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			return onay;
		}

	   
}









