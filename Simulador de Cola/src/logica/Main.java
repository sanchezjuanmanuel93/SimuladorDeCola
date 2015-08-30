package logica;

import java.util.ArrayList;
import java.util.Scanner;

import ventanas.AnimacionSimulacion;
import ventanas.frmInit;
import distribuciones.Distribucion;
import distribuciones.DistribucionExponencial;

public class Main {
	
			 static int siguiente_tipo_evento = 0, 
			 cantidad_clientes_atendidos = 0, 
			 cantidad_clientes_atendidos_requeridos = 0, 
			 cantidad_eventos = 0,
		     cantidad_clientes_en_cola = 0, 
		     estado_servidor = 0;

			 static float area_numero_clientes_en_cola = 0f,
			 area_estado_servidor = 0f, 
			 lambda_tiempo_entre_arribos = 1, 
			 lambda_tiempo_servicio = 2,
		     tiempo_simulacion = 0f, 
		     ocurrencia_ultimo_evento = 0f, 
		     esperas_total = 0f,
		     delay_entre_iteraciones = 25,
		     tiempo_simulacion_maximo = (float) 1E100;

			 static ArrayList<Float> tiempos_arribos = new ArrayList<>();
			 static ArrayList<Float> siguientes_eventos = new ArrayList<>();
			 
			 static ArrayList<Float> valoresXClientesEnCola = new ArrayList<Float>();
			 static ArrayList<Float> valoresYClientesEnCola = new ArrayList<Float>();
			 
			 static ArrayList<Float> valoresXServidorOcupado = new ArrayList<Float>();
			 static ArrayList<Float> valoresYServidorOcupado = new ArrayList<Float>();
			 
			 static Distribucion distribucionTiempoEntreArribos;
			 static Distribucion distribucionTiempoDeServicio;

			 static final int DESOCUPADO = 0,
					 OCUPADO = 1,
					 LIMITE_LONGITUD_COLA = 1000;
			 
			 static AnimacionSimulacion ventana = new AnimacionSimulacion();
	
	public static void cargameVariables(float lArribos, float lServicio, float cantClientesAtendidos, float tiempoMaxSimulacion){
		lambda_tiempo_entre_arribos = 1;
	     lambda_tiempo_servicio = 2;
	    cantidad_clientes_atendidos_requeridos = 1000;
	    tiempo_simulacion_maximo=123;
	}
			 
