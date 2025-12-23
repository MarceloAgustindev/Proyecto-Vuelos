import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;




public class Aerolinea {
    protected  String NombreDeLaAerolinea;
    protected String CUIT;
    protected HashMap<String,Aeropuerto>Aeropuertos;
    protected static   HashMap<String,Vuelo>Vuelos = new HashMap<>();
    protected HashMap<Integer,Cliente>Clientes;
    protected Map<String, Double> recaudacionPorDestino = new HashMap<>();
    private LocalDate fechaSalida;
    protected int CodigoNacional;
    protected int CodigoInternacional;
    protected int CodigoPrivado;

                //-------------------------------------------------------------------------------------------------------------------------------------------METODOS PRINCIPALES
                public Aerolinea(String Nombre,String CUIT ){
                    this.NombreDeLaAerolinea = Nombre;
                    this.CUIT=CUIT;
                    this.Aeropuertos=new HashMap<>();
                    this.Clientes=new HashMap<>();
                    this.CodigoNacional=100;
                    this.CodigoInternacional=400;
                    this.CodigoPrivado=700;
                }
                public static Aerolinea Crear(String NombreDeLaAerolinea,String CUIT){
                    Aerolinea aerolinea = new Aerolinea(NombreDeLaAerolinea, CUIT);
                    return aerolinea;
                }
                
                public void registrarCliente(int dni , String Nombre, String telefono){
                    if( Validacion_de_datos(Nombre ,telefono, dni) && this.Clientes.containsKey(dni)==false ){
                         Clientes.put(dni,Cliente.Crear(Nombre, dni, telefono));
                    }
                    else { 
                    	throw new RuntimeException("al Generar Cliente ");
                    }
                    
                }
                
                public void registrarAeropuerto(String Nombre,String Pais,String Provincia, String direccion){
                	if(!this.Aeropuertos.containsKey(Nombre)) {
                        Aeropuerto nuevAeropuerto=Aeropuerto.Crear(Nombre, Pais, Provincia, direccion);
                        Aeropuertos.put(Nombre,nuevAeropuerto);
                		}
                	else {
                		throw new RuntimeException("al REGISTRAR AEROPUERTO ");
                	}
                	 
                }
                public String registrarVueloPublicoNacional(String origen,String destino,String Fecha,int Tripulantes,double valorRefrigerio, double[]precios,int[]cantAsientos){
                    if(EsNacional(origen,destino)){
                            int cod=this.CodigoNacional;
                            String CodVuelo =cod +"-PUB";
                            this.CodigoNacional++;
                            Vuelos.put(CodVuelo,Nacional.Crear( origen, destino,Fecha,Tripulantes,valorRefrigerio,precios,cantAsientos));
                            return CodVuelo;
                            } 
                    throw new RuntimeException("No es nacional");
                  }
                    
                    public String registrarVueloPublicoInternacional(String origen,String destino,String Fecha,int Tripulantes,double valorRefrigerio,int cantRefrigerios, double[]precios,int[]cantAsientos,String[]escala){
                    int cod=this.CodigoInternacional;
                    String CodVuelo =cod +"-PUB";
                    this.CodigoInternacional++;
                    Vuelos.put(CodVuelo,Internacional.Crear( origen, destino, Fecha, Tripulantes, valorRefrigerio,cantRefrigerios, precios,cantAsientos,escala));
                    return CodVuelo;
                }

                public String VenderVueloPrivado(String origen,String destino,String Fecha,int Tripulantes,double precio, int dniComprador , int[]acompaniantes){
                    if(EsNacional(origen,destino) && TodosSonClientes(acompaniantes)){
                        int cod=this.CodigoPrivado;
                        String CodVuelo =cod +"-PRI";
                        this.CodigoPrivado++;
                        Privado vuelo=Privado.Crear( origen, destino, Fecha, Tripulantes, precio,  dniComprador ,acompaniantes);
                        Vuelos.put(CodVuelo,vuelo);
                        agregarRecaudacion(destino,vuelo.dameValorPrivado()+(vuelo.dameValorPrivado()*0.3));
                        return CodVuelo;
                   } 
                   throw new RuntimeException("Error,al generar venta");
                }   
            
