package converter.v2;

public class Sw 
{
	int a,b,c ;       //признаки
					  // Kabc_xxx a=1/2 номер датчика b= O/T 1=образец/2=текущий
					  // с= 1/2 диапазон ххх - номер
	int i;     	      // индекс групп массивов по структуре FFR
	int k;            // индекс групп ФДП по структуре FFR
	String prefix;    // префикс имени файла "Kabc_"


	public Sw(int a, int b, int c, int i, int k, String prefix)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.i = i;
		this.k = k;
		this.prefix = prefix;
	}   
}
