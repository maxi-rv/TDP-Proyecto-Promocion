package logica;

public class Suma implements PluginInterface 
{
	//ATRIBUTOS
	protected String nombre;
	protected int parametro1;
	protected int parametro2;
	
	@Override
	public void setParametros(int param1, int param2) 
	{
		nombre = "Suma";
		parametro1 = param1;
		parametro2 = param2;
	}

	@Override
	public int operar() throws OperacionException 
	{
		return parametro1+parametro2;
	}

	@Override
	public String getNombre() 
	{
		return nombre;
	}

	@Override
	public boolean tieneError() 
	{
		return false;
	}

}
