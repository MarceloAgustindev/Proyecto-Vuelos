
import java.util.HashMap;
 final class Nacional extends Vuelo{
    protected HashMap<Integer,Asiento>AsientosTuristasYEjecutivos;
    protected int TodosLosAsientos;
    protected double PrecioAsientoTurista; 
    protected double PrecioAsientoEjecutivo; 

    private Nacional(String origen,String destino,String Fecha,int Tripulantes,double valorRefrigerio, double[]precios,int[]cantAsientos){
        super(origen,destino,Fecha,Tripulantes,valorRefrigerio,precios);
        this.AsientosTuristasYEjecutivos=new HashMap<>();
        IngresaLosAsientos(TodosLosAsientos(cantAsientos));
        this.PrecioAsientoTurista=precios[0];
        this.PrecioAsientoEjecutivo=precios[1];
        this.TodosLosAsientos=TodosLosAsientos(cantAsientos);
    }


    protected static Nacional Crear(String origen,String destino,String Fecha,int Tripulantes,double valorRefrigerio, double[]precios,int[]cantAsientos) {
        Nacional Nacional= new Nacional(origen,destino,Fecha,Tripulantes,valorRefrigerio,precios,cantAsientos);
        return Nacional;
    }
    @Override
        public HashMap<Integer, String>DamelosAsientosDisponibles(){
            HashMap<Integer,String>AsientosEnGeneral=new HashMap<>();
            for(int i=0 ;i < 170 ;i++ ){
                    Asiento asiento=this.AsientosTuristasYEjecutivos.get(i);
                    if(!asiento.DimeDisponibilidad()){
                       if(i<=150){
                        AsientosEnGeneral.put(i,"Disponible - Asiento numero "+i+" Turista valor "+ this.PrecioAsientoTurista + "\n");
                       }
                       else{
                        AsientosEnGeneral.put(i,"Disponible - Asiento numero "+i+" Ejecutivo valor "+ this.PrecioAsientoEjecutivo + "\n");
                       }
                     }
            }
            return AsientosEnGeneral;
        }
        @Override
        public boolean BuscarAsiento(int Asiento){
            Asiento Asientosolicitado=this.AsientosTuristasYEjecutivos.get(Asiento);
            if(Asientosolicitado.Disponibilidad==false){
                return true;
            }
            return false;
        }    
        @Override
        public Asiento DameAsiento(int asiento){
            return this.AsientosTuristasYEjecutivos.get(asiento);
        }
        @Override
        public double DameValorASiento(int numDeAsiento){
            if(numDeAsiento<=150){
                return this.PrecioAsientoTurista + super.valorRefrigerio;
            }
            else{
                return this.PrecioAsientoEjecutivo + super.valorRefrigerio;
            }
            
        }
        @Override
        public void remover(int numDeAsiento,int DNI){
            Asiento asiento=AsientosTuristasYEjecutivos.get(numDeAsiento);
            Pasajero pasajero=asiento.Damepasajero();
            if(AsientoOcupado(numDeAsiento) || pasajero.DameDNI()==DNI){
                this.AsientosTuristasYEjecutivos.remove(numDeAsiento);
                this.AsientosTuristasYEjecutivos.put(numDeAsiento,Asiento.Crear());
            }
        }
        @Override
        public void BuscarYRemover(int DNI){
            for(Asiento asiento :this.AsientosTuristasYEjecutivos.values()){
                Pasajero pasajero= asiento.Damepasajero();
                if(pasajero !=null && pasajero.DameDNI()==DNI){
                    asiento.IngresarPasajero(null);
                    asiento.CambiarDisponibilidad(false);
                }
            }
        }
        @Override 
        public  HashMap<Integer,Integer> DamePasajeros(){
            HashMap<Integer,Integer>TodosLosPasajeros=new HashMap<>();
            for (Integer key : this.AsientosTuristasYEjecutivos.keySet()){
                Asiento asiento= this.AsientosTuristasYEjecutivos.get(key);
                if(asiento.DimeSiHayPasajero()){
                    int dni=asiento.DameDNIPasajero();
                    TodosLosPasajeros.put(key,dni);
                }
            }
            return TodosLosPasajeros;
        }
        @Override
        public  boolean equals(Vuelo vuelo){
            if(super.origen.equals(vuelo.dimeOrigen()) && super.Destino.equals(vuelo.dimeDestino()) && super.Fecha.equals(vuelo.dimeFecha()) ){
                return true;
            }
            return false;
        }
        @Override
        public boolean BuscarAsientoconDNI(int Dni){
            for (Asiento asiento : AsientosTuristasYEjecutivos.values()) {
                if(asiento.DimeSiHayPasajero() &&  asiento.DameDNIPasajero()==Dni){
                    return true;
                }
            }
            return false;
        }
        @Override
        public void reprograma(HashMap<Integer,Integer> pasajeros){
            for (Integer Keyasiento : pasajeros.keySet()) {
                if(!AsientoOcupado(Keyasiento)){
                    if(Keyasiento<=150){
                        Pasajero pasajero=new Pasajero(this.PrecioAsientoTurista,pasajeros.get(Keyasiento));
                       Asiento asiento= this.AsientosTuristasYEjecutivos.get(Keyasiento);
                       asiento.IngresarPasajero(pasajero);
                       asiento.CambiarDisponibilidad(true);
                    }
                    else{
                        Pasajero pasajero=new Pasajero(this.PrecioAsientoEjecutivo,pasajeros.get(Keyasiento));
                        Asiento asiento= this.AsientosTuristasYEjecutivos.get(Keyasiento);
                        asiento.IngresarPasajero(pasajero);
                       asiento.CambiarDisponibilidad(true);
                    }
                }
                if(AsientoOcupado(Keyasiento)&&!AsientoOcupado(Keyasiento+1)){
                    if(Keyasiento<=150){
                        Pasajero pasajero=new Pasajero(this.PrecioAsientoTurista,pasajeros.get(Keyasiento));
                       Asiento asiento= this.AsientosTuristasYEjecutivos.get(Keyasiento);
                       asiento.IngresarPasajero(pasajero);
                       asiento.CambiarDisponibilidad(true);
                    }
                    else{
                        Pasajero pasajero=new Pasajero(this.PrecioAsientoEjecutivo,pasajeros.get(Keyasiento));
                        Asiento asiento= this.AsientosTuristasYEjecutivos.get(Keyasiento);
                        asiento.IngresarPasajero(pasajero);
                       asiento.CambiarDisponibilidad(true);
                    }
                 }  
                }
            }
        


    private boolean AsientoOcupado(int numDeAsiento){
        Asiento asiento=this.DameAsiento(numDeAsiento);
        if(asiento.Disponibilidad==true || asiento.DimeSiHayPasajero() ){
            return true;
        }
        return false;
    }


    //Sirven para la creacion de el vuelo Nacional
    private  void IngresaLosAsientos(int TodosLosAsientos){
        for(int i=0; i < TodosLosAsientos;i++ ){
            this.AsientosTuristasYEjecutivos.put( i , Asiento.Crear());
        }
    }

    // @Override
    // protected String Detalles(){
    //     String detalle=super.Detalles() + "- Nacional";
    //     return detalle;
    // }

    @Override
    protected String Detalles(){
        String detalle=super.Detalles() + " - NACIONAL";
        return detalle;
    }
}
