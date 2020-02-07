package converter.v2;

public class F_sys2
{
    long cnt;        // абсолютный счетчик записи, изменяется функцией записи
     // при чтении F_sys2 если cnt=0 то это первое чтение после разметки
    
    Sys2[] rmk = {new Sys2(),new Sys2()};       //2 структуры для РМК1 и РМК2
	int nomer_rmk;                              // номер РМК, с которым работаем
	long period_recording;
	
	class Sys2 
	{
		Count_f count = new Count_f();                          // счетчики файлов 12 byte
		float Sr_skz_d1, Sr_skz_d2, Sr_pik_d1, Sr_pik_d2;       // средние значения ФДП
		float pred_skz_d1,pred_skz_d2,pred_pik_d1,pred_pik_d2;
		float Sr_M_d1, Sr_M_d2;                                 // средние значения ФДП --мощность
		long Cnt_Bad_red,Cnt_Bad_yel;                           // счетчики "хорошо"- "плохо" для индикации лампочкой
		float Sum_skz_d1, Sum_pik_d1, Sum_skz_d2, Sum_pik_d2;   // суммы для вычисления средних скз, пик
		float Prozent_yel,Prozent_red;                          // суммы для вычисления средних мощность
		int cnt_d1, cnt_d2;                                     // счетчики для вычисления средних
		short defect_file;                                      // номер АЦП, с которым работаем.
                                                            // 1-АЦП1, 2-АЦП2, 3-АЦП3, 0 - работаем по выбору из трех значений
    }

}
