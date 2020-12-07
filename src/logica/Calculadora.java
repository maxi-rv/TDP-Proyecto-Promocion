package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Calculadora 
{
	//The directory where we keep the plugin classes
	String pluginsDir;

	//A list where we keep an initialized object of each plugin class
	List<PluginInterface> plugins;

	
	//CONSTRUCTOR
	public Calculadora () throws PluginException 
	{
		plugins = new ArrayList<PluginInterface>();
		
		this.getPlugins();
	}
	
	//METODOS
	public void getPlugins() throws PluginException 
	{
		File dir = new File(System.getProperty("user.dir") + File.separator + pluginsDir);
		
		ClassLoader cl = new PluginClassLoader(dir);
		
		if (dir.exists() && dir.isDirectory()) 
		{
			// we'll only load classes directly in this directory -
			// no subdirectories, and no classes in packages are recognized
			String[] files = dir.list();
			
			for (int i=0; i<files.length; i++) 
			{
				try 
				{
					// only consider files ending in ".class"
					if (!files[i].endsWith(".class"))
						continue;

					Class c = cl.loadClass(files[i].substring(0, files[i].indexOf(".")));
					
					Class[] intf = c.getInterfaces();
					
					for (int j=0; j<intf.length; j++) 
					{
						if (intf[j].getName().equals("PluginFunction")) 
						{
							// the following line assumes that PluginFunction has a no-argument constructor
							PluginInterface pf = (PluginInterface) c.newInstance();
							plugins.add(pf);
							continue;
						}
					}
				} 
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) 
				{
					throw new PluginException("File "+ files[i] +" does not contain a valid Plugin class.");
				}
			}
		}
	}
	
	public int realizarOperacion(String nombre, int param1, int param2) throws PluginException, OperacionException
	{
		int toReturn;
		PluginInterface plugin = getOperacion(nombre);
		plugin.setParametros(param1, param2);
		
		toReturn = plugin.operar();
		
		if(plugin.tieneError())
			throw new PluginException("Error en el plugin.");
		
		return toReturn;
	}
	
	private PluginInterface getOperacion(String nombre) throws PluginException 
	{
		Iterator<PluginInterface> it = plugins.iterator();
		boolean encontrado = false; 
		PluginInterface plugin = null;
		
		while (it.hasNext() && !encontrado) 
		{
			plugin = (PluginInterface) it.next();
			
			if(plugin.getNombre().equals(nombre))
				encontrado = true;
		}
		
		if(plugin == null) 
			throw new PluginException("Plugin no Encontrado.");
		
		return plugin;
	}
	
	public List<String> getNombresOperaciones()
	{
		List<String> nombresOperaciones = new ArrayList<String>();
		
		for (PluginInterface plugin : plugins)
		{
			nombresOperaciones.add(plugin.getNombre());
		}
		
		return nombresOperaciones;
	}
	
	public int cantOperaciones()
	{
		return plugins.size();
	}
	
	
}
