public  class Cliente {
    protected String Nombre;
    protected int DNI;
    protected String Telefono;

    protected Cliente(String Nombre,int DNI,String Telefono){
        this.Nombre=Nombre;
        this.DNI=DNI;
        this.Telefono=Telefono;
}
    public static Cliente Crear(String Nombre,int DNI,String Telefono){
        Cliente nuevoCliente=new Cliente(Nombre,DNI,Telefono) ;
        return nuevoCliente;
    }
    public static Pasajero CrearPasajero(int precio, int NumDeAsiento){
        Pasajero nuevo= new Pasajero(precio,NumDeAsiento);
        return nuevo;
    }
    protected int DameDNI(){
        return this.DNI;
    }
    protected String dameNombre(){
        return this.Nombre;
    }
    public String DameTelefono() {
        return this.Telefono;
    }
}