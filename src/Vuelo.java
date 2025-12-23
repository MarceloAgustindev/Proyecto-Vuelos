import java.util.HashMap;


public abstract class Vuelo {
    protected  String origen;
    protected  String Destino;
    protected  int Tripulantes;
    protected  String Fecha;
    protected double valorRefrigerio;
    protected double[]precios;
   
    
    protected Vuelo(String origen,String destino,String Fecha,int Tripulantes,double valorRefrigerio, double[]precios){
        this.origen=origen;
        this.Destino=destino;
        this.Fecha=Fecha;
        this.Tripulantes=Tripulantes;
        this.valorRefrigerio=valorRefrigerio;
        this.precios=precios;
        
      
    }

    public static int TodosLosAsientos(int[]arr){
        int cantidadAsientos=0;
        for(int i =0 ; i < arr.length;i++){
            cantidadAsientos=cantidadAsientos + arr[i];
        }
        return cantidadAsientos;
    } 
    // protected String Detalles(){
    // //{Formato del String: CodigoVuelo - Nombre Aeropuerto de salida - Nombre Aeropuerto de llegada -  fecha de salida - [NACIONAL /INTERNACIONAL / PRIVADO + cantidad de jets necesarios]* 
    //     String Detalles=" - " + this.origen + " - " + this.Destino + " - " + this.Fecha ;
    //     return Detalles;
    // }

    protected  String Detalles() {        
        StringBuilder detallesBuilder = new StringBuilder();
        
        detallesBuilder.append(" - ");
        detallesBuilder.append(this.origen);
        detallesBuilder.append(" - ");
        detallesBuilder.append(this.Destino);
        detallesBuilder.append(" - ");
        detallesBuilder.append(this.Fecha);
                
        return detallesBuilder.toString();
    }
    
    protected abstract HashMap<Integer, String> DamelosAsientosDisponibles();  
    protected abstract boolean BuscarAsiento(int Asiento); 
    protected abstract boolean BuscarAsientoconDNI(int Dni); 
    protected abstract Asiento DameAsiento(int Asiento);
    protected abstract double DameValorASiento(int numDeAsiento); 
    protected abstract void remover(int numDeAsiento,int DNI);
    protected abstract void BuscarYRemover(int DNI);
    protected abstract HashMap<Integer,Integer> DamePasajeros();
    protected abstract boolean equals(Vuelo vuelo);
     protected abstract void reprograma(HashMap<Integer,Integer> pasajeros);

    protected String dimeOrigen(){
        return this.origen;
    }
    protected String dimeDestino(){
        return this.Destino;
    }
    protected String dimeFecha(){
        return this.Fecha;
    }
   
 }