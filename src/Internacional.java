import java.util.HashMap;




public class Internacional extends Vuelo {
    protected String[]escalas;
    protected HashMap<Integer,Asiento>AsientosTuristasYEjecutivosYPrimeraClase;
    protected int TodosLosAsientos;
    protected double PrecioAsientoTurista; 
    protected double PrecioAsientoEjecutivo;
    protected double PrecioAsientoPrimeraClase; 
    protected int cantRefrigerios;

   
    private Internacional(String origen,String destino,String Fecha,int Tripulantes,double valorRefrigerio,int cantRefrigerios, double[]precios,int[]cantAsientos,String[]escalas) {
        super(origen,destino,Fecha,Tripulantes,valorRefrigerio,precios);
        this.escalas=escalas;
        this.cantRefrigerios=cantRefrigerios;
        this.AsientosTuristasYEjecutivosYPrimeraClase=new HashMap<>();
        IngresaLosAsientos(TodosLosAsientos(cantAsientos));
        this.PrecioAsientoTurista=precios[0]+ (valorRefrigerio * this.cantRefrigerios);
        this.PrecioAsientoEjecutivo=precios[1]+ (valorRefrigerio * this.cantRefrigerios);
        this.PrecioAsientoPrimeraClase=precios[2]+ (valorRefrigerio * this.cantRefrigerios);
        this.TodosLosAsientos=TodosLosAsientos(cantAsientos);
    }
    protected static Internacional Crear(String origen,String destino,String Fecha,int Tripulantes,double valorRefrigerio,int cantRefrigerios, double[]precios,int[]cantAsientos,String[]escalas) {
        Internacional Internacional= new Internacional( origen, destino, Fecha, Tripulantes, valorRefrigerio, cantRefrigerios, precios,cantAsientos,escalas);
        return Internacional;
        }
    
   
    @Override
    public HashMap<Integer, String> DamelosAsientosDisponibles(){
            HashMap<Integer, String> AsientosAGenerar = new HashMap<>();
    
            for(int i=1; i < this.TodosLosAsientos + 1; i++){
                Asiento asiento=this.AsientosTuristasYEjecutivosYPrimeraClase.get(i);
                if(!asiento.DimeDisponibilidad()){
                    if(i<=200){
                        AsientosAGenerar.put(i, "Disponible - Asiento numero " +i+ "Turista valor " + this.PrecioAsientoTurista + "\n");
                    }
                    if(i>200 && i<=250){
                        AsientosAGenerar.put(i, "Disponible - Asiento numero " +i+ " Ejecutivo valor " + this.PrecioAsientoEjecutivo + "\n");
                    }
                    else{
                        AsientosAGenerar.put(i, "Disponible - Asiento numero " +i+ " Primera Clase valor " + this.PrecioAsientoPrimeraClase + "\n");
                    }
    
                }
            }
            return AsientosAGenerar;
        }
        @Override
        public boolean BuscarAsiento(int Asiento){
            Asiento Asientosolicitado=this.AsientosTuristasYEjecutivosYPrimeraClase.get(Asiento);
            if(Asientosolicitado.Disponibilidad==false){
                return true;
            }
            return false;
        }      
        @Override
        public Asiento DameAsiento(int asiento){
            return this.AsientosTuristasYEjecutivosYPrimeraClase.get(asiento);
        }
        @Override
        public double DameValorASiento(int numDeAsiento){
            if(numDeAsiento<=200){
                return (this.PrecioAsientoTurista + (super.valorRefrigerio * this.cantRefrigerios ));
            }
            if(numDeAsiento>200 && numDeAsiento<=250){
                return this.PrecioAsientoEjecutivo + (super.valorRefrigerio * this.cantRefrigerios);
            }
            else{
                return this.PrecioAsientoPrimeraClase + (super.valorRefrigerio * this.cantRefrigerios);
            } 
            }
            @Override
            public void remover(int numDeAsiento,int DNI){
            Asiento asiento=AsientosTuristasYEjecutivosYPrimeraClase.get(numDeAsiento);
            Pasajero pasajero=asiento.Damepasajero();
                if(AsientoOcupado(numDeAsiento) ||  pasajero.DameDNI()==DNI){
                    this.AsientosTuristasYEjecutivosYPrimeraClase.remove(numDeAsiento);
                    this.AsientosTuristasYEjecutivosYPrimeraClase.put(numDeAsiento,Asiento.Crear());
                }
            }
            @Override
            public void BuscarYRemover(int DNI){
                for(Asiento asiento :this.AsientosTuristasYEjecutivosYPrimeraClase.values()){
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
                for (Integer key : this.AsientosTuristasYEjecutivosYPrimeraClase.keySet()){
                    Asiento asiento= this.AsientosTuristasYEjecutivosYPrimeraClase.get(key);
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
            for (Asiento asiento : AsientosTuristasYEjecutivosYPrimeraClase.values()) {
                if(asiento.DameDNIPasajero()==Dni){
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
                       Asiento asiento= this.AsientosTuristasYEjecutivosYPrimeraClase.get(Keyasiento);
                       asiento.IngresarPasajero(pasajero);
                    }
                    else{
                        Pasajero pasajero=new Pasajero(this.PrecioAsientoEjecutivo,pasajeros.get(Keyasiento));
                        Asiento asiento= this.AsientosTuristasYEjecutivosYPrimeraClase.get(Keyasiento);
                        asiento.IngresarPasajero(pasajero);
                    }
                }
                if(AsientoOcupado(Keyasiento)&&!AsientoOcupado(Keyasiento+1)){
                    Pasajero pasajero=new Pasajero(this.PrecioAsientoTurista,pasajeros.get(Keyasiento));
                    Asiento asiento= this.AsientosTuristasYEjecutivosYPrimeraClase.get(Keyasiento);
                    asiento.IngresarPasajero(pasajero);
                 }
                 else{
                     Pasajero pasajero=new Pasajero(this.PrecioAsientoEjecutivo,pasajeros.get(Keyasiento));
                     Asiento asiento= this.AsientosTuristasYEjecutivosYPrimeraClase.get(Keyasiento);
                     asiento.IngresarPasajero(pasajero);
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
    private  void IngresaLosAsientos(int TodosLosAsientos){
        for(int i=1; i < TodosLosAsientos+1;i++ ){
            this.AsientosTuristasYEjecutivosYPrimeraClase.put(i,Asiento.Crear());
        }
    }
    // @Override
    // protected String Detalles(){
    //     String detalle=super.Detalles() + "- Internacional";
    //     return detalle;
    // }
    @Override
    protected String Detalles(){
        String detalle=super.Detalles() + " - INTERNACIONAL";
        return detalle;
    }
}
    

