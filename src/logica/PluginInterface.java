package logica;

public interface PluginInterface 
{
	// let the application pass in a parameter
	public void setParametros(int param1, int param2);

	// retrieve a result from the plugin
	public float operar() throws OperacionException;

	// return the name of this plugin
	public String getNombre();
}