	public static void main(String[] args) throws InterruptedException 
	{
		 
		//frmInit init = new frmInit(lambda_tiempo_entre_arribos,lambda_tiempo_servicio,cantidad_clientes_atendidos_requeridos,tiempo_simulacion_maximo);
		//init.setVisible(true);
		Scanner s = new Scanner(System.in);
		System.out.println("Ingrese el tiempo entre arribos:");
		lambda_tiempo_entre_arribos = s.nextFloat();
		//lambda_tiempo_entre_arribos = 1;
	    //lambda_tiempo_servicio = 2;
		System.out.println("Ingrese el tiempo de servicio:");

	    lambda_tiempo_servicio = s.nextFloat();
	    //cantidad_clientes_atendidos_requeridos = 1000;
		System.out.println("Ingrese la cantidad de clientes atendidos: ");
	    cantidad_clientes_atendidos_requeridos = s.nextInt();    
	    //tiempo_simulacion_maximo=123;
		System.out.println("Ingrese el tiempo maximo de simulacion:");
	    tiempo_simulacion_maximo=s.nextInt();
	    
		ventana.setVisible(true);
	    cantidad_eventos = 2; // numero de eventos posibles (Arribo, Partida)
	    /*
	    lambda_tiempo_entre_arribos = 1;
	     lambda_tiempo_servicio = 2;
	    cantidad_clientes_atendidos_requeridos = 1000;
	    tiempo_simulacion_maximo = (float) 100; // parametros simulacion!
	     */
	    
	    distribucionTiempoEntreArribos =
	    		new DistribucionExponencial(lambda_tiempo_entre_arribos);
	    distribucionTiempoDeServicio = 
	    		new DistribucionExponencial(lambda_tiempo_servicio);
	    
	    //parametros de entrada

	    System.out.println("Sistema de cola con una servidor\n\n");
	    System.out.println("Lambda tiempo entre arribos "+
	    lambda_tiempo_entre_arribos+" minutes\n\n");
	    System.out.println("Lambda tiempo de servicios "+
	    lambda_tiempo_servicio+" minutes\n\n");
	    System.out.println("Cantidad de clientes atendidos objetivo"+
	    cantidad_clientes_atendidos_requeridos);

	    inicializar(); 
	    //inicializar variables
	    
	    while (cantidad_clientes_atendidos < cantidad_clientes_atendidos_requeridos &&
	    		tiempo_simulacion < tiempo_simulacion_maximo && tiempo_simulacion<tiempo_simulacion_maximo
	    		&& cantidad_clientes_en_cola<LIMITE_LONGITUD_COLA) 
	    // la simulacion se corre hasta que se atienden todos los 
	    	//clientes requeridos o se termina el tiempo
	    {

	        //correr tiempo hasta siguiente evento y determinar tipo de evento siguiente

	        correrTiempoHastaSiguienteEvento();
	        actualizar_Acumuladores_Areas();

	        switch (siguiente_tipo_evento) {
	            case 1:
	                programarArribo();
	                break;
	            case 2:
	                programarPartida();
	                break;
	        }
	        delay_entre_iteraciones = ventana.getDelay();
	        Thread.sleep((long) delay_entre_iteraciones);
	    }

	    //aca hay que guardar los valores de x e y en archivos .txt de clientes en cola 
	    // y de ocupacion del servidor
	}
	
	private static void programarPartida() 
	{
		int   i;
	    Float delay;

	    if (cantidad_clientes_en_cola == 0) {

	        //si la cola esta vacia, descocupamos el servidor y no tenemos en cuenta
	    	//la partida, con un valor infinito
	    	
	        estado_servidor      = DESOCUPADO;
	        ventana.cambiarEstadoServicio(0);
	        siguientes_eventos.set(1,(float) 1.0e+30);
	        valoresXServidorOcupado.add(tiempo_simulacion);
	        valoresYServidorOcupado.add((float) DESOCUPADO);
	    }

	    else {

	        //la cola no esta vacia, entonces hay que reducir su tamaño en 1

	        --cantidad_clientes_en_cola;
	        ventana.cambiarTamañoDeCola(-1);
	        //guardar en el acumulador la espera de este cliente

	        delay            = tiempo_simulacion - tiempos_arribos.get(0);
	        esperas_total += delay;

	        // incrementar el numero de clientes atendidos y programar partida

	        ++cantidad_clientes_atendidos;
	        siguientes_eventos.set(1, tiempo_simulacion + distribucionTiempoDeServicio.generateValue());

	        //mover a todos los clientes un puesto adelante

	        for (i = 1; i <= cantidad_clientes_en_cola; ++i)
	            tiempos_arribos.set(i-1, tiempos_arribos.get(i));
	    }
	    valoresXClientesEnCola.add(tiempo_simulacion);
	    valoresYClientesEnCola.add((float) cantidad_clientes_en_cola);
	    System.out.println("Cliente termina de ser atentido en "+tiempo_simulacion);
		
	}

