import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import acm.graphics.*;
import acm.program.*;
import java.awt.Color;
import java.awt.event.*;
public class FireBall2 extends GraphicsProgram{
	private RandomGenerator rgen = RandomGenerator.getInstance();
	GImage tavsan; 
	private GOval alevTopu[];
	GLabel skor_metni, can_metni, kaybettin, basla, cikis, menuyeGit;
	int can = 3;
	int skor;
	int a=0;
	int secim =0; //0ise menü, 1 ise oyuna basla, 2 ise oyundan cik
	boolean yandi = false;
	private static final int TAVSAN_BOYUT =50;
	int ALEV_TOPU_SAYISI = 10;
	private static final int ALEV_TOPU_BOYUT = 50;
	double start_x = rgen.nextInt(0, getWidth()- ALEV_TOPU_BOYUT) ;
	double start_y = -ALEV_TOPU_BOYUT;
	public void run() {
		setSize(1370,800);
		background();
		baslangicMetinleri();
		addMouseListeners();
		if(secim==1) {
			sifirla();
			background();
			tavsanýYarat();
			animasyon();
		}else if(secim==2) {
			System.exit(0);
		}
		run();
	}
	private void background() { //arkaplan resmi
		GImage yanardag = new GImage("yanardag1.jpg");
		yanardag.scale(2.1);
		add(yanardag, 0,0);
	}
	private void sifirla() {
		secim = 0;
		removeAll();
	}
	private void baslangicMetinleri() {
		basla = new GLabel("BASLA");
		basla.setFont("Times New Roman-30");
		basla.setColor(Color.blue);
		add(basla, getWidth()/2-(basla.getWidth()), getHeight()/2-basla.getHeight());
		cikis = new GLabel("CIKIS");
		cikis.setFont("Times New Roman-30");
		cikis.setColor(Color.blue);
		add(cikis, getWidth()/2+(cikis.getWidth()/2), getHeight()/2+cikis.getHeight());
	}
	private void animasyon() { //animasyon komutumuz
		GOval [] alevTopu = alevleriYarat();
		animasyonMetinleri();
		while(true) {
			for(int i = 0; i < ALEV_TOPU_SAYISI; i++) {
				alevTopu[i].move(0,rgen.nextInt(2,15));

				if(alevTopu[i].getY()>=getHeight()) {
					skor++;
					skor_metni.setLabel("Skor: " + skor);
					skor_metni.setLocation(getWidth()-skor_metni.getWidth()-10,40);
					alevTopu[i].setLocation(rgen.nextInt(0, getWidth()- ALEV_TOPU_BOYUT), start_y);
				}
			}

			carpismaKontrol(tavsan.getX(), tavsan.getY());
			carpismaKontrol(tavsan.getX()+TAVSAN_BOYUT, tavsan.getY());
			carpismaKontrol(tavsan.getX(), tavsan.getY() + TAVSAN_BOYUT);
			carpismaKontrol(tavsan.getX()+TAVSAN_BOYUT, tavsan.getY() + TAVSAN_BOYUT);
			if(can==0) {
				removeAll();
				background();
				kaybettin = new GLabel("Anne tavsan yavrularýna ulasamadý :(");
				kaybettin.setFont("Jokerman-30");
				add(kaybettin, getWidth()/2 - kaybettin.getLineWidth(), getHeight()/2);
				waitForClick();
				System.exit(0);
				

			}
			if(skor>=100) {
				for(int i = 0; i < ALEV_TOPU_SAYISI; i++) {
					alevTopu[i].move(0,rgen.nextInt(4,10));
				}
			}else if(skor>=200) {           
				for(int i = 0; i < ALEV_TOPU_SAYISI; i++) {
					alevTopu[i].move(0,rgen.nextInt(7,15));
				}
			}
			if(yandi) {
				yandi = false;
				for(int i = 0; i < ALEV_TOPU_SAYISI; i++) {
					remove(alevTopu[i]);
				}
				waitForClick();
				animasyon();
			}
			pause(15);
		}
	}
	private void carpismaKontrol(double x, double y) { //carpisma kontrol komutumuz
		GObject obje = getElementAt(x,y);
		GOval oval = new GOval(4,5);
		if(obje != null && obje.getClass() == oval.getClass()) {
			can--;
			can_metni.setLabel("Can: " + can);
			yandi = true;
		}
	}
	private void animasyonMetinleri() {
		can_metni = new GLabel("Can: " + can);
		can_metni.setColor(Color.blue);
		can_metni.setFont("Jokerman-30");
		add(can_metni, 0,30);

		skor_metni = new GLabel("Skor: " + skor);
		skor_metni.setColor(Color.blue);
		skor_metni.setFont("Jokerman-30");
		add(skor_metni,getWidth()-130,40);
	}
	private GOval[] alevleriYarat() { //alevtoplarini yaratma ve ekrana ekleme komutumuz
		GOval[] alevTopu = new GOval[ALEV_TOPU_SAYISI];
		for(int i = 0; i < ALEV_TOPU_SAYISI; i++) {
			double start_x =rgen.nextInt(0, getWidth()-ALEV_TOPU_BOYUT);
			double start_y = 50;
			alevTopu[i] = new GOval(ALEV_TOPU_BOYUT,ALEV_TOPU_BOYUT);
			alevTopu[i].setColor(Color.red);
			alevTopu[i].setFilled(true);
			add(alevTopu[i],start_x, start_y);
		}
		return alevTopu;
	}
	public void mouseMoved(MouseEvent m) {
		int x = m.getX() - TAVSAN_BOYUT/2;
		tavsan.setLocation(x, tavsan.getY());
	}
	public void mouseClicked(MouseEvent m) {
		GObject obje = getElementAt(m.getX(),m.getY());
		if(obje != null) {
			if(obje == basla) {
				secim = 1;
			}else if(obje == cikis) {
				secim = 2;
			}

		}
	}
	private void tavsanýYarat() {
		tavsan = new GImage("tavsan1.png");
		tavsan.setSize(TAVSAN_BOYUT, TAVSAN_BOYUT);
		add(tavsan,(getWidth()/2)- 50, getHeight()-70);
	}
}