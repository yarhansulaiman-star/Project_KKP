
import java.awt.CardLayout;
import java.awt.Container;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class beranda extends javax.swing.JFrame {
    
    
  

  
    public beranda() {
        initComponents();
      

    setLocationRelativeTo(null); // biar muncul di tengah laya
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_kiri = new javax.swing.JPanel();
        jPanelGradient2 = new jpanelgradient.JPanelGradient();
        txtDashboard = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtJasa = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDataPelanggan = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTransaksi = new javax.swing.JLabel();
        txtLogout = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtLaporan = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pn_kanan = new javax.swing.JPanel();
        JpanelNavbar = new javax.swing.JPanel();
        jPanelGradient1 = new jpanelgradient.JPanelGradient();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pn_dasar = new javax.swing.JPanel();
        pn_utama = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pn_kiri.setBackground(new java.awt.Color(255, 255, 255));

        jPanelGradient2.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient2.setColorStart(new java.awt.Color(0, 191, 255));
        jPanelGradient2.setDoubleBuffered(false);

        txtDashboard.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        txtDashboard.setForeground(new java.awt.Color(255, 255, 255));
        txtDashboard.setText("Dashboard");
        txtDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDashboardMouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Dashboard Gauge_1.png"))); // NOI18N

        txtJasa.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        txtJasa.setForeground(new java.awt.Color(255, 255, 255));
        txtJasa.setText("Jasa");
        txtJasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtJasaMouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/People.png"))); // NOI18N

        txtDataPelanggan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        txtDataPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        txtDataPelanggan.setText("Data Pelanggan");
        txtDataPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataPelangganMouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Tools.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Analyze.png"))); // NOI18N

        txtTransaksi.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        txtTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        txtTransaksi.setText("Transaksi");
        txtTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTransaksiMouseClicked(evt);
            }
        });

        txtLogout.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        txtLogout.setForeground(new java.awt.Color(255, 255, 255));
        txtLogout.setText("Logout");
        txtLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLogoutMouseClicked(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Move Up.png"))); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Profile.png"))); // NOI18N

        txtLaporan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        txtLaporan.setForeground(new java.awt.Color(255, 255, 255));
        txtLaporan.setText("Laporan");
        txtLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLaporanMouseClicked(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Transaction_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanelGradient2Layout = new javax.swing.GroupLayout(jPanelGradient2);
        jPanelGradient2.setLayout(jPanelGradient2Layout);
        jPanelGradient2Layout.setHorizontalGroup(
            jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLaporan)
                    .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtDataPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtJasa)
                        .addComponent(txtTransaksi)
                        .addComponent(jLabel14)
                        .addComponent(txtDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtLogout))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanelGradient2Layout.setVerticalGroup(
            jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(55, 55, 55)
                .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelGradient2Layout.createSequentialGroup()
                        .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelGradient2Layout.createSequentialGroup()
                                .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanelGradient2Layout.createSequentialGroup()
                                        .addComponent(txtDashboard)
                                        .addGap(84, 84, 84)
                                        .addComponent(txtDataPelanggan)
                                        .addGap(48, 48, 48))
                                    .addGroup(jPanelGradient2Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel6)
                                        .addGap(28, 28, 28)))
                                .addGap(42, 42, 42)
                                .addComponent(txtJasa)
                                .addGap(21, 21, 21))
                            .addComponent(jLabel9))
                        .addGap(75, 75, 75)
                        .addComponent(txtTransaksi)
                        .addGap(19, 19, 19))
                    .addComponent(jLabel11))
                .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGradient2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel10))
                    .addGroup(jPanelGradient2Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(txtLaporan)))
                .addGroup(jPanelGradient2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGradient2Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelGradient2Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(txtLogout)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_kiriLayout = new javax.swing.GroupLayout(pn_kiri);
        pn_kiri.setLayout(pn_kiriLayout);
        pn_kiriLayout.setHorizontalGroup(
            pn_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_kiriLayout.createSequentialGroup()
                .addComponent(jPanelGradient2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_kiriLayout.setVerticalGroup(
            pn_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_kiriLayout.createSequentialGroup()
                .addComponent(jPanelGradient2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        getContentPane().add(pn_kiri, java.awt.BorderLayout.LINE_START);

        pn_kanan.setLayout(new java.awt.BorderLayout());

        JpanelNavbar.setBackground(new java.awt.Color(255, 255, 255));

        jPanelGradient1.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient1.setColorStart(new java.awt.Color(0, 191, 255));
        jPanelGradient1.setPreferredSize(new java.awt.Dimension(1180, 70));

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Giant Computer Store");

        javax.swing.GroupLayout jPanelGradient1Layout = new javax.swing.GroupLayout(jPanelGradient1);
        jPanelGradient1.setLayout(jPanelGradient1Layout);
        jPanelGradient1Layout.setHorizontalGroup(
            jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient1Layout.createSequentialGroup()
                .addGap(374, 374, 374)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(378, Short.MAX_VALUE))
        );
        jPanelGradient1Layout.setVerticalGroup(
            jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JpanelNavbarLayout = new javax.swing.GroupLayout(JpanelNavbar);
        JpanelNavbar.setLayout(JpanelNavbarLayout);
        JpanelNavbarLayout.setHorizontalGroup(
            JpanelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpanelNavbarLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jPanelGradient1, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        JpanelNavbarLayout.setVerticalGroup(
            JpanelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpanelNavbarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelGradient1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pn_kanan.add(JpanelNavbar, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        pn_dasar.setBackground(new java.awt.Color(255, 255, 255));

        pn_utama.setBackground(new java.awt.Color(255, 255, 255));
        pn_utama.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pn_dasarLayout = new javax.swing.GroupLayout(pn_dasar);
        pn_dasar.setLayout(pn_dasarLayout);
        pn_dasarLayout.setHorizontalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_utama, javax.swing.GroupLayout.PREFERRED_SIZE, 948, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_dasarLayout.setVerticalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addComponent(pn_utama, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_dasar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pn_dasar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(35, 35, 35))
        );

        pn_kanan.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(pn_kanan, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDashboardMouseClicked
pn_utama.removeAll();
pn_utama.add(new Dashboard());       
pn_utama.repaint();
pn_utama.revalidate();

    }//GEN-LAST:event_txtDashboardMouseClicked

    private void txtDataPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataPelangganMouseClicked
 pn_utama.removeAll();
 pn_utama.add(new DataPelanggan());
 pn_utama.repaint();
 pn_utama.revalidate();
    }//GEN-LAST:event_txtDataPelangganMouseClicked

    private void txtJasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtJasaMouseClicked
pn_utama.removeAll();
pn_utama.add(new Jasa());       
pn_utama.repaint();
pn_utama.revalidate();
    }//GEN-LAST:event_txtJasaMouseClicked

    private void txtTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTransaksiMouseClicked
pn_utama.removeAll();
pn_utama.add(new transaksi());       
pn_utama.repaint();
pn_utama.revalidate();
    }//GEN-LAST:event_txtTransaksiMouseClicked

    private void txtLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLogoutMouseClicked
dispose(); 
    }//GEN-LAST:event_txtLogoutMouseClicked

    private void txtLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLaporanMouseClicked
pn_utama.removeAll();
pn_utama.add(new laporanbulanan());       
pn_utama.repaint();
pn_utama.revalidate();
    }//GEN-LAST:event_txtLaporanMouseClicked

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
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new beranda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JpanelNavbar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private jpanelgradient.JPanelGradient jPanelGradient1;
    private jpanelgradient.JPanelGradient jPanelGradient2;
    private javax.swing.JPanel pn_dasar;
    private javax.swing.JPanel pn_kanan;
    private javax.swing.JPanel pn_kiri;
    private javax.swing.JPanel pn_utama;
    private javax.swing.JLabel txtDashboard;
    private javax.swing.JLabel txtDataPelanggan;
    private javax.swing.JLabel txtJasa;
    private javax.swing.JLabel txtLaporan;
    private javax.swing.JLabel txtLogout;
    private javax.swing.JLabel txtTransaksi;
    // End of variables declaration//GEN-END:variables
}
