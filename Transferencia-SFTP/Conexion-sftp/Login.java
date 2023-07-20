package sftpv2;

import Clases.ConexionORCL;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

//Clase encargada de validar usuario y contraseña a travez de una ventana de login.
public class Login extends javax.swing.JFrame {
    //Referencia a clase ConexionORCL
    ConexionORCL cc= new ConexionORCL();
    //Declara variable cn de tipo Connection para asignarle la función "conexion" de la clase ConexionORCL
    Connection cn= cc.conexion(); 
    
    Statement st = null;
    ResultSet rs = null;
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    //Variables en private static para poder ser obtenidas en otras clases.
    private static String USUARIO;
    private static String CONTRASEÑA;
    
    //Getter and Setter para obtener valor de USUARIO y CONTRASEÑA permitiendo a las varibles ser utilizadas en otras clases.
    
    public Login(String USUARIO, String CONTRASEÑA) {
        this.USUARIO = USUARIO;
        this.CONTRASEÑA = CONTRASEÑA;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getCONTRASEÑA() {
        return CONTRASEÑA;
    }

    public void setCONTRASEÑA(String CONTRASEÑA) {
        this.CONTRASEÑA = CONTRASEÑA;
    }
    
    String validar_login(String user , String pass){
        //Función que valida el usuario y la contraseña
        String buscar = "";
        try {
            //Sentencia SQL que devuelve un registro de la base si el usuario y contraseña existen.
            String sql = "SELECT * FROM SRCEI.LOGIN_SFTP WHERE V_LOGIN_USER='"+user+"' and V_LOGIN_PASS = '"+pass+"'";
            
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            //Mientras haya coincidencias asigna el tercer valor del registro(V_Login_Estado) del login a variable buscar.
            while(rs.next()){                
                buscar=rs.getString(3);           
            }
        }
        catch(Exception e){
            System.out.println("Falló la carga del sql \n"+e);              
        }
        if (buscar.equals("0")){ 
            //Acción a realizar si el usuario ingresado esta habilitado.
         TXT_USER.setText("");
         TXT_PASS.setText("");
         this.dispose();
        Ventana ventana = new Ventana();             
        ventana.setVisible(true);
        }
        else if(buscar.equals("1")){
            //Acción a realizar si el usuario ingresado esta deshabilitado.
            JOptionPane.showMessageDialog(null, "Usuario Inhabilitado."); 
        }
        else{
            //Acción a realizar si el usuario o contraseña ingresada no existe.
         JOptionPane.showMessageDialog(null, "Usuario/Contraseña Incorrecto.");
        }
        return user;
    }
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BTN_ACEPTAR = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TXT_USER = new javax.swing.JTextField();
        TXT_PASS = new javax.swing.JPasswordField();
        BTN_SALIR = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BTN_ACEPTAR.setText("Aceptar");
        BTN_ACEPTAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_ACEPTARActionPerformed(evt);
            }
        });
        BTN_ACEPTAR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BTN_ACEPTARKeyTyped(evt);
            }
        });

        jLabel1.setText("Usuario");

        jLabel2.setText("Contraseña");

        TXT_USER.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TXT_USERKeyTyped(evt);
            }
        });

        TXT_PASS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TXT_PASSKeyTyped(evt);
            }
        });

        BTN_SALIR.setText("Salir");
        BTN_SALIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_SALIRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BTN_ACEPTAR)
                        .addGap(74, 74, 74)
                        .addComponent(BTN_SALIR, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TXT_USER, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(TXT_PASS))
                        .addContainerGap(51, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TXT_USER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TXT_PASS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_ACEPTAR)
                    .addComponent(BTN_SALIR))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTN_ACEPTARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_ACEPTARActionPerformed
        //Llama a función  validar_login al presionar boton activar.
        USUARIO = TXT_USER.getText();
        CONTRASEÑA = TXT_PASS.getText();
        validar_login(USUARIO,CONTRASEÑA);
    }//GEN-LAST:event_BTN_ACEPTARActionPerformed

    private void BTN_ACEPTARKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BTN_ACEPTARKeyTyped
        //Para activar boton aceptar con Enter.
        char cTeclaPresionada = evt.getKeyChar();
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
        BTN_ACEPTAR.doClick();
        }        
    }//GEN-LAST:event_BTN_ACEPTARKeyTyped

    private void TXT_USERKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TXT_USERKeyTyped
        //Para activar boton aceptar con Enter mientras el foco este en usuario.
        char cTeclaPresionada = evt.getKeyChar();
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
        BTN_ACEPTAR.doClick();
        }
    }//GEN-LAST:event_TXT_USERKeyTyped

    private void TXT_PASSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TXT_PASSKeyTyped
        //Para activar boton aceptar con Enter mientras el foco este en contraseña.
        char cTeclaPresionada = evt.getKeyChar();
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
        BTN_ACEPTAR.doClick();
        }
    }//GEN-LAST:event_TXT_PASSKeyTyped

    private void BTN_SALIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_SALIRActionPerformed
        //Boton para salir del programa.
        System.exit(0);
    }//GEN-LAST:event_BTN_SALIRActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_ACEPTAR;
    private javax.swing.JButton BTN_SALIR;
    private javax.swing.JPasswordField TXT_PASS;
    private javax.swing.JTextField TXT_USER;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
