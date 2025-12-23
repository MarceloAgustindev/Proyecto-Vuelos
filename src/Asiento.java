
public class Asiento {
    protected boolean Disponibilidad;
    protected Pasajero pasajero;

    private Asiento(){
        this.Disponibilidad=false;
        this.pasajero=null;
    }
    protected static Asiento Crear(){
        Asiento asiento=new Asiento();
        return asiento; 
    }
    protected void CambiarDisponibilidad(boolean Disponibilidad){
        this.Disponibilidad=Disponibilidad;
    }
    protected boolean DimeDisponibilidad(){
        return this.Disponibilidad;
    }
    protected void IngresarPasajero(Pasajero pasajero){
        this.pasajero=pasajero;
        this.Disponibilidad=true;
    }
    protected boolean DimeSiHayPasajero(){
        if(this.pasajero !=null ){
            return true;
        }
        return false;
    }
    public Pasajero Damepasajero() {
        return this.pasajero;
    }
    public int DameDNIPasajero(){
        return this.pasajero.DameDNI();
    }

}
