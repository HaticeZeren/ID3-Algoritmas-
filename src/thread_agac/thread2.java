package thread_agac;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;
import java.util.ListIterator;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;



//import agac.agac.Dugum;

//import agac.agac.Dugum;

   public class thread2  extends Frame{
	//private static Vector<Integer> tablo;
	private static Vector<String> tablo;
	private static Vector<Double> kazanc;
	private static Vector<Double> tablo_kazanc;
	private static Vector<String> tablo_bol;
	private static Vector<String> tablo_bol1;
	private static Vector<String> tablo_bol2;
	private static int tablo_satir;
	private static int tablo_sutun;
	private static float ort_entropi;
	public static Dugum agac;
	public thread2() throws IOException {
		
		//tablo=new Vector<Integer>();
		tablo=new Vector<String>();
		kazanc=new Vector<Double>();
		tablo_kazanc=new Vector<Double>();
		tablo_sutun=0;
		tablo_satir=0;
		ort_entropi=0;
		dosya_oku();
        ort_entropi=sinif_entropi(0,tablo,tablo_sutun,tablo_satir);
		bolumleme1 dene=new bolumleme1(tablo, tablo_satir, tablo_sutun,50,0);
		bolumleme1 dene2=new bolumleme1(tablo, tablo_satir, tablo_sutun,62,1);
		bolumleme1 dene3=new bolumleme1(tablo, tablo_satir, tablo_sutun,5,2);
		dene.start();
		dene2.start();
		dene3.start();
		
		
		bolumleme2 dene4=new bolumleme2(tablo, tablo_satir, tablo_sutun,60,0);
		bolumleme2 dene5=new bolumleme2(tablo, tablo_satir, tablo_sutun,63,1);
		bolumleme2 dene6=new bolumleme2(tablo, tablo_satir, tablo_sutun,10,2);
		dene4.start();
		dene5.start();
		dene6.start();
		
		
		bolumleme3 dene7=new bolumleme3(tablo, tablo_satir, tablo_sutun,70,0);
		bolumleme3 dene8=new bolumleme3(tablo, tablo_satir, tablo_sutun,64,1);
		bolumleme3 dene9=new bolumleme3(tablo, tablo_satir, tablo_sutun,19,2);
		dene7.start();
		dene8.start();
		dene9.start();
		
		addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
			     dispose();
			     System.exit(0);
			   }
			 });
		 setLayout(null);
		 
		 
		 
	}
	
	private static void yaz(){
		int sayac=0;
		for(int i=0;i<tablo.size();i++){
			if(sayac%tablo_sutun==0 ) System.out.println();
			System.out.print(tablo.get(i)+",");
			sayac++;
		}
	}
	private void dosya_oku() throws IOException{
		File file = new File("haberman.txt");
		FileReader fileReader = new FileReader(file);
		String line;

		BufferedReader br = new BufferedReader(fileReader);

		while ((line = br.readLine()) != null) {

		    String[] kelime=line.split(",");
            int_cevir_vec_ata(kelime);
            tablo_satir++;
		}

		br.close();
	}
	
	private void int_cevir_vec_ata(String[] satir){
		tablo_sutun=satir.length;
		for(int i=0;i<satir.length;i++){
			 // tablo.add(Integer.parseInt(satir[i]));
			tablo.add(satir[i]);
		}
	}

	public Vector<String> kac_tane_kactan(Vector<String> tablo,int sutun,int satir){
		/**
		 * gonderilen satýr sutun sayýsý belli tablonun etiket kýsmýndaki  hangi deðiþkenlerin kaç kere tekrar ettiðini bulur.
		 * */
		Vector<String> nitelikler=new Vector<String>();
		for(int i=0;i<satir;i++){
			String eleman=tablo.get(i*sutun+sutun-1);
		//	System.out.println("eleman:"+eleman);
		//	System.out.println("sutun:"+sutun);
			if(!nitelikler.contains(eleman)){
				nitelikler.add(eleman);
				int say=0;
				for(int j=0;j<satir;j++){
					if(eleman.equals(tablo.get(j*sutun+sutun-1))){
						say++;
					}
				}
				nitelikler.add(Integer.toString(say));
			}
		}
		
		
		return nitelikler;
	}
	public float P(float tekrar,int satir_sayisi){
		return tekrar/satir_sayisi;
	}
	public float sinif_entropi(float entropi,Vector<String> tablo,int tablo_sutun,int satir){
		Vector<String> nitelikler=kac_tane_kactan(tablo,tablo_sutun,satir);
        for(int i=0;i<nitelikler.size();i++){
        	//System.out.println("nitelikler.get(i+1)"+nitelikler.get(i+1));
        	float tekrar=Float.parseFloat(nitelikler.get(i+1));
        	//System.out.println(nitelikler.get(i)+". sayinin tekrari:"+Double.parseDouble(nitelikler.get(i+1)));

        	float olasilik=P(tekrar,satir);
        	entropi+=olasilik*(Math.log(olasilik)/Math.log(2));
        	i++;
           }
		return -1*entropi;
	}
	
	public void kazanc_hesapla(Vector<String> tablo,int hangi_sutun,int aralik){
		
		Vector<String> sinif_bag_tablo=new Vector<String>();
		Vector<String> yeni_tablo=new Vector<String>();	
		//for(int i=0;i<tablo_sutun-1;i++){
			float bagli_entr=0;
			for(int j=0;j<tablo_satir;j++){
				String eleman=tablo.get(j*tablo_sutun+hangi_sutun);
				if(!sinif_bag_tablo.contains(eleman)){
					sinif_bag_tablo.add(eleman);
					int say=0;
					for(int k=0;k<tablo_satir;k++){
						if(eleman.equals(tablo.get(k*tablo_sutun+hangi_sutun))){
							yeni_tablo.add(tablo.get(k*tablo_sutun+tablo_sutun-1));
							say++;
						}
					}
					float H=0;
					//System.out.println(eleman+"için sinifa baglý degerler :"+"say:"+say);
					/*
					for(int m=0;m<yeni_tablo.size();m++){
						System.out.println(yeni_tablo.get(m));
					}
					*/
					H=sinif_entropi(H,yeni_tablo,1,say);
					//System.out.println(eleman +"icin H:"+H);
					bagli_entr+=((float)say/(float)tablo_satir)*H;
					//System.out.println("bagli entropi :"+bagli_entr);
					yeni_tablo.clear();
				}

			}
			sinif_bag_tablo.clear();
			//System.out.println("ort_ent:"+ort_entropi);
			//System.out.println("bagli_ent:"+bagli_entr);
		
			//float kazanc=(ort_entropi-bagli_entr);
			
			double deger=ort_entropi-bagli_entr;
			
            kazanc.add((double) hangi_sutun);
            kazanc.add((double) aralik);
			kazanc.add(deger);
		//}
	}
	
	//********************************
