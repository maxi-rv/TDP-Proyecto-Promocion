package logica;

public class Resta implements PluginInterface 
{
	//ATRIBUTOS
	protected int parametro1;
	protected int parametro2;
	
	@Override
	public void setParametros(int param1, int param2) 
	{
		parametro1 = param1;
		parametro2 = param2;
	}

	@Override
	public int operar() throws OperacionException 
	{
		return parametro1-parametro2;
	}

	@Override
	public String getNombre() 
	{
		return "Resta";
	}

	@Override
	public boolean tieneError() 
	{
		return false;
	}

}
