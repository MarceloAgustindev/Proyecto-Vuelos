public class Pasaje {
    protected double Precio;
    protected int CodPasaje;

    protected Pasaje(double Precio){
        this.Precio=Precio;
        this.CodPasaje=0;
    }
    protected double DamePrecio(){
        return Precio;
    }
    protected void Nuevocodigo(int codPasaje){
        this.CodPasaje=codPasaje;
    }
}