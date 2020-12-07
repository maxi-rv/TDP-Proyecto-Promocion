package logica;

public class Potencia implements PluginInterface 
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
		if(parametro2<0)
			throw new OperacionException("Las potencias negativas no pueden realizarse.");
		
		return (int) Math.pow(parametro1, parametro2);
	}

	@Override
	public String getNombre() 
	{
		return "Potencia";
	}

	@Override
	public boolean tieneError() 
	{
		return false;
	}

}
