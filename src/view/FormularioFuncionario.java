package view;

import controller.FuncionarioController;
import exception.AppException;
import model.Cargo;
import model.Dependencia;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioFuncionario extends JFrame {

    private JTextField txtNombre, txtApellido, txtDocumento;
    private JComboBox<Cargo> cbCargo;
    private JComboBox<Dependencia> cbDependencia;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    
    private FuncionarioController controller;
    private int idSeleccionado = -1;

    public FormularioFuncionario() {
        controller = new FuncionarioController();
        
        setTitle("Gestión de Funcionarios");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Panel Superior: Formulario ---
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Documento:"));
        txtDocumento = new JTextField();
        panelFormulario.add(txtDocumento);

        panelFormulario.add(new JLabel("Cargo:"));
        cbCargo = new JComboBox<>();
        panelFormulario.add(cbCargo);

        panelFormulario.add(new JLabel("Dependencia:"));
        cbDependencia = new JComboBox<>();
        panelFormulario.add(cbDependencia);

        // --- Botones del Formulario ---
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        panelFormulario.add(panelBotones);

        add(panelFormulario, BorderLayout.NORTH);

        // --- Panel Central: Tabla ---
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Documento", "Cargo", "Dependencia"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que editen las celdas directamente
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // --- Cargar Datos y Eventos ---
        cargarListas();
        cargarTabla();
        configurarEventos();

        setVisible(true);
    }

    private void cargarListas() {
        try {
            List<Cargo> cargos = controller.listarCargos();
            for (Cargo c : cargos) {
                cbCargo.addItem(c);
            }

            List<Dependencia> dependencias = controller.listarDependencias();
            for (Dependencia d : dependencias) {
                cbDependencia.addItem(d);
            }
        } catch (AppException e) {
            mostrarError("Error al cargar combos: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        try {
            List<Funcionario> lista = controller.listarFuncionarios();
            for (Funcionario f : lista) {
                modelo.addRow(new Object[]{
                        f.getId(),
                        f.getNombre(),
                        f.getApellido(),
                        f.getDocumento(),
                        f.getNombreCargo(),
                        f.getNombreDependencia()
                });
            }
        } catch (AppException e) {
            mostrarError("Error al cargar la tabla: " + e.getMessage());
        }
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            try {
                controller.guardarFuncionario(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtDocumento.getText(),
                        (Cargo) cbCargo.getSelectedItem(),
                        (Dependencia) cbDependencia.getSelectedItem()
                );
                JOptionPane.showMessageDialog(this, "Funcionario guardado correctamente.");
                limpiarCampos();
                cargarTabla();
            } catch (AppException ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                mostrarError("Seleccione un funcionario de la tabla para actualizar.");
                return;
            }
            try {
                controller.actualizarFuncionario(
                        idSeleccionado,
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtDocumento.getText(),
                        (Cargo) cbCargo.getSelectedItem(),
                        (Dependencia) cbDependencia.getSelectedItem()
                );
                JOptionPane.showMessageDialog(this, "Funcionario actualizado correctamente.");
                limpiarCampos();
                cargarTabla();
            } catch (AppException ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                mostrarError("Seleccione un funcionario de la tabla para eliminar.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminarlo?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.eliminarFuncionario(idSeleccionado);
                    JOptionPane.showMessageDialog(this, "Funcionario eliminado.");
                    limpiarCampos();
                    cargarTabla();
                } catch (AppException ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Evento al hacer clic en la tabla para cargar datos en los inputs
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                idSeleccionado = (int) modelo.getValueAt(fila, 0);
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                txtApellido.setText(modelo.getValueAt(fila, 2).toString());
                txtDocumento.setText(modelo.getValueAt(fila, 3).toString());
                
                // Nota: Setear el combo box visualmente requiere comparar los strings o re-cargar, 
                // para mantenerlo simple lo dejamos básico, el usuario puede volver a elegir.
            }
        });
    }

    private void limpiarCampos() {
        idSeleccionado = -1;
        txtNombre.setText("");
        txtApellido.setText("");
        txtDocumento.setText("");
        if(cbCargo.getItemCount() > 0) cbCargo.setSelectedIndex(0);
        if(cbDependencia.getItemCount() > 0) cbDependencia.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}