package distribuciones;

import java.util.ArrayList;
import java.util.Random;

public class DistribucionEmpirica extends Distribucion{

	private final ArrayList<Float> limites;
	private final ArrayList<Float> valoresPorcentuales;
	private static float valorRandom = new Random().nextFloat();
	
	public DistribucionEmpirica(ArrayList<Float> lims, ArrayList<Float> valPorc)
	{
		limites = lims;
		valoresPorcentuales = valPorc;
	}
	public float generateValue() 
	{
		float acumulador = 0;
		int contador =0;
		while(true)
		{
			if (acumulador + valoresPorcentuales.get(contador)> valorRandom)
			{
				return (limites.get(contador+1)+limites.get(contador))/2;
			}
			acumulador += valoresPorcentuales.get(contador);
			contador++;
		}
	}

}
