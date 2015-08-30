package distribuciones;

import java.util.Random;

public class DistribucionExponencial  extends Distribucion{

	private final float _lambda; // Lambda es una frecuencia, no la media
	private static Random random = new Random();
	
	public DistribucionExponencial(float valor) 
	{
		this._lambda = valor; 
	}
	
	public float generateValue() 
	{
		float valor = (float) (-(1/_lambda)*Math.log(random.nextFloat()));
		return  (float) (valor);
	}

}
