package converter.v2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;


public class FileBuilder extends Task<Void> 
{
	static  FileBuilder fileBuilder = new FileBuilder();
	
	StringProperty textProperty = new SimpleStringProperty(this, "TextProperty", "");
	
	static  Str_f fim = new Str_f();
	static  Fz_fdp fdp = new Fz_fdp();
	static  F_sys1 fsys1 = new F_sys1();
	static F_sys2 fsys2 = new F_sys2();
	
	static File file = null;
	
	static 	Sw[] sw = new Sw[8];
	Ffrr ffrr = new Ffrr();
	
	static int percent = 0;
	final static String vibr = "vibr";
	
	Formatter f = new Formatter();
	Calendar cal = Calendar.getInstance();

	public static void getDataSys1(byte[] sys1)
	{	
		fsys1.K1_skz_yel 	= getFloat(sys1, 8);
		fsys1.K2_skz_yel 	= getFloat(sys1, 12);
		fsys1.K1_pik_yel 	= getFloat(sys1, 16);
		fsys1.K2_pik_yel 	= getFloat(sys1, 20);
		fsys1.K1_skz_red 	= getFloat(sys1, 24);
		fsys1.K2_skz_red 	= getFloat(sys1, 28);
		fsys1.n_zub 		= getFloat(sys1, 32);
		fsys1.Kd_rmk1 		= getFloat(sys1, 36);
		fsys1.Kd_rmk2	    = getFloat(sys1, 40);
		fsys1.k_moment_rmk1 = getFloat(sys1, 44);
		fsys1.k_moment_rmk2 = getFloat(sys1, 48);
		fsys1.graniza_1 	= getInt(sys1, 52);
		fsys1.graniza_2 	= getInt(sys1, 54);
		fsys1.moment_1 		= getInt(sys1, 56);
		fsys1.moment_2 	    = getInt(sys1, 58);
		fsys1.kratnost_FOB  = getInt(sys1, 60);		
	}
	
