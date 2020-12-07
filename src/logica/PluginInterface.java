package logica;

public interface PluginInterface 
{
	// let the application pass in a parameter
	public void setParametros (int param1, int param2);

	// retrieve a result from the plugin
	public int operar() throws OperacionException;

	// return the name of this plugin
	public String getNombre();

	// can be called to determine whether the plugin
	// aborted execution due to an error condition
	public boolean tieneError();
}
