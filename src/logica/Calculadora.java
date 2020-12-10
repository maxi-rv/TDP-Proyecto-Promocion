package logica;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Calculadora 
{
	//A list where we keep an initialized object of each plugin class
	protected List<PluginInterface> plugins;
	
	protected File dir;

	
	//CONSTRUCTOR
	public Calculadora() throws PluginException 
	{
		dir = new File("./plugins");
		
		plugins = new ArrayList<PluginInterface>();
	}
	
	//METODOS
	public void getPlugins() throws PluginException 
	{
		ClassLoader cl = new PluginClassLoader(dir);
		String[] files = dir.list();
		
		if (files!=null) 
		{
			// we'll only load classes directly in this directory -
			// no subdirectories, and no classes in packages are recognized
			
			for (int i=0; i<files.length; i++) 
			{
				try 
				{
					// only consider files ending in ".class"
					if (! files[i].endsWith(".class"))
						continue;

					Class c = cl.loadClass(files[i].substring(0, files[i].indexOf(".")));
					Class[] intf = c.getInterfaces();
					
					for (int j=0; j<intf.length; j++) 
					{
						if (intf[j].getName().contentEquals("logica.PluginInterface")) 
						{
							// the following line assumes that PluginFunction has a no-argument constructor
							PluginInterface pf = (PluginInterface) c.newInstance();
							plugins.add(pf);
							continue;
						}
					}
				} 
				catch (Exception ex) 
				{
					throw new PluginException("File "+ files[i] +" does not contain a valid PluginInterface class.");
				}
			}
		}
	}
	
	
	
	public float realizarOperacion(String nombre, int param1, int param2) throws PluginException, OperacionException
	{
		if(plugins.size()==0)
			throw new PluginException("No hay plugins cargados.");
		
		float toReturn;
		PluginInterface plugin = getOperacion(nombre);
		plugin.setParametros(param1, param2);
		
		toReturn = plugin.operar();
		
		return toReturn;
	}
	
	public List<String> getNombresOperaciones() throws PluginException
	{
		List<String> nombresOperaciones = new ArrayList<String>();
		
		for (PluginInterface plugin : plugins)
		{
			nombresOperaciones.add(plugin.getNombre());
		}
		
		if(nombresOperaciones.size() == 0) 
			throw new PluginException("No se encontraron plugins.");
		
		return nombresOperaciones;
	}
	
	protected PluginInterface getOperacion(String nombre) throws PluginException 
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
}
