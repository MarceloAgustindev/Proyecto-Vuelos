import java.util.HashMap;

final class Privado extends Vuelo{
    protected double PrecioDeJet;
    protected int dniCompradorVIP;
    protected int CantidadDeJets;
    protected HashMap<Integer,Asiento>Asientos;

    
    private Privado(String origen,String destino,String Fecha,int Tripulantes,double precio, int dniComprador , int[]acompaniantes){
        super(origen,destino,Fecha,Tripulantes,0,null);
        int ContDejets=1;
        int cantAsientos=0;
        this.Asientos=new HashMap<>();
        for(int i=1; i < acompaniantes.length+1;i++ ){
            this.Asientos.put(i,Asiento.Crear());
            if(cantAsientos==15){
                ContDejets++;
                cantAsientos=0;
            }
            cantAsientos++;
        }
        this.CantidadDeJets=ContDejets;
        this.PrecioDeJet=(precio * ContDejets);
    }
    protected static Privado Crear(String origen,String destino,String Fecha,int Tripulantes,double precio, int cantAsientos , int[]acompaniantes){
        Privado nuevoPrivado=new Privado(origen,destino,Fecha,Tripulantes,precio,cantAsientos,acompaniantes);
        return nuevoPrivado;
    }
    @Override
        public HashMap<Integer, String>DamelosAsientosDisponibles(){
            throw new RuntimeException("No se puede ver Asientos Disponibles de un vuelo privado");
        } 
    @Override
        public boolean BuscarAsiento(int Asiento){
           throw new RuntimeException("No Puedes buscar un asiento de un vuelo privado");
        }    
        @Override
        public Asiento DameAsiento(int asiento){
            throw new RuntimeException("No Puedes dar un asiento de un vuelo privado");
        }
        @Override
        public double DameValorASiento(int asiento){
            throw new RuntimeException("No Puedes dar valor de un asiento de vuelo privado");
        }
        @Override
        public void remover(int asiento,int DNI){
            throw new RuntimeException("No puede cancelar un pasaje  de un  vuelo privado");
        }
        @Override
        public void BuscarYRemover(int DNI){
            throw new RuntimeException("No puede cancelar un pasaje  de un  vuelo privado");
        }
            @Override 
        public HashMap<Integer,Integer> DamePasajeros(){
            throw new RuntimeException("No puede Dar los pasajeros de un  vuelo privado");
        }
        @Override
        public boolean BuscarAsientoconDNI(int Dni){
            throw new RuntimeException("No puede Dar los Datos de los pasajeros de un  vuelo privado");
        }
    // @Override
    // protected String Detalles(){
    //     String detalle=super.Detalles() + "- Privado " +"[" + this.CantidadDeJets +"]"  ;
    //     return detalle;
    // }

    @Override
    protected String Detalles() {        
        StringBuilder detallesBuilder = new StringBuilder(super.Detalles());
        detallesBuilder.append(" - PRIVADO (").append(this.CantidadDeJets).append(")");
        return detallesBuilder.toString();
    }
    @Override
    public  boolean equals(Vuelo vuelo){
        if(super.origen.equals(vuelo.dimeOrigen()) && super.Destino.equals(vuelo.dimeDestino()) && super.Fecha.equals(vuelo.dimeFecha()) ){
            return true;
        }
        return false;
    }
    @Override
        public void reprograma(HashMap<Integer,Integer> pasajeros){
            throw new RuntimeException("No es posible reprogramar un vuelo privado");
        }
	public double dameValorPrivado() {
		return this.PrecioDeJet;
	}
}