                public Map<Integer, String> asientosDisponibles(String codVuelo){
                    if(BuscarVuelo(codVuelo)){
                        Vuelo vueloBuscado=Vuelos.get(codVuelo);
                         return  vueloBuscado.DamelosAsientosDisponibles();//tengo que cambiar que devuelva el map     
                };
                throw new RuntimeException("codigo no encontrado");
                }  
                
                

                
                public int venderPasaje(int DNI,String codVuelo,int NumDeAsiento,boolean aOcupar){
                    //devolver el codigo de pasaje
                     if(BuscarVuelo(codVuelo) && BuscarCliente(DNI)){
                            Vuelo vuelo =Aerolinea.Vuelos.get(codVuelo); 
                            if(vuelo.BuscarAsiento(NumDeAsiento)){
                                Asiento asiento = vuelo.DameAsiento(NumDeAsiento);
                                Pasajero pasajero=new Pasajero(vuelo.DameValorASiento(NumDeAsiento), DNI);
                                double valorAsiento = vuelo.DameValorASiento(NumDeAsiento);
                                asiento.IngresarPasajero(pasajero);
                                asiento.CambiarDisponibilidad(aOcupar);
                                int CodPasaje=ThreadLocalRandom.current().nextInt(900, 1000);
                                pasajero.IngresaCodPasaje(CodPasaje); 
                                agregarRecaudacion(vuelo.dimeDestino(), valorAsiento +(valorAsiento*0.20));
                                return CodPasaje;
                            }
                     }       
                    throw new RuntimeException("Error,No se pudo vender"); 
    
    
                }
    
                public void agregarRecaudacion(String destino, double monto) {
                    // Sumar el monto recaudado al destino correspondiente
                    recaudacionPorDestino.put(destino, recaudacionPorDestino.getOrDefault(destino, 0.0) + monto);
                }

                public double totalRecaudado(String destino) {
                    return recaudacionPorDestino.getOrDefault(destino, 0.0);
                }
    
                public void cancelarPasaje(int DNI,String CodVuelo,int NumDeAsiento){
                    if(BuscarVuelo(CodVuelo)){
                        Vuelo vueloSolicitado=Aerolinea.Vuelos.get(CodVuelo);
                        vueloSolicitado.remover(NumDeAsiento,DNI);
                        
                    }
                }
                public void cancelarPasaje(int DNI,String CodVuelo ){
                    if(BuscarVuelo(CodVuelo)){
                        Vuelo vueloSolicitado=Aerolinea.Vuelos.get(CodVuelo);
                        vueloSolicitado.BuscarYRemover(DNI);
                    }
                } 
                
                public LinkedList<String> cancelarVuelo(String CodigoVuelo){
                    if(BuscarVuelo(CodigoVuelo)){
                        Vuelo vueloACancelar=Vuelos.get(CodigoVuelo);
                        HashMap<Integer,Integer>pasajeros=vueloACancelar.DamePasajeros();
                        Vuelos.remove(CodigoVuelo);
                        Vuelo VueloParaReprogramar=consultarVuelosSimilares(vueloACancelar);  
                        VueloParaReprogramar.reprograma(pasajeros);
                        LinkedList<String>ListaDeCancelacion= new LinkedList<>();
                        for(Integer DNIpasajero : pasajeros.values()){
                            Cliente cliente = DameCliente(DNIpasajero);
                            if(VueloParaReprogramar.BuscarAsientoconDNI(DNIpasajero)){     
                                ListaDeCancelacion.add(cliente.DameDNI() + " - " + cliente.dameNombre() + " - " + cliente.DameTelefono() + " - " + dameKey(VueloParaReprogramar));
                            }
                            else{
                                ListaDeCancelacion.add(cliente.DameDNI() + " - " + cliente.dameNombre() + " - " + cliente.DameTelefono() + " - " + "CANCELADO");
                            }
                        }
                        return ListaDeCancelacion;
                    }
                    throw new RuntimeException("Vuelo no encontrado");
                }
                
    
    
                private String dameKey(Vuelo vueloParaReprogramar) {
					for(String key : Vuelos.keySet()) {
						if(vueloParaReprogramar.equals(Vuelos.get(key))) {
							return key;
						}
					}
					return "";
				}
				public LinkedList<String> consultarVuelosSimilares(String origen, String destino, String fecha) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LinkedList<String> vuelosSimilares = new LinkedList<>();
                    LocalDate fechaBase = LocalDate.parse(fecha, formatter);
                    LocalDate fechaMaxima = fechaBase.plusDays(7);
            
