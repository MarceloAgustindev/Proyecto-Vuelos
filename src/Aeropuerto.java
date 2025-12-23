
public class Aeropuerto {
   private String Nombre;
   private String Pais;   
   private String Provincia;
   private String direccion;
 private Aeropuerto(String nombre, String pais, String provincia, String direccion) {
    this.Nombre = nombre;
    this.Pais = pais;
    this.Provincia = provincia;
    this.direccion = direccion;
    }
protected static Aeropuerto Crear(String nombre, String pais, String provincia, String direccion){
    Aeropuerto aeropuerto=new Aeropuerto(nombre, pais, provincia, direccion);
    return aeropuerto;
}

protected String DamePais() {
    return this.Pais;
}
protected String DameProvincia() {
    return this.Provincia;
}
protected String dameNombre(){
    return this.Nombre;
}
protected String Detalle() {
	 StringBuilder Detalle = new StringBuilder();
	 Detalle.append("Aeropuerto:" + this.Nombre +"\n");
	 Detalle.append( this.Pais +"\n");
	 Detalle.append( this.Provincia +"\n");
	 Detalle.append( this.direccion +"\n");
	return Detalle.toString();

}
}
