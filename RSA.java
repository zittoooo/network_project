import java.lang.Math;

public class RSA {
	//�Ҽ� ����Ʈ
	static int[] list =
	{ 		
		223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 
		307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367
	};	
	public static int GCD(int x, int y) //�ִ�����
	{
		int tmp;
		while(y!=0)
		{
			tmp = x%y;
			x = y;
			y = tmp;
		}
		return x;
	}	
	public static int LCM(int x, int y)//�ּҰ����
	{
		return x*y/GCD(x,y);
	}
	
	public static boolean isRelativePrime(int x, int y)//���μ� Ȯ��
	{
		return (GCD(x,y)==1);
	}
	
	public static int getD(int e, int l) //e�� l�� �̿��Ͽ� d������
	{
		int d = 2;
		while( ((e*d)%l != 1) ) d++;
		return d;
	}
	
	public static int result(int x, int y, int z) //��,��ȣȭ�� ���
	{
		//text,E,N ��ȣȭ // enc,D,N ��ȣȭ
		long tmp = 1;
		for(int i=0; i<y;i ++) tmp = Math.floorMod(tmp*x, z);
		return (int)tmp;
	}
	public static int result(String x2, String y2, String z2) //��,��ȣȭ�� ���
	{
		int x = Integer.parseInt(x2);
		int y = Integer.parseInt(y2);
		int z = Integer.parseInt(z2);
		//text,E,N ��ȣȭ // enc,D,N ��ȣȭ
		long tmp = 1;
		for(int i=0; i<y;i ++) tmp = Math.floorMod(tmp*x, z);
		return (int)tmp;
	}
	
	public static int generateRandom(int min, int max)
	{
		return (int)Math.floor(Math.random()*(max-min+1)) + min;
	}
	
	public static int[] generateKey()
	{
		int ranNum1 = RSA.generateRandom(0, RSA.list.length-1);
		int ranNum2 = RSA.generateRandom(0, RSA.list.length-1);
		int p= RSA.list[ranNum1];
		while(ranNum1 == ranNum2)
			ranNum2 = RSA.generateRandom(0, RSA.list.length-1);
		int q= RSA.list[ranNum2];
		
		int N = p*q;
		int L = RSA.LCM(p-1,q-1);
		int E = 0;
		
		while(true)
		{
			E = RSA.generateRandom(1, L-1);
			if(RSA.isRelativePrime(E, L)) break;
					
		}
		
		int D = RSA.getD(E, L);
		
		int[] key = { N, L, E, D};
		return key;
	}
}
