package servidor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class ventanaServidor extends JFrame {

	private JPanel contentPane;
	private JTextField campoPuerto;
	private JButton botonEncender;
	private JTextArea areaServidor;
	private JLabel labelPuerto;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaServidor frame = new ventanaServidor();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void encender() {
		botonEncender.setEnabled(false);
		servidor servidor = new servidor(this);
		servidor.start();
	}

	public ventanaServidor() {
		setTitle("Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		labelPuerto = new JLabel("Puerto");
		labelPuerto.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPuerto.setBounds(10, 11, 67, 23);
		contentPane.add(labelPuerto);

		campoPuerto = new JTextField();
		campoPuerto.setText("5000");
		campoPuerto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==10) {
					encender();
				}
			}
		});
		campoPuerto.setBounds(87, 12, 241, 20);
		contentPane.add(campoPuerto);
		campoPuerto.setColumns(10);

		botonEncender = new JButton("Encender");
		botonEncender.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				encender();
			}
		});
		botonEncender.setBounds(338, 11, 114, 23);
		contentPane.add(botonEncender);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 45, 432, 250);
		contentPane.add(scrollPane);

		areaServidor = new JTextArea();
		areaServidor.setEditable(false);
		scrollPane.setViewportView(areaServidor);
	}

	public JTextField getCampoPuerto() {
		return campoPuerto;
	}

	public JTextArea getAreaServidor() {
		return areaServidor;
	}
}
