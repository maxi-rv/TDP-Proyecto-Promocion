package logica;

public class Division implements PluginInterface 
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
	public float operar() throws OperacionException 
	{
		if(parametro2==0)
			throw new OperacionException("No se puede dividir por cero.");
			
		return (float)parametro1/parametro2;
	}

	@Override
	public String getNombre() 
	{
		return "Divison";
	}

}
