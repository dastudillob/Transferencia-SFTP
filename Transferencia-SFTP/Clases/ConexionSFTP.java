package Clases;

/**
 *
 * @author Daniel
 */
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase encargada de establecer conexion y ejecutar comandos SFTP.
 */
public class ConexionSFTP {

    private Session session;

    public void connect(String user, String password, String host, int port)
            throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();

            this.session = jsch.getSession(user, host, port);
            this.session.setPassword(password);
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.connect();
        } else {
            throw new IllegalAccessException("Sesion SFTP ya iniciada.");
        }
    }
    
    Connection conectar=null;
    public Connection conexionORCL(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conectar=DriverManager.getConnection("jdbc:oracle:thin:@id","user","user");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return conectar;
    }
            

    public final void AgregarArchivo(String ftpDir, String ArchDir,
            String ArchNom) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        if (this.session != null && this.session.isConnected()) {

            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            channelSftp.connect();
            channelSftp.cd(ftpDir);

            System.out.println(String.format("Creando archivo %s en el "
                    + "directorio %s", ArchNom, ftpDir));
            channelSftp.put(ArchDir, ArchNom);

            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }

    public final void disconnect() {
        this.session.disconnect();
    }

    public final void renombrar(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        System.out.print(nombrearch);
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand("cd /BDReplicacion/oradata/datos_txt/data_in ; mv " + nombrearch + " " + nombrearch + ".txt");
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }
        channelExec.disconnect();
    }

    public final void crearctl(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand("touch /BDReplicacion/oradata/datos_txt/data_in/" + nombrearch + ".ctl");
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }

        channelExec.disconnect();
    }

    public final void escribirctl(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand("echo \" LOAD DATA CHARACTERSET UTF8 INFILE './" + nombrearch + ".txt' BADFILE './" + nombrearch + ".bad' DISCARDFILE './" + nombrearch + ".dsc' INTO TABLE BT_RUN_SUSESO TRUNCATE FIELDS TERMINATED BY ';'  TRAILING NULLCOLS(RUN) \" " + " >> /BDReplicacion/oradata/datos_txt/data_in/" + nombrearch + ".ctl");
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }
        channelExec.disconnect();
    }

    public final void sqlload(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
              ChannelExec channelExec = (ChannelExec)session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        /*channelExec.setCommand("sqlldr CONTROL=/BDReplicacion/oradata/datos_txt/data_in/" + nombrearch + ".ctl, USERID=srcei,iecrs");
*/
        ((ChannelExec)channelExec).setPty(true);
        channelExec.setCommand("cd /BDReplicacion/oradata/datos_txt/data_in ; bash " +nombrearch+".sh");

        channelExec.connect();
 
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }

        channelExec.disconnect();
  
    }
    
        public final void crearSh(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand("touch /BDReplicacion/oradata/datos_txt/data_in/" + nombrearch + ".sh");
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }

        channelExec.disconnect();
    }
        
        
        public final void escribirSh(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand("echo \"export ORACLE_SID=prod; export ORACLE_HOME=/u01/app/oracle/product/12.1.0/db_1; export ORACLE_BASE=/u01/app/oracle; export ORACLE_HOSTNAME=GoldenGate-Replicacion-Prod.localdomain; export PATH=/usr/lib64/qt-3.3/bin:/usr/local/bin:/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/sbin:/home/cargauser/bin:/u01/app/oracle/product/12.1.0/db_1/bin; sqlldr control=" + nombrearch + " userid=srcei/iecrs \" ERRORs=300000 " + " >> /BDReplicacion/oradata/datos_txt/data_in/" + nombrearch + ".sh");
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }

        channelExec.disconnect();
    }
        
       public final void Borrar_temporales(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand("cd /BDReplicacion/oradata/datos_txt/data_in/ ; rm " + nombrearch + ".sh ; rm " + nombrearch + ".log ; rm " + nombrearch + ".txt ; rm " + nombrearch + ".ctl");
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }

        channelExec.disconnect();
    }        
      
       public final void DescargarPorsftp(String DirDesc, String nombrearch, String DirArch)
            throws Exception {
        if (this.session != null && this.session.isConnected()) {

            ChannelSftp channelSftp = (ChannelSftp) this.session.
            openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(DirArch);

            OutputStream os = new BufferedOutputStream(new FileOutputStream(DirDesc+nombrearch));
 
            channelSftp.get(nombrearch, os);

            channelSftp.exit();
            channelSftp.disconnect();
            
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }
       
            public final void DescargarPorsftp2(String DirDesc, String nombrearch, String DirArch)
            throws Exception {
        if (this.session != null && this.session.isConnected()) {

            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
                        
            InputStream in = channelExec.getInputStream();
            channelExec.setCommand("cd /BDReplicacion/oradata/datos_txt/data_in ; if [ -e \""+ nombrearch+ "\" ] ; then echo \"archivo existe\" ; else echo \"archivo NO existe\" ; fi");
            channelExec.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String linea = null;
            int index = 0;
            linea = reader.readLine();
            /*while ((linea = reader.readLine()) != null) {
                System.out.println(++index + " : " + linea);
            swExiste = linea;
            }*/
            channelExec.disconnect();
            System.out.println("linea: "+ linea);
            
            
            
            ChannelSftp channelSftp = (ChannelSftp) this.session.
            openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(DirArch);
            
            
                  
            if(linea.equals("archivo existe")){
            System.out.println("descargare");    
            OutputStream os = new BufferedOutputStream(new FileOutputStream(DirDesc+nombrearch));
            channelSftp.get(nombrearch, os);
            }
            
            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }
    
    
    public final void Borrar_temporales_out(String nombrearch) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand("cd /BDReplicacion/oradata/datos_txt/data_out/ ; rm " + nombrearch + "e");
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linea = null;
        int index = 0;

        while ((linea = reader.readLine()) != null) {
            System.out.println(++index + " : " + linea);
        }
    }          
}