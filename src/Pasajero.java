
public class Pasajero {
    protected int DNI;
    protected Pasaje pasaje;

    protected Pasajero(double Precio , int DNI){
        this.DNI=DNI;
        Pasaje pasaje=new Pasaje(Precio);
        this.pasaje=pasaje;
    }
    protected double DamePrecio(){
        return pasaje.DamePrecio();
    }
    protected void IngresaCodPasaje(int codigo){
        pasaje.Nuevocodigo(codigo);
    }
    public int DameDNI() {
        return this.DNI;
    }
}