	public static void inicializar() 
	{
	    tiempo_simulacion = 0f;

	    estado_servidor   = DESOCUPADO;
	    cantidad_clientes_en_cola        = 0;
	    ocurrencia_ultimo_evento = 0f;

	    cantidad_clientes_atendidos  = 0;
	    esperas_total    = 0f;
	    area_numero_clientes_en_cola      = 0f;
	    area_estado_servidor = 0f;
	    
	    siguientes_eventos.add(0,tiempo_simulacion  +distribucionTiempoEntreArribos.generateValue()) ;
	    // el primer arribo 
	    
	    siguientes_eventos.add(1,(float) 1e+30);
	    // la primer partida no se la tiene en cuenta dandole un tiempo infinito,
	    //ya que no hay clientes para partir todavia, porque nadie llego aun
	}
		

	public static void correrTiempoHastaSiguienteEvento()  //este metodos se encarga de manejar el tiempo
	{
		int   i;
		Float min_time_next_event = (float) 1e+29;

		siguiente_tipo_evento = 0;

		// Determinar siguiente tipo de evento

		for (i = 1; i <= cantidad_eventos; ++i)
			if (siguientes_eventos.get(i-1) < min_time_next_event)
			{
            min_time_next_event = siguientes_eventos.get(i-1);
            siguiente_tipo_evento     = i;
			}

		if (siguiente_tipo_evento == 0) 
		{

        // Lista de eventos siguientes vacia, se termina la simulacion

        System.out.println("Listado de eventos vacios en "+tiempo_simulacion);
		}
		else
		{
			// Lista de eventos siguientes no vacia, sigue corriendo la simulacion

			tiempo_simulacion = min_time_next_event;
		}
	}
	
	public static void programarArribo()  
	{
	    Float delay;

	    //programar proximo arribo

	    siguientes_eventos.set(0, tiempo_simulacion + distribucionTiempoEntreArribos.generateValue()); 

	    //chequear estado del servidor

	    if (estado_servidor == OCUPADO) {

	        //servidor ocupado, aumenta tamaño de la cola

	        ++cantidad_clientes_en_cola;
	        ventana.cambiarTamañoDeCola(1);

	        //chequear longitud maxima de la cola

	        if (cantidad_clientes_en_cola > LIMITE_LONGITUD_COLA) {

	            // Cola sobrepasada de carga, termina la simulacion
	            System.out.println("Cola sobrepasada de carga "+tiempo_simulacion);
	        }
	        else
	        {
	        //Hay lugar en la cola, guardamos tiempo de arribo
	        	tiempos_arribos.add(cantidad_clientes_en_cola-1, tiempo_simulacion); 
	        }
	    }

	    else {

	        //el servidor esta desocupado, no hay espera

	        delay            = 0f;
	        esperas_total += delay;

	       //aumenta cantidad de clientes atendidos y ocupada el servidor

	        ++cantidad_clientes_atendidos;
	        estado_servidor = OCUPADO;
	        ventana.cambiarEstadoServicio(1);
	        valoresXServidorOcupado.add(tiempo_simulacion);
	        valoresYServidorOcupado.add((float) OCUPADO);

	        //programamos la partida

	        siguientes_eventos.set(1, tiempo_simulacion + distribucionTiempoDeServicio.generateValue());
	    }
	    valoresXClientesEnCola.add(tiempo_simulacion);
	    valoresYClientesEnCola.add((float) cantidad_clientes_en_cola);
	    System.out.println("Cliente arriba en "+tiempo_simulacion);
	}
	
	public static void actualizar_Acumuladores_Areas()  
	//actualizar acumuladores de area
	
	{
		Float tiempo_desde_ultimo_evento;

		// calcular tiempo desde el ultimo evento y actualizar ocurrencia_ultimo_evento

		tiempo_desde_ultimo_evento = tiempo_simulacion - ocurrencia_ultimo_evento;
		ocurrencia_ultimo_evento       = tiempo_simulacion;

		//actualizar area de numero de clientes en cola

		area_numero_clientes_en_cola      += cantidad_clientes_en_cola * tiempo_desde_ultimo_evento;

		//actualizar area de ocupacion del servidor

		area_estado_servidor += estado_servidor * tiempo_desde_ultimo_evento;
		
}

}
