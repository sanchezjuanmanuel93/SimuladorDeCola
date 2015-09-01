package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

import distribuciones.Distribucion;
import distribuciones.DistribucionExponencial;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logica.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnimacionSimulacion extends JFrame {

	private JPanel contentPane;
	private JTextField txtCola;
	private JTextField txtOcupado;
	private JSpinner spnDelay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnimacionSimulacion frame = new AnimacionSimulacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AnimacionSimulacion() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtCola = new JTextField();
		txtCola.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCola.setEditable(false);
		txtCola.setText("0");
		txtCola.setFont(new Font("Times New Roman", Font.PLAIN, 72));
		txtCola.setColumns(10);
		
		txtOcupado = new JTextField();
		txtOcupado.setText("NO");
		txtOcupado.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOcupado.setEditable(false);
		txtOcupado.setFont(new Font("Times New Roman", Font.PLAIN, 68));
		txtOcupado.setColumns(10);
		
		spnDelay = new JSpinner();
		spnDelay.setModel(new SpinnerNumberModel(25, 0, 1000, 25));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(58)
							.addPreferredGap(ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
							.addComponent(spnDelay, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(48)
							.addComponent(txtCola, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
							.addComponent(txtOcupado, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
					.addGap(48))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCola, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtOcupado, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(spnDelay, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(47))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void cambiarTamañoDeCola(int cambio)
	{
		int cantidad = Integer.parseInt(txtCola.getText());
		cantidad += cambio;
		txtCola.setText(String.valueOf(cantidad));
	}
	
	public void cambiarEstadoServicio(int bandera)
	{
		if (bandera == 0 )
		{
			txtOcupado.setText("NO");
		}
		else
		{
			txtOcupado.setText("SI");
		}
	}
	
	public int getDelay()
	{
		int _delay = (Integer)spnDelay.getValue();
		return _delay;
	}
	

}
