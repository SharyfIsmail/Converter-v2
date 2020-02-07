package converter.v2;

public class Str_f 
{
	short year, month, day;                      // календарное время
	short hour, min, sec;
    int reserve;

	float skz, pik, F_ob_min, moment;             // значения

	short [] res = new short[4];                  //зарезервированные значения
	                                                 
	short [] m = new short[60030];                //   структура =60 030 byte
}
