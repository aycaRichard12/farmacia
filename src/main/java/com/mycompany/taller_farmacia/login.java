/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.taller_farmacia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author HP
 */
public class login extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
        private static login instance;

      private static Connection conexionGlobal = null;
    private static final String URL = "jdbc:postgresql://localhost/db_farmacia";
    private static final String USER = "user_farmacia";
    private static final String PASS = "12e45";
    
    public login() {
        initComponents();
                inicializarConexionGlobal(); // Intenta conectar al crear el JFrame

    }
       public static login getInstance() {
        if (instance == null) {
            instance = new login();
        }
        return instance;
    }

    // Método para obtener la conexión
    public static Connection getConexionGlobal() {
        return conexionGlobal;
    }
 private void inicializarConexionGlobal() {
        try {
            if (conexionGlobal == null || conexionGlobal.isClosed()) {
                Class.forName("org.postgresql.Driver");
                conexionGlobal = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("🔹 Conexión global establecida. PID: " + obtenerPidActual());
            }
        } catch (ClassNotFoundException | SQLException e) {
            jLabel3.setText("Error al conectar a la BD");
            e.printStackTrace();
        }
    }
  private int obtenerPidActual() throws SQLException {
        try (Statement stmt = conexionGlobal.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT pg_backend_pid() AS pid")) {
            return rs.next() ? rs.getInt("pid") : -1;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jButton1.setText("iniciar sesion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Usuario:");

        jLabel2.setText("Contraseña:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(44, 44, 44)
                .addComponent(jLabel3)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
             String username = jTextField1.getText().trim();
            String password = jPasswordField1.getText().trim();
            System.out.println(password);

        if (username.isEmpty() || password.isEmpty()) {
            jLabel3.setText("Usuario y contraseña son requeridos");
            return;
        }

        try {
            // Verifica si la conexión sigue activa
            if (conexionGlobal == null || conexionGlobal.isClosed()) {
                inicializarConexionGlobal();
            }

            // Obtiene el PID actual (siempre el mismo mientras la conexión esté abierta)
            int pid = obtenerPidActual();
            System.out.println("Usando conexión existente. PID: " + pid);

            // Resto de tu lógica original...
            Integer idUsuario = obtenerIdUsuarioConValidacion(username, password);

            if (idUsuario != null) {
                boolean inserto = insertarSesionConValidacion(idUsuario, pid, true);
                jLabel3.setText(inserto ? "Conexión exitosa! PID: " + pid : "Error al registrar sesión");
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
                scheduler.schedule(() -> {
                    if(inserto){
                            PanelFarmacia otroFrame = new PanelFarmacia(idUsuario,password);
                            otroFrame.setVisible(true);
                    }
                }, 2, TimeUnit.SECONDS);

                scheduler.shutdown(); 
                
            } else {
                jLabel3.setText("Usuario o contraseña incorrectos");
            }
        } catch (SQLException e) {
            jLabel3.setText("Error de conexión");
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

public Integer obtenerIdUsuarioConValidacion(String nombreUsuario, String password) throws SQLException {
        String sql = "SELECT iduser_n FROM public.user_n WHERE \"USER\" = ? AND pass = ?";
        try (PreparedStatement pstmt = conexionGlobal.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt("iduser_n") : null;
            }
        }
    }

    public boolean insertarSesionConValidacion(int idUsuario, int pid, boolean activo) throws SQLException {
        String sql = "INSERT INTO public.sesion (iduser_n, pid, activo_s) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexionGlobal.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, pid);
            pstmt.setBoolean(3, activo);
            return pstmt.executeUpdate() == 1;
        }
    }
/*private boolean usuarioExiste(int idUsuario) {
    String sql = "SELECT 1 FROM public.user_n WHERE iduser_n = ?";
    
    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, idUsuario);
        try (ResultSet rs = pstmt.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        System.err.println("Error al verificar usuario: " + e.getMessage());
        return false;
    }
}*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
