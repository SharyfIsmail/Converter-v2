package converter.v2;

public class Ffrr 
{
	public long bgn_fi;
	public long end_fi;
	public R[] r;
	
    class R
	{
		long b_bl, b_by, s_by ,N_bl, ost_nbl, n_fi;
		
		public R(long b_bl, long b_by, long s_by, long N_bl, long ost_nbl, long n_fi)
		{
			this.b_bl = b_bl;   //beg blok
            this.b_by = b_by;   //beg byte
            this.s_by = s_by;   //size byte
            this.N_bl = N_bl;   //N blok (-1)
            this.ost_nbl = ost_nbl; //ost
            this.n_fi = n_fi;   //N file
		}
	}
	public Ffrr()
	{
		bgn_fi = 528;
		end_fi = 255589;
		
		r = new R[18];
		r[0] = new R(528,      270336,     512,      1,         0,    1);
		r[1] = new R(529,      270848,     512,      1,         0,    256);// sys2			1
        //  M1 - первый вибродатчик системы	
        r[2] = new R(785,      401920,     120036,   234,       228,  20); // D1-OBR	        2	235
        r[3] = new R(5485 ,    2808320,    120036,   234,       228,  250);// D1-rab	        3	235
        r[4]=  new R(64235,    32888320,   120036,   234,       228,  20 );// D2-OBR		4	235
        r[5]=  new R(68935,    35294720,   120036,   234,       228,  250);// D2-rab		5	235
        //	127725	,	,		,     ,		                 ,							
        // --- FDP	,	,	m	,     ,	n in blok	          ,// FDP		m*n<512 !!
        r[6]=  new R(127685,   65374720,   24,       1,	    20,    20);// D1-OBR		6	440
        r[7]=  new R(127686,   65375232,   24,       250,	    20,    5000);// D1-rab		7	440
        r[8]=  new R(127936,   65503232,   24,       1,	    20,    20);// D2-OBR		8	440
        r[9]=  new R(127937,   65503744,   24,       250,	    20,    5000);// D2-rab		9	440
        // след св блок	128187	   
        //                beg blok  beg byte size byte   N blok (-1) ost     N file   // M2 - второй датчик
        r[10]=  new R(128187,  65631744,   120036,   234,      228 ,  20);// D1-OBR		10	235
        r[11]=  new R(132887,  68038144,   120036,   234,      228 ,  250);//D1-rab		11	235
        r[12]=  new R(191637,  98118144,   120036,   234,      228 ,  20);//D2-OBR                 12      235
        r[13]=  new R(196337,  100524544,  120036,   234,      228 ,  250);//D2-rab	        13      235
        //	255087 	,		,		,		,		,							
        // --- FDP	,		,		,		,	n in blok	,	// FDP				
        r[14]=  new R(255087,  130604544,  24,        1,       20,      20);  //D1-OBR	        14      440
        r[15]=  new R(255088,  130605056,  24,        250,     20,      5000); //D1-rab	        15      440
        r[16]=  new R(255338,  130733056,  24,        1,       20,      20); //D2-OBR	        16      440
        r[17]=  new R(255339,  130733568,  24,        250,     20,      5000); //D2-rab	        17      440
	}
}
