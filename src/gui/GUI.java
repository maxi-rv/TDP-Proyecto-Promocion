package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.BevelBorder;

import logica.Calculadora;
import logica.OperacionException;
import logica.PluginException;

import java.awt.Font;
import java.awt.SystemColor;

public class GUI 
{
	protected Calculadora calculadora;
	
	protected JFrame frmCalculadorasimple;
	
	protected JTextField textFieldNumero1;
	protected JTextField textFieldNumero2;
	protected JTextField textFieldResultado;
	
	protected JComboBox<String> listaDesplegableOperaciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI window = new GUI();
					window.frmCalculadorasimple.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() 
	{
		initialize();
		
		iniciarCalculadora();
		
		iniciarListaDesplegable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() 
	{
		frmCalculadorasimple = new JFrame();
		frmCalculadorasimple.setResizable(false);
		frmCalculadorasimple.getContentPane().setBackground(Color.DARK_GRAY);
		frmCalculadorasimple.setTitle("CalculadoraSimple");
		frmCalculadorasimple.setBounds(100, 100, 640, 410);
		frmCalculadorasimple.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculadorasimple.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 11, 604, 350);
		frmCalculadorasimple.getContentPane().add(panel);
		panel.setLayout(null);
		
		textFieldNumero2 = new JTextField();
		textFieldNumero2.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNumero2.setBounds(324, 171, 270, 20);
		panel.add(textFieldNumero2);
		textFieldNumero2.setColumns(10);
		
		textFieldNumero1 = new JTextField();
		textFieldNumero1.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNumero1.setBounds(10, 171, 270, 20);
		panel.add(textFieldNumero1);
		textFieldNumero1.setColumns(10);
		
		//INICIALIZA BOTON CARGA DE PLUGINS
		JButton btnActualizar = new JButton("Actualizar/Cargar Operaciones");
		btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnActualizar.setBounds(160, 23, 300, 23);
		btnActualizar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				iniciarCalculadora();
				
				iniciarListaDesplegable();
				
				reiniciarTextBoxes();
			}
		});
		panel.add(btnActualizar);
		
		listaDesplegableOperaciones = new JComboBox<String>();
		listaDesplegableOperaciones.setBounds(65, 80, 480, 20);
		panel.add(listaDesplegableOperaciones);
		
		//INICIALIZA BOTON DE OPERACION
		JButton btnOperacion = new JButton("Realizar Operacion");
		btnOperacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOperacion.setBounds(200, 227, 200, 23);
		btnOperacion.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String nombrePlugin = String.valueOf(listaDesplegableOperaciones.getSelectedItem());
				Integer resultado;
				int param1 = Integer.parseInt(textFieldNumero1.getText());
				int param2 = Integer.parseInt(textFieldNumero2.getText());
				
				try 
				{
					resultado = calculadora.realizarOperacion(nombrePlugin, param1, param2);
					textFieldResultado.setText(Integer.toString(resultado));
				} 
				catch (NumberFormatException | PluginException | OperacionException e) 
				{
					JFrame f = new JFrame();  
					JOptionPane.showMessageDialog(f,e.getMessage());
				}
			}
		});
		panel.add(btnOperacion);
		
		textFieldResultado = new JTextField();
		textFieldResultado.setEditable(false);
		textFieldResultado.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldResultado.setBounds(10, 306, 584, 20);
		panel.add(textFieldResultado);
		textFieldResultado.setColumns(10);
		
		JLabel lblNumero1 = new JLabel("Inserte el 1\u00B0 Numero:");
		lblNumero1.setForeground(SystemColor.textHighlight);
		lblNumero1.setBackground(new Color(255, 140, 0));
		lblNumero1.setVerticalAlignment(SwingConstants.TOP);
		lblNumero1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumero1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumero1.setBounds(10, 153, 270, 38);
		panel.add(lblNumero1);
		
		JLabel lblNumero2 = new JLabel("Inserte el 2\u00B0 Numero:");
		lblNumero2.setForeground(SystemColor.textHighlight);
		lblNumero2.setBackground(new Color(255, 140, 0));
		lblNumero2.setVerticalAlignment(SwingConstants.TOP);
		lblNumero2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumero2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumero2.setBounds(324, 153, 270, 38);
		panel.add(lblNumero2);
		
		JLabel lblResultado = new JLabel("Resultado:");
		lblResultado.setForeground(SystemColor.textHighlight);
		lblResultado.setBackground(new Color(255, 140, 0));
		lblResultado.setVerticalAlignment(SwingConstants.TOP);
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultado.setBounds(10, 289, 584, 37);
		panel.add(lblResultado);
	}
	
	protected void iniciarCalculadora() 
	{
		try 
		{
			calculadora = new Calculadora();
		} 
		catch (PluginException e) 
		{
			JFrame f = new JFrame();  
			JOptionPane.showMessageDialog(f,e.getMessage());
			System.exit(1);
		}
	}
	
	protected void iniciarListaDesplegable() 
	{
		listaDesplegableOperaciones.removeAllItems();
		
		listaDesplegableOperaciones.addItem("Seleccione la Operacion a realizar");
		
		List<String> operaciones = calculadora.getNombresOperaciones();
		
		for (String nombre : operaciones)
		{
			listaDesplegableOperaciones.addItem(nombre);			
		}
	}
	
	protected void reiniciarTextBoxes()
	{
		textFieldNumero1.setText("");
		textFieldNumero2.setText("");
		textFieldResultado.setText("");
	}
}