                    for (Vuelo vuelo : Vuelos.values()) {
                        LocalDate fechaSalida = LocalDate.parse(vuelo.Fecha, formatter);                        
                        if (fechaSalida != null){
                            if (vuelo.dimeOrigen().equalsIgnoreCase(origen) &&
                            vuelo.dimeDestino().equalsIgnoreCase(destino) &&
                            (fechaSalida.isEqual(fechaBase) || 
                            (fechaSalida.isAfter(fechaBase) && fechaSalida.isBefore(fechaMaxima)))) {
                                vuelosSimilares.add(vuelo.Detalles());
                            }
                        }
                    }
            
                    return vuelosSimilares; // Lista vacía si no se encontraron coincidencias.
                }
    
            //-------------------------------------------------------------------------------------------------------------------------------------------FIN DE METODOS PRINCIPALES
            @SuppressWarnings("static-access")
				//-----------------------------------------------------------------------------------------------------------------------------------------------------METODOS PRIVADOS
				@Override
				public String toString() {
					 StringBuilder detallesAerolinea = new StringBuilder();
					detallesAerolinea.append(" - "+this.NombreDeLaAerolinea +"\n");
					detallesAerolinea.append(" - "+this.CUIT +"\n");
					for(String key : this.Aeropuertos.keySet()) {
						Aeropuerto aeropuerto= this.Aeropuertos.get(key);
						detallesAerolinea.append(aeropuerto.Detalle()+"\n");
					}
					Iterator<String>iterador= this.Vuelos.keySet().iterator();
					while(iterador.hasNext()) {
						String key = iterador.next();
						Vuelo vuelo= Vuelos.get(key);
						detallesAerolinea.append( key + "\n");
						detallesAerolinea.append(vuelo.Detalles() + "\n");
						detallesAerolinea.append(vuelo.DamelosAsientosDisponibles() + "\n");
					}
					return detallesAerolinea.toString();
				}
				
				
				
				
				
				
				
                @SuppressWarnings("static-access")
                private Vuelo consultarVuelosSimilares(Vuelo vueloaCancelar){
                    for (Vuelo vuelo : this.Vuelos.values()){
                    	if(!vuelo.equals(vueloaCancelar) || vuelo.equals(vueloaCancelar))
                    		return vuelo;
                    }
                     throw new RuntimeException("No hay Vuelos similar");   
                }
            
                protected boolean EsNacional(String origen ,String Destino){
                if(BuscarAeropuerto(origen) && BuscarAeropuerto(Destino)) {
                	Aeropuerto DeOrigen= DameAeropuerto(origen);
                	Aeropuerto DeDestino= DameAeropuerto(Destino);
                	if(DeOrigen.DamePais() =="Argentina" && DeDestino.DamePais() == "Argentina") {
                		return true;
                	}	
                }
				throw new RuntimeException("No es un vuelo nacional");
             }
            private Aeropuerto DameAeropuerto(String Locacion) {
					return this.Aeropuertos.get(Locacion);
				}
			private boolean BuscarAeropuerto(String origen) {
					if(this.Aeropuertos.containsKey(origen)) {
						return true;
					}
					return false;
				}
			private boolean BuscarCliente(int DNI){
                    if(this.Clientes.containsKey(DNI)){
                        return true;
                    }
                return false;
            }
            private Cliente DameCliente(int DNI){
                    if(BuscarCliente(DNI)){
                        return this.Clientes.get(DNI);
                    }
                return null;
            }

            private boolean TodosSonClientes(int[]arr){
                boolean ret=true;
                for(int i =0 ; i<arr.length;i++){
                    ret=ret && BuscarCliente(arr[i]);
                }
                return ret;
            }
            
            private static boolean BuscarVuelo(String CodVuelo){
                if(Vuelos.containsKey(CodVuelo)){
            return true;
        }
        return false;
        }
            protected String detalleDeVuelo(String CodVuelo){
                if(BuscarVuelo(CodVuelo)){
                    Vuelo vueloSolicitado=Vuelos.get(CodVuelo);
                     String detalles=CodVuelo + vueloSolicitado.Detalles();
                     return detalles;
                }
               else{
                throw new RuntimeException("Codigo de Vuelo desconocido");
               }
            } 

            private boolean Validacion_de_datos(String nombre,String telefono, int dni) {
            	if(nombre.length()!=0 && telefono.length()!=0 && dni !=0) {
            		return true;
            	}
            	return false;
            }
//---------------------------------------------------------------------------------------------------------------------------------------------FIN METODOS PRIVADOS



                
}