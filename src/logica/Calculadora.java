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
	//The directory where we keep the plugin classes
	protected String pluginsDir;

	//A list where we keep an initialized object of each plugin class
	protected List<PluginInterface> plugins;

	
	//CONSTRUCTOR
	public Calculadora() throws PluginException 
	{
		pluginsDir = "plugins";
				
		plugins = new ArrayList<PluginInterface>();
		
		this.getPlugins();
	}
	
	//METODOS
	public void getPlugins() throws PluginException 
	{
		//ALTENATIVA 1
		File dir = new File(System.getProperty("user.dir")+File.separator+pluginsDir);
		
		//ALTERNATIVA 2
		//File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		//String ruta = jarDir.getPath();	//.replace("%20", " ");
		//File dir = new File(ruta+File.separator+pluginsDir);
		
		//Muestra la direccion obtenia en un mensaje
		JFrame f = new JFrame();
		JOptionPane.showMessageDialog(f, dir.getPath());
		
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
					if (files[i].endsWith(".class"))
					{
						Class c = cl.loadClass("logica."+files[i].substring(0, files[i].indexOf(".")));
						
						Class[] intf = c.getInterfaces();
						
						for (int j=0; j<intf.length; j++) 
						{
							if (intf[j].getName().equals("logica.PluginInterface")) 
							{
								// the following line assumes that PluginFunction has a no-argument constructor
								PluginInterface pf = (PluginInterface) c.getDeclaredConstructor().newInstance();
								plugins.add(pf);
							}
						}
					}
				} 
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) 
				{
					throw new PluginException("File "+ files[i] +" does not contain a valid Plugin class.");
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
		
		if(plugin.tieneError())
			throw new PluginException("Error en el plugin.");
		
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
	
	public int cantOperaciones()
	{
		return plugins.size();
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
