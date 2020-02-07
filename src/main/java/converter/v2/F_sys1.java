package converter.v2;

public class F_sys1
{
	char[] pref = new char[8]; 
	float K1_skz_yel, K2_skz_yel, K1_pik_yel, K2_pik_yel, K1_skz_red, K2_skz_red;  // коэф-ты для допуска средних значений
	float n_zub;                                                                   // количество зубьев для вычисления F_ob_min
	float Kd_rmk1, Kd_rmk2;                                                        // коэф-ты для нормализации значений файла вибропроцесса
	float k_moment_rmk1,k_moment_rmk2;                                             // коэффициенты крутящего момента
	int  graniza_1, graniza_2;                                                     // границы нижняя и верхняя для определения диапазонов
	int moment_1,moment_2;                                                         // границы нижняя и верхняя для определения диапазонов по нагрузке
	int  kratnost_FOB;
}