	public static void getDataSys2(byte[] sys2)
	{
		int startByte = 512;
		fsys2.cnt = getLong(sys2, startByte); startByte += 4;
		for (int i = 0; i < 2; i++)
		{
			fsys2.rmk[i].count.o1	 = getShort(sys2, startByte); startByte += 1;
			fsys2.rmk[i].count.o2 	 = getShort(sys2, startByte); startByte += 1;
			
			fsys2.rmk[i].count.t1 	 = getInt(sys2, startByte); startByte += 2;
			fsys2.rmk[i].count.t2 	 = getInt(sys2, startByte); startByte += 2;
			
			fsys2.rmk[i].count.o31 	 = getShort(sys2, startByte); startByte += 1;
			fsys2.rmk[i].count.o32 	 = getShort(sys2, startByte); startByte += 1;	
			
			fsys2.rmk[i].count.t31   = getInt(sys2, startByte);   startByte += 2;
			fsys2.rmk[i].count.t32 	 = getInt(sys2, startByte);   startByte += 2;
			
			fsys2.rmk[i].Sr_skz_d1	 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sr_skz_d2 	 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sr_pik_d1 	 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sr_pik_d2 	 = getFloat(sys2, startByte); startByte += 4;          
			fsys2.rmk[i].pred_skz_d1 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].pred_skz_d2 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].pred_pik_d1 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].pred_pik_d2 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sr_M_d1	 = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sr_M_d2 	 = getFloat(sys2, startByte); startByte += 4;
			
			fsys2.rmk[i].Cnt_Bad_red = getLong(sys2, startByte);  startByte += 4;
			fsys2.rmk[i].Cnt_Bad_yel = getLong(sys2, startByte);  startByte += 4;		
			
			fsys2.rmk[i].Sum_skz_d1  = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sum_pik_d1  = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sum_skz_d2  = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Sum_pik_d2  = getFloat(sys2, startByte); startByte += 4;             
			fsys2.rmk[i].Prozent_yel = getFloat(sys2, startByte); startByte += 4;
			fsys2.rmk[i].Prozent_red = getFloat(sys2, startByte); startByte += 4;

			fsys2.rmk[i].cnt_d1      = getInt(sys2, startByte);   startByte += 2;
			fsys2.rmk[i].cnt_d2 	 = getInt(sys2, startByte);   startByte += 2;
			
			fsys2.rmk[i].defect_file = getShort(sys2, startByte); startByte += 1;
		}
		fsys2.nomer_rmk 	  		 = getInt(sys2, startByte);   startByte += 2;

		fsys2.period_recording 		 = getLong(sys2, startByte);  startByte += 4;
	}
	
	public static void getDataFdp(byte[] fdpArray)
	{
		fdp.year  = getShort(fdpArray, 0);
		fdp.month = getShort(fdpArray, 1);
		fdp.day   = getShort(fdpArray, 2);
		fdp.hour  = getShort(fdpArray, 3);
		fdp.min   = getShort(fdpArray, 4);
		fdp.sec   = getShort(fdpArray, 5);
		fdp.reserve= getInt(fdpArray, 6);
		fdp.skz   = getFloat(fdpArray, 8);
		fdp.pik   = getFloat(fdpArray, 12);
		fdp.F_ob_min = getFloat(fdpArray, 16);
		fdp.moment   = getFloat(fdpArray, 20);
	}
	
	public static void getDataFim(byte[] fimArray)
	{
		fim.year  = getShort(fimArray, 0);
		fim.month = getShort(fimArray, 1);
		fim.day   = getShort(fimArray, 2);
		fim.hour  = getShort(fimArray, 3);
		fim.min   = getShort(fimArray, 4);
		fim.sec   = getShort(fimArray, 5);
		fim.reserve= getInt(fimArray, 6);
		fim.skz   = getFloat(fimArray, 8);
		fim.pik   = getFloat(fimArray, 12);
		fim.F_ob_min = getFloat(fimArray, 16);
		fim.moment   = getFloat(fimArray, 20);
		
		for(int i = 0 , j = 24; i <= 3 ; i++)
		{
			fim.res[i] = (short) getInt(fimArray, j); j += 2;
		}
		for (int i = 0, j = 32 ; i  <= 60000 ; i++)
		{
			fim.m[i] = (short)getInt(fimArray, j); j +=2;
		}
		FileBuilder.percent++;
		fileBuilder.updateProgress(FileBuilder.percent, 1080);
		fileBuilder.updateMessage( (int) (FileBuilder.percent / 10.8) +"%");
	}

	public static void sysFilePrinter(String dir) throws IOException
	{
		String fileName = dir + "\\KSYSTEM1.TXT";
		try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName))))
		{
			printWriter.println("Системный файл_1");
	        printWriter.println("--------------------------------------");
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "K1_skz_yel:", fsys1.K1_skz_yel);			
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "K2_skz_yel:", fsys1.K2_skz_yel);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "K1_pik_yel:", fsys1.K1_pik_yel);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "K2_pik_yel:", fsys1.K2_pik_yel);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "K1_skz_red:", fsys1.K1_skz_red);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "K2_skz_red:", fsys1.K2_skz_red);
			printWriter.printf(Locale.PRC, "%s\t\t\t%f%n", "n_zub:", fsys1.n_zub);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "Kd_rmk1:", fsys1.Kd_rmk1);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "Kd_rmk2:", fsys1.Kd_rmk2);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "k_moment_rmk1:", fsys1.k_moment_rmk1);
			printWriter.printf(Locale.PRC, "%s\t\t%f%n", "k_moment_rmk2:", fsys1.k_moment_rmk2);
			printWriter.printf(Locale.PRC, "%s\t\t%d%n", "graniza_K1:", fsys1.graniza_1);
			printWriter.printf(Locale.PRC, "%s\t\t%d%n", "graniza_K2:", fsys1.graniza_2);
			printWriter.printf(Locale.PRC, "%s\t\t%d%n", "moment_1:", fsys1.moment_1);
			printWriter.printf(Locale.PRC, "%s\t\t%d%n", "moment_2:", fsys1.moment_2);
	        printWriter.println("--------------------------------------");
			printWriter.printf(Locale.FRANCE,"%s\t%d%n" ,"period_recording:", fsys2.period_recording);
			printWriter.printf(Locale.FRANCE,"%s\t\t%d%n" ,"kratnost_FOB:", fsys1.kratnost_FOB);

		}
		fileName = dir + "\\KSYSTEM2.TXT"; 
		try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName))))
		{
	        printWriter.println("Системный файл_2");
	        printWriter.println("--------------------------------------");
	        printWriter.printf("%s%13s%15s%n", "Параметр", "rmk_1", "rmk_2"); 
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "O1:", fsys2.rmk[0].count.o1, fsys2.rmk[1].count.o1);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "O2:", fsys2.rmk[0].count.o2, fsys2.rmk[1].count.o2);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "T1:", fsys2.rmk[0].count.t1, fsys2.rmk[1].count.t1);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "T2:", fsys2.rmk[0].count.t2, fsys2.rmk[1].count.t2);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "O31:", fsys2.rmk[0].count.o31, fsys2.rmk[1].count.o31);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "O32:", fsys2.rmk[0].count.o32, fsys2.rmk[1].count.o32);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "T31:", fsys2.rmk[0].count.t31, fsys2.rmk[1].count.t31);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "T32:", fsys2.rmk[0].count.t32, fsys2.rmk[1].count.t32);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sr_skz_d1:", fsys2.rmk[0].Sr_skz_d1, fsys2.rmk[1].Sr_skz_d1);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sr_pik_d1:" , fsys2.rmk[0].Sr_pik_d1 , fsys2.rmk[1].Sr_pik_d1);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sr_skz_d2:" , fsys2.rmk[0].Sr_skz_d2 , fsys2.rmk[1].Sr_skz_d2);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sr_pik_d2:" , fsys2.rmk[0].Sr_pik_d2 , fsys2.rmk[1].Sr_pik_d2);
	    	
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "pred_skz_d1:", fsys2.rmk[0].pred_skz_d1 , fsys2.rmk[1].pred_skz_d1);	    	
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "pred_pik_d1:", fsys2.rmk[0].pred_pik_d1 , fsys2.rmk[1].pred_pik_d1);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "pred_skz_d2:", fsys2.rmk[0].pred_skz_d2 , fsys2.rmk[1].pred_skz_d2);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "pred_pik_d2:", fsys2.rmk[0].pred_pik_d2 , fsys2.rmk[1].pred_pik_d2);
	    	
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sr_M_d1:", fsys2.rmk[0].Sr_M_d1 , fsys2.rmk[1].Sr_M_d1);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sr_M_d2:", fsys2.rmk[0].Sr_M_d2 , fsys2.rmk[1].Sr_M_d2);
	    	printWriter.printf(Locale.PRC, "%s\t%d\t\t%d%n", "Cnt_Bad_red:", fsys2.rmk[0].Cnt_Bad_red , fsys2.rmk[1].Cnt_Bad_red);
	    	printWriter.printf(Locale.PRC, "%s\t%d\t\t%d%n", "Cnt_Bad_yel:", fsys2.rmk[0].Cnt_Bad_yel , fsys2.rmk[1].Cnt_Bad_yel);
	    
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Prozent_yel:", fsys2.rmk[0].Prozent_yel , fsys2.rmk[1].Prozent_yel);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Prozent_red:", fsys2.rmk[0].Prozent_red , fsys2.rmk[1].Prozent_red);
	    	printWriter.printf(Locale.PRC, "%s\t%d\t\t%d%n", "defect_file:", fsys2.rmk[0].defect_file , fsys2.rmk[1].defect_file);
	    	
	    	printWriter.println();
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sum_skz_d1:", fsys2.rmk[0].Sum_skz_d1 , fsys2.rmk[1].Sum_skz_d1);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sum_pik_d1:", fsys2.rmk[0].Sum_pik_d1 , fsys2.rmk[1].Sum_pik_d1);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sum_skz_d2:", fsys2.rmk[0].Sum_skz_d2 , fsys2.rmk[1].Sum_skz_d2);
	    	printWriter.printf(Locale.PRC, "%s\t%f\t%f%n", "Sum_pik_d2:", fsys2.rmk[0].Sum_pik_d2 , fsys2.rmk[1].Sum_pik_d2);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "cnt_d1:", fsys2.rmk[0].cnt_d1 , fsys2.rmk[1].cnt_d1);
	    	printWriter.printf(Locale.PRC, "%s\t\t%d\t\t%d%n", "cnt_d2:", fsys2.rmk[0].cnt_d2 , fsys2.rmk[1].cnt_d2);
		}
	}
	
	private static void masFilePrinter(String dir) throws IOException
	{
		float h,k;
		try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(dir))))
		{
	        printWriter.printf("%s %17s %20s","Время","Отметка","Значение АЦП"); 
	        printWriter.println();
	        printWriter.println("-----------------------------------------------");
	        
	        for(int i = 0 ; i <=  60000 ; i++)
	        {
	        	h = (float) (i * 0.0001);
	        	 k = fim.m[i]&0x01;
	        	printWriter.printf(Locale.PRC,"%.4f\t\t%.1f\t\t%d%n", h, k, fim.m[i]);
	        }
	        printWriter.println("Конец");
		}
	}
	
	public static void otherFileBuilder(String dir, Sw[] sw, Ffrr ffrr, ByteBuffer byteBuffer)throws IOException 
	{
		StrKv kV = new StrKv();
		 byte[] dataArray;
		long addr = 0;
		String fileNameS = dir +"\\-+S+-.TXT";
		String fileNameFdp = dir +  "\\-FDP.TXT";
		try(PrintWriter printWriterS = new PrintWriter(new BufferedWriter(new FileWriter(fileNameS))))
		{
			printWriterS.printf("%s %9s %s %12s %7s %4s %7s %8s %6s %7s %7s %7s %15s %18s %14s %14s %7s %7s %7s %13s%n", 
					"№ PMK", "Обр/раб", "Тип диап.","Тип ф.", "№ п.п.","Год","Мес","День","Час", "Мин","Сек","СКЗ","Пик","Об/мин",
					"Момен","Рез0","Рез1","Рез2","Рез3","Имя файла");
			printWriterS.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			try(PrintWriter printWriterFdp = new PrintWriter(new BufferedWriter(new FileWriter(fileNameFdp))))
			{
				printWriterFdp.printf("%s %9s %s %12s %7s %4s %7s %8s %6s %7s %7s %7s %15s %18s %14s%n", 
						"№ PMK", "Обр/раб", "Тип диап.","Тип ф.", "№ п.п.","Год","Мес","День","Час", "Мин","Сек","СКЗ","Пик","Об/мин",
						"Момен");
				printWriterFdp.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
				
				for(int i = 0; i < sw.length ; i++ )
				{
					 kV.ssw = sw[i]; 
					 kV.Ab_m = (ffrr.r[kV.ssw.i].b_bl- ffrr.bgn_fi) * 512; 
					 kV.Len_m =  ffrr.r[kV.ssw.i].s_by ;
					 kV.step_m = ffrr.r[kV.ssw.i].N_bl;
					 if (ffrr.r[kV.ssw.i].ost_nbl > 0) 
						 kV.step_m++;
					 kV.step_m = kV.step_m * 512;
					 kV.N_m = ffrr.r[kV.ssw.i].n_fi;
					  for(int j = 0; j < kV.N_m; j++ )
					  {
						  addr = kV.Ab_m + j * kV.step_m;
						  dataArray = new byte[(int) kV.Len_m];
						  byteBuffer.position((int) addr);
						  byteBuffer.get(dataArray, 0, (int) kV.Len_m);
						  getDataFim(dataArray );
						  getDataFdp(dataArray);
						  if( (fim.year+ fim.month + fim.day)== 0)
						  {
							  continue;
						  }
						  
						  else
						  {
							  String s = dir+ "\\" + kV.ssw.prefix + String.format("%03d",j) +"__"+fim.year+"__"+fim.month +"__"+ fim.day+"__" +fim.hour + fim.min + fim.sec + ".TXT";
							  masFilePrinter(s);
							  printWriterS.printf(Locale.PRC, "%d\t%d\t%d\t\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%f\t%f\t%f\t%f\t%d\t%d\t%d\t%d\t%s%n",
									  kV.ssw.a, kV.ssw.b, kV.ssw.c, kV.ssw.i, j, fim.year, fim.month, fim.day, fim.hour, fim.min, fim.sec, fim.skz, fim.pik, fim.F_ob_min, fim.moment, fim.res[0], fim.res[1], fim.res[2], fim.res[3], s);						   
							  printWriterFdp.printf(Locale.PRC, "%d\t%d\t%d\t\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%f\t%f\t%f\t%f%n",
									  kV.ssw.a, kV.ssw.b, kV.ssw.c, kV.ssw.i, j, fim.year, fim.month, fim.day, fim.hour, fim.min, fim.sec, fim.skz, fim.pik, fim.F_ob_min,fim.moment);
						  }
					  }
				}
			}
		}
	}
	private static float getFloat(byte[] array, int startByte)
	{
		return ByteBuffer.wrap(array, startByte, Float.BYTES).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
	private static int getInt(byte[] array, int startByte)
	{
		return ByteBuffer.wrap(array, startByte, Short.BYTES).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}
	private  static long getLong(byte[] array, int startByte)
	{
		return ByteBuffer.wrap(array, startByte, Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	private static short getShort(byte[] array, int startByte)
	{
		return ByteBuffer.wrap(array, startByte, Byte.BYTES).order(ByteOrder.LITTLE_ENDIAN).get();
	}

	@Override
	protected Void call() throws Exception 
	{
		byte[] bytes;
		byte[] sys1;  
		byte[] sys2;  
		
		File myPath = null;
		String dir = null;
		
		dir = "files_"+ f.format(new Locale("ru","Ru"), "%ta,%td.%tm.%ty_%tH.%tM", cal,cal,cal,cal,cal,cal,cal).toString();
		
		bytes = Reader.getBytesFromFile(file);
		
		String vibr1 = bytesToString(bytes).intern();
	   // System.out.println(vibr.equals(vibr1));
	    if(vibr.equals(vibr1))
	    {
	    	Platform.runLater(() -> fileBuilder.setTextProperty("Processing..."));
	    	
	    	myPath = new File(dir);
			myPath.mkdir();
			myPath.mkdirs();
			
	    	ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
	    	
	    	sys1 = new byte[(int)(ffrr.r[0].s_by * ffrr.r[0].n_fi)]; 
	    	byteBuffer.get(sys1, 0, 512);
		 
	    	sys2 = new byte[(int)(ffrr.r[1].s_by * ffrr.r[1].n_fi)];
	    	byteBuffer.get(sys2, 0,sys2.length);
	
			 FileBuilder.getDataSys1(sys1);
			 FileBuilder.getDataSys2(sys2);
			 
			 try {
				 FileBuilder.sysFilePrinter(dir);
				 FileBuilder.otherFileBuilder(dir, sw, ffrr,byteBuffer );
				 
				 Platform.runLater(() -> fileBuilder.setTextProperty("Done..."));
				 
				 FileBuilder.percent = 0;
				 
			 } catch (IOException e) {
				 Platform.runLater(() -> fileBuilder.setTextProperty(e.toString()));
				 
				 e.printStackTrace();
			 }	
	     }
		 else
		 {
			 Platform.runLater(() -> fileBuilder.setTextProperty("Error..."));
			 throw new IOException();

		 }
		return null;
	}
	public void setFile (File file)
	{
		FileBuilder.file = file;
	}

	public StringProperty getTextProperty() {
		return textProperty;
	}

	public void setTextProperty(String textProperty) {
		this.textProperty.set(textProperty);
	}
	
	public static String bytesToString(byte[] bytes)
	{
		String vibr;
		char[] buf =  new char[4];
		for (int i = 0 ; i < buf.length; i++)
		{
			buf[i] = (char)bytes[i];
		}
		vibr = String.valueOf(buf);
		return  vibr;		
	}
}