public int tablo_kazanc_hesapla(Vector<String> tablo,int tablo_sutun,int tablo_satir,float sinif_entropi){
		
		Vector<String> sinif_bag_tablo=new Vector<String>();
		Vector<String> yeni_tablo=new Vector<String>();	
		for(int i=0;i<tablo_sutun-1;i++){
			float bagli_entr=0;
			for(int j=0;j<tablo_satir;j++){
				String eleman=tablo.get(j*tablo_sutun+i);
				if(!sinif_bag_tablo.contains(eleman)){
					sinif_bag_tablo.add(eleman);
					int say=0;
					for(int k=0;k<tablo_satir;k++){
						if(eleman.equals(tablo.get(k*tablo_sutun+i))){
							yeni_tablo.add(tablo.get(k*tablo_sutun+tablo_sutun-1));
							say++;
						}
					}
					float H=0;
					//System.out.println(eleman+"için sinifa baglý degerler :"+"say:"+say);
					/*
					for(int m=0;m<yeni_tablo.size();m++){
						System.out.println(yeni_tablo.get(m));
					}
					*/
					H=sinif_entropi(H,yeni_tablo,1,say);
					//System.out.println(eleman +"icin H:"+H);
					bagli_entr+=((float)say/(float)tablo_satir)*H;
					//System.out.println("bagli entropi :"+bagli_entr);
					yeni_tablo.clear();
				}

			}
			sinif_bag_tablo.clear();
			//System.out.println("ort_ent:"+ort_entropi);
			//System.out.println("bagli_ent:"+bagli_entr);
		
			//float kazanc=(ort_entropi-bagli_entr);
			
			double deger=sinif_entropi-bagli_entr;
			tablo_kazanc.add(deger);
		}
		
		double enb_kazanc=-1;
		int secilen_sutun=-1;
		for(int i=0;i<tablo_kazanc.size();i++){
			if(enb_kazanc<tablo_kazanc.get(i)){
				enb_kazanc=tablo_kazanc.get(i);
				secilen_sutun=i;
			}
		}
		/*
		 System.out.println("************KAZANÇLAR*********************************************************");
		    for(int i=0;i<tablo_kazanc.size();i++){
		    	System.out.format("kazanc: %4f%n",tablo_kazanc.get(i));
		    }
		    */
		   tablo_kazanc.clear(); 
		  // System.out.println("sutun"+secilen_sutun);
		   return secilen_sutun;
	}
	
	//*********************************
	public class bolumleme1 extends Thread {
		 
			private int  aralik;
			//private double enb_kazanc;
			//private int secilen_sinir;
			private int hangi_sutun;
			private int tablo_satir;
			private int tablo_sutun;
			public bolumleme1(Vector<String> tablo,int tablo_satir,int tablo_sutun,int sinir,int hangi_sutun){
				Vector<String> clone = (Vector<String>)tablo.clone();
				tablo_bol=clone;
				
				aralik=sinir;
				this.tablo_satir=tablo_satir;
				this.tablo_sutun=tablo_sutun;
				this.hangi_sutun=hangi_sutun;
			}
		
			public void  yaz(){
				for(int i=0;i<tablo_satir;i++){
					for(int j=0;j<tablo_sutun;j++){
						System.out.print(tablo_bol.get(i*tablo_sutun+j)+"  ");
					}
					System.out.println();
				}
			}
			
			 synchronized public void run() {
				ReentrantLock kilit=new ReentrantLock();
				kilit.lock();
				 try {
					//sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}
				 for(int i=0;i<tablo_satir;i++){
					 if(Double.parseDouble(tablo_bol.get(i*tablo_sutun+hangi_sutun))<aralik){
						 tablo_bol.set(i*tablo_sutun+hangi_sutun,"<"+aralik);
						// System.out.println("if kýsmýnda"+getName());
						  // System.out.println(getName());
						 //System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+"<"+aralik);
					 }
					 else{
						 tablo_bol.set(i*tablo_sutun+hangi_sutun,">="+aralik);
						// System.out.println("else kýsmýnda"+getName());
						// System.out.println(getName());
						// System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+">"+aralik);
					 }
				 }
				// System.out.println("entropi kýsmýnda"+getName());
				 
				 //System.out.println("ort_entropi:"+ort_entropi);
				 kazanc_hesapla(tablo_bol,hangi_sutun,aralik);
				//kazanc_hesapla(tablo_bol,ort_entropi,hangi_sutun);
				//kazanc_hesapla(tablo_bol,ort_entropi,hangi_sutun);
				 kilit.unlock();
				 
				
				//yaz();
				 
				 /*
				 double ort_entropi=0;
			        ort_entropi=sinif_entropi(ort_entropi,tablo_bol,tablo_sutun,tablo_satir);
				    System.out.println("ort_entropi:"+ort_entropi);	
				    
				    
				    kazanc_hesapla(ort_entropi,0);
				    
				    for(int i=0;i<kazanc.size();i++){
				    	System.out.println(kazanc.get(i));
				    }
			    // super.run();
			     
			     */
			}
			
		
		
	}
	
	
	
	//********************************
	
	public class bolumleme2 extends Thread {
		 
		private int  aralik;
		//private double enb_kazanc;
		//private int secilen_sinir;
		private int hangi_sutun;
		private int tablo_satir;
		private int tablo_sutun;
		public bolumleme2(Vector<String> tablo,int tablo_satir,int tablo_sutun,int sinir,int hangi_sutun){
			
			tablo_bol1=(Vector<String>)tablo.clone();
			aralik=sinir;
			this.tablo_satir=tablo_satir;
			this.tablo_sutun=tablo_sutun;
			this.hangi_sutun=hangi_sutun;
		}
	
		public void  yaz(){
			for(int i=0;i<tablo_satir;i++){
				for(int j=0;j<tablo_sutun;j++){
					System.out.print(tablo_bol1.get(i*tablo_sutun+j)+"  ");
				}
				System.out.println();
			}
		}
		
		 synchronized public void run() {
			ReentrantLock kilit=new ReentrantLock();
			kilit.lock();
			 try {
				//sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
			 for(int i=0;i<tablo_satir;i++){
				 if(Double.parseDouble(tablo_bol1.get(i*tablo_sutun+hangi_sutun))<aralik){
					 tablo_bol1.set(i*tablo_sutun+hangi_sutun,"<"+aralik);
					// System.out.println("if kýsmýnda"+getName());
					  // System.out.println(getName());
					 //System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+"<"+aralik);
				 }
				 else{
					 tablo_bol1.set(i*tablo_sutun+hangi_sutun,">="+aralik);
					// System.out.println("else kýsmýnda"+getName());
					// System.out.println(getName());
					// System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+">"+aralik);
				 }
			 }
			// System.out.println("entropi kýsmýnda"+getName());
			 
			 //System.out.println("ort_entropi:"+ort_entropi);	
			 kazanc_hesapla(tablo_bol1,hangi_sutun,aralik);
			//kazanc_hesapla(tablo_bol,ort_entropi,hangi_sutun);
			//kazanc_hesapla(tablo_bol,ort_entropi,hangi_sutun);
			 kilit.unlock();
			 
			
			//yaz();
			 
			 /*
			 double ort_entropi=0;
		        ort_entropi=sinif_entropi(ort_entropi,tablo_bol,tablo_sutun,tablo_satir);
			    System.out.println("ort_entropi:"+ort_entropi);	
			    
			    
			    kazanc_hesapla(ort_entropi,0);
			    
			    for(int i=0;i<kazanc.size();i++){
			    	System.out.println(kazanc.get(i));
			    }
		    // super.run();
		     
		     */
		}
		
	
	
}

	
	
	//********************************
    
	
	public class bolumleme3 extends Thread {
		 
		private int  aralik;
		//private double enb_kazanc;
		//private int secilen_sinir;
		private int hangi_sutun;
		private int tablo_satir;
		private int tablo_sutun;
		public bolumleme3(Vector<String> tablo,int tablo_satir,int tablo_sutun,int sinir,int hangi_sutun){
			
			tablo_bol2=(Vector<String>)tablo.clone();
			aralik=sinir;
			this.tablo_satir=tablo_satir;
			this.tablo_sutun=tablo_sutun;
			this.hangi_sutun=hangi_sutun;
		}
	
		public void  yaz(){
			for(int i=0;i<tablo_satir;i++){
				for(int j=0;j<tablo_sutun;j++){
					System.out.print(tablo_bol2.get(i*tablo_sutun+j)+"  ");
				}
				System.out.println();
			}
		}
		
		 synchronized public void run() {
			ReentrantLock kilit=new ReentrantLock();
			kilit.lock();
			 try {
				//sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
			 for(int i=0;i<tablo_satir;i++){
				 if(Double.parseDouble(tablo_bol2.get(i*tablo_sutun+hangi_sutun))<aralik){
					 tablo_bol2.set(i*tablo_sutun+hangi_sutun,"<"+aralik);
					// System.out.println("if kýsmýnda"+getName());
					  // System.out.println(getName());
					 //System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+"<"+aralik);
				 }
				 else{
					 tablo_bol2.set(i*tablo_sutun+hangi_sutun,">="+aralik);
					// System.out.println("else kýsmýnda"+getName());
					// System.out.println(getName());
					// System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+">"+aralik);
				 }
			 }
			// System.out.println("entropi kýsmýnda"+getName());
			 
			 //System.out.println("ort_entropi:"+ort_entropi);	
			 kazanc_hesapla(tablo_bol2,hangi_sutun,aralik);
			//kazanc_hesapla(tablo_bol,ort_entropi,hangi_sutun);
			//kazanc_hesapla(tablo_bol,ort_entropi,hangi_sutun);
			 kilit.unlock();
			 
			
			//yaz();
			 
			 /*
			 double ort_entropi=0;
		        ort_entropi=sinif_entropi(ort_entropi,tablo_bol,tablo_sutun,tablo_satir);
			    System.out.println("ort_entropi:"+ort_entropi);	
			    
			    
			    kazanc_hesapla(ort_entropi,0);
			    
			    for(int i=0;i<kazanc.size();i++){
			    	System.out.println(kazanc.get(i));
			    }
		    // super.run();
		     
		     */
		}
		
	
	
}
	//**************
	public void tabloyu_bol(double sutun,double aralik){
		for(int i=0;i<tablo_satir;i++){
			 if(Double.parseDouble(tablo.get(i*tablo_sutun+(int)sutun))<aralik){
				 tablo.set(i*tablo_sutun+(int)sutun,"<"+aralik);
				// System.out.println("if kýsmýnda"+getName());
				  // System.out.println(getName());
				 //System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+"<"+aralik);
			 }
			 else{
				 tablo.set(i*tablo_sutun+(int)sutun,">="+aralik);
				// System.out.println("else kýsmýnda"+getName());
				// System.out.println(getName());
				// System.out.println(i+"*"+tablo_sutun+"+"+hangi_sutun+">"+aralik);
			 }
		 }
	}
	//**********************************
	public void en_iyi_tablo(){
		double enb_kazanc1=0;
		double hangi_sutun1=0;
		double hangi_aralik1=0;
		
		
		
			   for(int sutun=0;sutun<3;sutun++){
				for(int i=0;i<kazanc.size()/3;i++){
				if(sutun==kazanc.get(i*3+0)){
					 if(enb_kazanc1<=kazanc.get(i*3+2)){
						 hangi_sutun1=sutun;
						 hangi_aralik1=kazanc.get(3*i+1);
						 enb_kazanc1=kazanc.get(3*i+2);
					 }
				}
			}
			tabloyu_bol(hangi_sutun1,hangi_aralik1);
			  enb_kazanc1=0;
			  hangi_sutun1=0;
			  hangi_aralik1=0;
		}
		
		kazanc.clear();
	}
	//**********************************
	public int tabloyu_kirp(Vector<String> yeni_tablo,Vector<String> tablo,int tab_sutun,int tab_satir,int secilen_sutun,String degisken){
		//Vector<String> tablo1=(Vector<String>) tablo.clone();
		int yeni_satir=0;
		int yeni_sutun=0;
		for(int satir=0;satir<tab_satir;satir++){
			if(tablo.get(satir*tab_sutun+secilen_sutun).equals(degisken)){
				 for(int j=0;j<tab_sutun;j++){
					 if(j!=secilen_sutun) yeni_tablo.add(tablo.get(satir*tab_sutun+j));
					 
				 }
				 yeni_satir++;
				
			}
			
		}
		
		yeni_sutun=tab_sutun-1;
		
		
		
		
		return yeni_satir;
		
		}
	//**********************************
	
	//**********************************
	
	class Dugum{
		Vector<String> tablo;
		Vector<String> etiket;
		 int tablo_satir;
		 int tablo_sutun; 
		int secilen_sutun;
		String degisken;
		float sinif_entropisi;
		Dugum sag;
		Dugum sol;
		public Dugum(int tablo_satir,int tablo_sutun){
			 tablo=new Vector<String>();
			 this.tablo_satir=tablo_satir;
			 this.tablo_sutun=tablo_sutun;
		}
	}
	//*********************************
	
	//**********************************
	public void basla(){
		
		Dugum kok=new Dugum(tablo_satir,tablo_sutun);
		kok.tablo=(Vector<String>) tablo.clone();
		float sinif_entropi=sinif_entropi(0,kok.tablo,kok.tablo_sutun, kok.tablo_satir);
		int secilen_sutun=tablo_kazanc_hesapla(kok.tablo,kok.tablo_sutun,kok.tablo_satir, sinif_entropi);
		kok.secilen_sutun=secilen_sutun;
		kok.sinif_entropisi=sinif_entropi;
		kok.degisken=Integer.toString(secilen_sutun)+".sutun";
		//kok.degisken=null;
		kok.etiket=null;
		 for(int i=0;i<tablo_satir;i++){
				for(int j=0;j<tablo_sutun;j++){
					System.out.print(tablo.get(i*tablo_sutun+j)+"  ");
				}
				System.out.println();
			}
		ekle(kok);
		//preorder(kok);
		agac=kok;
		/*
		  Vector<String> yeni_tablo=new Vector<String>();
		    int yeni_satir=tabloyu_kirp(yeni_tablo,kok.tablo,kok.tablo_sutun,kok.tablo_satir,kok.secilen_sutun,kok.degisken);
		    int yeni_sutun=tablo_sutun-1;
		    */
	}
	
	//**********************************
	
	public Vector<String> degiskenleri_bul(Dugum dugum){
		Vector<String>liste=new Vector<String>();
		String ilk=dugum.tablo.get(dugum.secilen_sutun);
		String ikinci = null;
		for(int i=0;i<dugum.tablo_satir;i++){
			if(!dugum.tablo.get(dugum.tablo_sutun*i+dugum.secilen_sutun).equals(ilk)){
				ikinci=dugum.tablo.get(dugum.tablo_sutun*i+dugum.secilen_sutun);
				break;
			}
		}
		liste.add(ilk);
		liste.add(ikinci);
		return liste;
	}
	//**********************************
	public Vector<String> etiket_bul(Dugum dugum){
		Vector<String>etiket=new Vector<String>();
		String ilk=dugum.tablo.get(dugum.tablo_sutun-1);
		String ikinci = null;
		for(int i=0;i<dugum.tablo_satir;i++){
			if(!dugum.tablo.get((dugum.tablo_sutun)*i+(dugum.tablo_sutun-1)).equals(ilk)){
				ikinci=dugum.tablo.get(dugum.tablo_sutun*i+(dugum.tablo_sutun-1));
				break;
			}
		}
		etiket.add(ilk);
		etiket.add(ikinci);
		return etiket;
	}
	
	//**********************************
	   class agacta_thread1 extends Thread{
		   private Dugum dugum;
		   private String degisken;
		   public agacta_thread1(Dugum kok,String degisken){
			   dugum=kok;
			   this.degisken=degisken;
		   }
		   
		   public void elemanlari_ekle(){
			   Vector<String> yeni_tablo=new Vector<String>();
				 int yeni_satir1=tabloyu_kirp(yeni_tablo,dugum.tablo,dugum.tablo_sutun,dugum.tablo_satir,dugum.secilen_sutun,degisken);
				 int yeni_sutun1=dugum.tablo_sutun-1;
				 dugum.sol=new Dugum(yeni_satir1,yeni_sutun1);
				 dugum.sol.tablo=yeni_tablo;
				 
				 if(yeni_sutun1==1) {
					 dugum.sol=null;
					 Vector<String> etiket=etiket_bul(dugum);
					 dugum.etiket=etiket;
					 System.out.println("etiket degeri sifirlanmadi 1 ve 2 ye geçiþ var.");
				 }
				 else{
				 float  sinif_entropi=sinif_entropi(0,yeni_tablo,yeni_sutun1,yeni_satir1);
				 int secilen_sutun=tablo_kazanc_hesapla(yeni_tablo,yeni_sutun1,yeni_satir1, sinif_entropi);
					dugum.sol.secilen_sutun=secilen_sutun;
					dugum.sol.sinif_entropisi=sinif_entropi;
					dugum.sol.degisken=degisken;
					dugum.sol.etiket=null;
					System.out.println(dugum.degisken+"  dugumunun soluna "+degisken + "eklendi");
					System.out.println(degisken+"degerinin entropisi:"+dugum.sol.sinif_entropisi);
					//System.out.println("tablo_satir:"+dugum.sol.tablo_satir);
					//System.out.println("tablo_sutun:"+dugum.sol.tablo_sutun);
				 }
		   }
		   
		   @Override
		public void run() {
			elemanlari_ekle();
		}
	   }
	
	
	//**********************************
	   class agacta_thread2 extends Thread{
		   private Dugum dugum;
		   private String degisken;
		   public agacta_thread2(Dugum kok,String degisken){
			   dugum=kok;
			   this.degisken=degisken;
		   }
		   
		   public void elemanlari_ekle(){
			   
			   Vector<String> yeni_tablo2=new Vector<String>();
				 int yeni_satir2=tabloyu_kirp(yeni_tablo2,dugum.tablo,dugum.tablo_sutun,dugum.tablo_satir,dugum.secilen_sutun,degisken);
				 int yeni_sutun2=dugum.tablo_sutun-1;	 
				 dugum.sag=new Dugum(yeni_satir2,yeni_sutun2);
				 dugum.sag.tablo=yeni_tablo2;
				 if(yeni_sutun2==1) {
					 dugum.sag=null;
					 Vector<String> etiket=etiket_bul(dugum);
					 dugum.etiket=etiket;
					 System.out.println("etiket degeri sifirlanmadi 1 ve 2 ye geçiþ var.");
				 }
				 else{
				 float sinif_entropi2=sinif_entropi(0,yeni_tablo2,yeni_sutun2,yeni_satir2);
				 int secilen_sutun2=tablo_kazanc_hesapla(yeni_tablo2,yeni_sutun2,yeni_satir2, sinif_entropi2);
					dugum.sag.secilen_sutun=secilen_sutun2;
					dugum.sag.sinif_entropisi=sinif_entropi2;
					dugum.sag.degisken=degisken;
					dugum.sag.etiket=null;
					System.out.println(dugum.degisken+"  dugumunun sagýna "+degisken + "eklendi");
					System.out.println(degisken+"degerinin entropisi:"+dugum.sag.sinif_entropisi);
					//System.out.println("tablo_satir:"+dugum.sag.tablo_satir);
					//System.out.println("tablo_sutun:"+dugum.sag.tablo_sutun);
				 }    
		       }
		   
		     public void run() {
			       elemanlari_ekle();
		        }
		   }
   
	   
	   
	//*********************************
	public void ekle(Dugum dugum) {
		
		if(dugum!=null){
	
		if(dugum.sinif_entropisi!=0){
			Vector<String> degiskenler=degiskenleri_bul(dugum);

				if(dugum.sol==null&&dugum.sag==null){
					
                   
					 //************dugumun solu için
					/*
					 Vector<String> yeni_tablo=new Vector<String>();
					 int yeni_satir1=tabloyu_kirp(yeni_tablo,dugum.tablo,dugum.tablo_sutun,dugum.tablo_satir,dugum.secilen_sutun,degiskenler.get(0));
					 int yeni_sutun1=dugum.tablo_sutun-1;
					 dugum.sol=new Dugum(yeni_satir1,yeni_sutun1);
					 dugum.sol.tablo=yeni_tablo;
					
					  if(yeni_sutun1==1) {
						 dugum.sol=null;
						 System.out.println("etiket degeri sifirlanmadi 1 ve 2 ye geçiþ var.");
					 }
					 else{
					 float  sinif_entropi=sinif_entropi(0,yeni_tablo,yeni_sutun1,yeni_satir1);
					 int secilen_sutun=tablo_kazanc_hesapla(yeni_tablo,yeni_sutun1,yeni_satir1, sinif_entropi);
						dugum.sol.secilen_sutun=secilen_sutun;
						dugum.sol.sinif_entropisi=sinif_entropi;
						dugum.sol.degisken=degiskenler.get(0);
						System.out.println(dugum.degisken+"  dugumunun soluna "+degiskenler.get(0) + "eklendi");
						System.out.println("entropi degeri:"+dugum.sol.sinif_entropisi);
						
					 }	
						
						*/
						
						agacta_thread1 sol=new agacta_thread1(dugum,degiskenler.get(0));
						agacta_thread2 sag=new agacta_thread2(dugum,degiskenler.get(1));
                        sol.start();
                        sag.start();
                        try {
							sol.sleep(100);
							sag.sleep(100);
						} catch (Exception e) {
							// TODO: handle exception
						}
				
				 //*****************dugumun sagý için
		              
		     			
			             /*
						 Vector<String> yeni_tablo2=new Vector<String>();
						 int yeni_satir2=tabloyu_kirp(yeni_tablo2,dugum.tablo,dugum.tablo_sutun,dugum.tablo_satir,dugum.secilen_sutun,degiskenler.get(1));
						 int yeni_sutun2=dugum.tablo_sutun-1;	 
						 dugum.sag=new Dugum(yeni_satir2,yeni_sutun2);
						 dugum.sag.tablo=yeni_tablo2;
						 if(yeni_sutun2==1) {
							 dugum.sag=null;
							 System.out.println("etiket degeri sifirlanmadi 1 ve 2 ye geçiþ var.");
						 }
						 else{
						 float sinif_entropi2=sinif_entropi(0,yeni_tablo2,yeni_sutun2,yeni_satir2);
						 int secilen_sutun2=tablo_kazanc_hesapla(yeni_tablo2,yeni_sutun2,yeni_satir2, sinif_entropi2);
							dugum.sag.secilen_sutun=secilen_sutun2;
							dugum.sag.sinif_entropisi=sinif_entropi2;
							dugum.sag.degisken=degiskenler.get(1);
							System.out.println(dugum.degisken+"  dugumunun sagýna "+degiskenler.get(1) + "eklendi");
							System.out.println("entropi degeri:"+dugum.sol.sinif_entropisi);
							
						 }
						 */
							
			}
				
		}
		
		else if(dugum.sinif_entropisi==0){
			 Vector<String> etiket=etiket_bul(dugum);
			 dugum.etiket=etiket;
		}
		
		ekle(dugum.sag);
		ekle(dugum.sol);
		
	}
	
    }
	
	//**********************************
	public void preorder(Dugum kok){
		if(kok!=null){
		/*
			 System.out.println("*******Dugumdeki tablo*******************************");
				for(int i=0;i<kok.tablo_satir;i++){
					for(int j=0;j<kok.tablo_sutun;j++){
						System.out.print(kok.tablo.get(i*kok.tablo_sutun+j)+"      ");
					}
					System.out.println();
				}
			*/	
				System.out.println("*******Dugumdeki diðer bilgiler*******************************");
				//System.out.println("secilen_sutun:"+kok.secilen_sutun);
				System.out.println("degisken:"+kok.degisken);
				if(kok.etiket!=null){
					for(int i=0;i<kok.etiket.size();i++){
						System.out.print("etiket degeri:"+kok.etiket.get(i)+"  ");
					}
				}
				System.out.println();
				//System.out.println("sinif_entropisi:"+kok.sinif_entropisi);
			preorder(kok.sol);
			preorder(kok.sag);
		}
	}
	
	//**********************************
	 public void agaci_ciz(Graphics g,Dugum kok,int x,int y){
 		if(kok!=null){
 			/*
 		   if(kok.sol==null&&kok.sag==null){
 			   g.setColor(Color.black);
    	        g.drawString(Integer.toString(kok.deger),x,y);
    			g.setColor(Color.YELLOW);
    			g.fillOval(x-15, y-15,50,50);//dugum icin daire cizer.
 		   }
 			*/
 			
 		  
 			if(kok.sol!=null){
 				
 				//sola yatýk bir çizgi çek
 				//kok.sol dugumunu ekrana bas
 				g.setColor(Color.black);
 				g.drawLine(x+10,y+30,x-100,y+100);
 				g.setColor(Color.YELLOW);
        		g.fillOval(x-120, y+60,50,50);//dugum icin daire cizer
        	 g.setColor(Color.black);
     	     g.drawString(kok.sol.degisken,x-100,y+40);
     	    if(kok.sag.etiket!=null){
   	        	g.drawString(kok.sol.etiket.get(0)+","+kok.sol.etiket.get(1),x-95,y+100);
   	          }
 			}
 			if(kok.sag!=null){
 				//saga yatýk cizgi çek
 				//kok.sag dugumunu ekrana bas
 				g.setColor(Color.black);
 				g.drawLine(x+10,y+30,x+100,y+100);
 				g.setColor(Color.YELLOW);
        	g.fillOval(x+60, y+60,50,50);//dugum icin daire cizer 
 				
        		 g.setColor(Color.black);
   	          g.drawString(kok.sag.degisken,x+100,y+40);
   	          if(kok.sag.etiket!=null){
   	        	g.drawString(kok.sag.etiket.get(0)+","+kok.sag.etiket.get(1),x+75,y+100);
   	          }
 			}
 			
 			
 			
 			agaci_ciz(g, kok.sol,x-150,y+100);
 			agaci_ciz(g, kok.sag,x+150,y+100);
 		}
 		
 	}
   
	//**********************************
    /*
	public void agaci_ciz(Graphics g,Dugum kok,int x,int y){
		if(kok!=null){
	

			//g.setColor(Color.YELLOW);
			//g.fillRect(x, y,20,20);//dugum icin daire cizer.
			
			if(kok.sol!=null){
				
				//sola yatýk bir çizgi çek
				//kok.sol dugumunu ekrana bas
				g.setColor(Color.black);
				g.drawLine(x+10,y+30,x-50,y+100);
			  
			}
			if(kok.sag!=null){
				//saga yatýk cizgi çek
				//kok.sag dugumunu ekrana bas
				g.setColor(Color.black);
				g.drawLine(x+10,y+30,x+50,y+100);
			}
			
			agaci_ciz(g, kok.sol,x-150,y+100);
			agaci_ciz(g, kok.sag,x+150,y+100);
		}
		
	}
	*/
	//**********************************
	@Override
	public void paint(Graphics g) {
        agaci_ciz(g,agac,450,150);
		super.paint(g);
		
	}
  
	
	
	//**********************************
	public static void main(String[] args) throws IOException{
		
		thread2 nesne=new thread2();
		//cizim mainFrame = new cizim();
		
		//yaz();
		System.out.println();
		System.out.println("satir:"+tablo_satir);
		System.out.println("sutun:"+tablo_sutun);
		System.out.println("ort_entropi:"+ort_entropi);	
		/*float ort_entropi=0;
        ort_entropi=nesne.sinif_entropi(ort_entropi,tablo,tablo_sutun,tablo_satir);
        */
	    
	    /*
	    nesne.bolumleme(tablo, tablo_satir, tablo_sutun,50,0);
	    nesne.kazanc_hesapla(ort_entropi,0);
	    
	    for(int i=0;i<kazanc.size();i++){
	    	System.out.println(kazanc.get(i));
	    }
	    */
	    
/*
		System.out.println("*********************************************************************");
		for(int i=0;i<tablo_satir;i++){
			for(int j=0;j<tablo_sutun;j++){
				System.out.print(tablo_bol.get(i*tablo_sutun+j)+"      ");
			}
			System.out.println();
		}
		 
		
		System.out.println("*********************************************************************");
		for(int i=0;i<tablo_satir;i++){
			for(int j=0;j<tablo_sutun;j++){
				System.out.print(tablo_bol1.get(i*tablo_sutun+j)+"      ");
			}
			System.out.println();
		}
		 
	*/
		
		/*
		System.out.println("*********************************************************************");
		for(int i=0;i<tablo_satir;i++){
			for(int j=0;j<tablo_sutun;j++){
				System.out.print(tablo_bol.get(i*tablo_sutun+j)+"      ");
			}
			System.out.println();
		}
		
		System.out.println("*********************************************************************");
		for(int i=0;i<tablo_satir;i++){
			for(int j=0;j<tablo_sutun;j++){
				System.out.print(tablo_bol1.get(i*tablo_sutun+j)+"      ");
			}
			System.out.println();
		}
		
		
		System.out.println("*********************************************************************");
		for(int i=0;i<tablo_satir;i++){
			for(int j=0;j<tablo_sutun;j++){
				System.out.print(tablo_bol2.get(i*tablo_sutun+j)+"      ");
			}
			System.out.println();
		}
		*/
		    
		    
		    System.out.println("******************threadlerden sonra tablonun hali*******************");
			for(int i=0;i<tablo_satir;i++){
				for(int j=0;j<tablo_sutun;j++){
					System.out.print(tablo.get(i*tablo_sutun+j)+"      ");
				}
				System.out.println();
			}
			for(int i=0;i<kazanc.size();i++){
		    	System.out.format("kazanc: %4f%n",kazanc.get(i));
		    }
	     nesne.en_iyi_tablo();
	     
	     System.out.println("****************en iyi tablo kýsmý*****************************************************");
			for(int i=0;i<tablo_satir;i++){
				for(int j=0;j<tablo_sutun;j++){
					System.out.print(tablo.get(i*tablo_sutun+j)+"      ");
				}
				System.out.println();
			}
			
			
		
		     nesne.basla();
			// nesne.en_iyi_tablo();
			// nesne.basla();
			 nesne.setSize(900,700);
				
			 nesne.setTitle("Agac");
			 nesne.setBackground(Color.gray);
			 nesne.setResizable(false);
			 nesne.setVisible(true);
            
		/*
		    nesne.en_iyi_tablo();
		    int secilen_sutun=nesne.tablo_kazanc_hesapla(tablo);
		    System.out.println(secilen_sutun);
		    System.out.println("*********************************************************************");
			for(int i=0;i<tablo_satir;i++){
				for(int j=0;j<tablo_sutun;j++){
					System.out.print(tablo.get(i*tablo_sutun+j)+"      ");
				}
				System.out.println();
			}
		    Vector<String> yeni_tablo=new Vector<String>();
		    int yeni_satir=nesne.tabloyu_kirp(yeni_tablo,tablo,tablo_sutun,tablo_satir,secilen_sutun,tablo.get(2));
		    int yeni_sutun=tablo_sutun-1;
		    
		    System.out.println("kirpilmiþ hali");
			
			for(int i=0;i<yeni_satir;i++){
				for(int j=0;j<yeni_sutun;j++){
					System.out.print(yeni_tablo.get(i*yeni_sutun+j)+"  ");
				}
				System.out.println();
			}
		  */
		   nesne.preorder(agac);
			
			 
	    
	    }

}

