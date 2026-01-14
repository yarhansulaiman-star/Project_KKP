import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Jasa extends javax.swing.JPanel {

    public Jasa() {
        initComponents();
        loadTableJasa();
    }

    
    //=============================================================
    // CLEAR FORM 
    // ============================================================
    
    
     private void clearForm() {
        txtJasa.setText("");
        txtKategori.setText("");
        txtDeskripsi.setText("");
        txtHarga.setText("");
        txtEstimasi.setText("");
        txtGaransi.setText("");
    }
   
    // ============================================================
    // RESET FORM
    // ============================================================
    private void resetForm() {
        txtJasa.setText("");
        txtKategori.setText("");
        txtDeskripsi.setText("");
        txtHarga.setText("");
        txtEstimasi.setText("");
        txtGaransi.setText("");
        tablejasa.clearSelection();
    }

    // ============================================================
    // LOAD DATA TABEL JASA
    // ============================================================
    private void loadTableJasa() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama Jasa");
        model.addColumn("Kategori");
        model.addColumn("Deskripsi");
        model.addColumn("Harga");
        model.addColumn("Estimasi");
        model.addColumn("Garansi");

        try {
            Connection conn = config.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM jasa ORDER BY id_jasa DESC"
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_jasa"),
                    rs.getString("nama_jasa"),
                    rs.getString("kategori"),
                    rs.getString("deskripsi"),
                    rs.getInt("harga"),
                    rs.getString("estimasi_waktu"),
                    rs.getString("garansi")
                });
            }

            tablejasa.setModel(model);
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load error: " + e.getMessage());
        }
    }

    // ============================================================
    // SIMPAN DATA JASA
    // ============================================================
    private void simpanDataJasa() {

        if (txtJasa.getText().isEmpty() ||
            txtKategori.getText().isEmpty() ||
            txtHarga.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Data wajib diisi!");
            return;
        }

        try {
            Connection conn = config.configDB();

            String sql =
                "INSERT INTO jasa (nama_jasa,kategori,deskripsi,harga,estimasi_waktu,garansi) " +
                "VALUES (?,?,?,?,?,?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtJasa.getText());
            pst.setString(2, txtKategori.getText());
            pst.setString(3, txtDeskripsi.getText());
            pst.setInt(4, Integer.parseInt(txtHarga.getText()));
            pst.setString(5, txtEstimasi.getText());
            pst.setString(6, txtGaransi.getText());
            pst.executeUpdate();
            conn.close();

            // 🔥 UPDATE RIWAYAT HARIAN
            updateTotalJasaPerhari();

            JOptionPane.showMessageDialog(this, "Jasa berhasil disimpan");
            loadTableJasa();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Simpan error: " + e.getMessage());
        }
    }

    // ============================================================
    // HAPUS DATA JASA
    // ============================================================
    private void hapusDataJasa() {

        int row = tablejasa.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu");
            return;
        }

        int id = Integer.parseInt(
            tablejasa.getValueAt(row, 0).toString()
        );

        int confirm = JOptionPane.showConfirmDialog(
            this, "Yakin hapus jasa?", "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = config.configDB();
                PreparedStatement pst = conn.prepareStatement(
                    "DELETE FROM jasa WHERE id_jasa=?"
                );
                pst.setInt(1, id);
                pst.executeUpdate();
                conn.close();

                // 🔥 KURANGI RIWAYAT
                kurangiJasaHarian();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadTableJasa();
                resetForm();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hapus error: " + e.getMessage());
            }
        }
    }

    // ============================================================
    // UPDATE TOTAL JASA PER HARI
    // ============================================================
    private void updateTotalJasaPerhari() {

        try {
            Connection conn = config.configDB();

            String sql =
                "INSERT INTO riwayat_harian " +
                "(total_pelanggan,total_jasa,total_transaksi,total_laporan,tanggal) " +
                "VALUES (0,1,0,0,CURDATE()) " +
                "ON DUPLICATE KEY UPDATE total_jasa = total_jasa + 1";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update harian error: " + e.getMessage());
        }
    }

    // ============================================================
    // KURANGI JASA HARIAN
    // ============================================================
    private void kurangiJasaHarian() {

        try {
            Connection conn = config.configDB();

            String sql =
                "UPDATE riwayat_harian " +
                "SET total_jasa = IF(total_jasa > 0, total_jasa - 1, 0) " +
                "WHERE tanggal = CURDATE()";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kurangi error: " + e.getMessage());
        }
    
}

   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        mainpanel = new javax.swing.JPanel();
        datajasa = new javax.swing.JPanel();
        jPanelGradient2 = new jpanelgradient.JPanelGradient();
        txtHapus = new javax.swing.JButton();
        txtUpdate = new javax.swing.JButton();
        txtTambah = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablejasa = new jtable_custom.JTable_Custom();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tambahjasa = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtJasa = new javax.swing.JTextField();
        txtKategori = new javax.swing.JTextField();
        txtDeskripsi = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtEstimasi = new javax.swing.JTextField();
        txtGaransi = new javax.swing.JTextField();
        jPanelGradient1 = new jpanelgradient.JPanelGradient();
        txtSimpan = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtBack = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        jMenuItem1.setText("jMenuItem1");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        datajasa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelGradient2.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient2.setColorStart(new java.awt.Color(0, 191, 255));
        jPanelGradient2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtHapus.setText("Hapus");
        txtHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHapusMouseClicked(evt);
            }
        });
        jPanelGradient2.add(txtHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 74, 30));

        txtUpdate.setText("Update");
        txtUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUpdateMouseClicked(evt);
            }
        });
        jPanelGradient2.add(txtUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 74, 30));

        txtTambah.setText("Tambah");
        txtTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTambahMouseClicked(evt);
            }
        });
        jPanelGradient2.add(txtTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Hasil Data");
        jPanelGradient2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 110, 20));

        tablejasa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama Jasa", "Kategori", "Deskripsi", "Harga", "Estimasi Waku", "Garansi"
            }
        ));
        jScrollPane4.setViewportView(tablejasa);

        jPanelGradient2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 211, 960, 530));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jasa    >    Dashboard");
        jPanelGradient2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Tools.png"))); // NOI18N
        jPanelGradient2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, -1, 50));

        datajasa.add(jPanelGradient2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 740));

        mainpanel.add(datajasa, "card2");

        tambahjasa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Nama Jasa");

        jLabel3.setText("Kategori");

        jLabel4.setText("Deskripsi");

        jLabel5.setText("Harga");

        jLabel6.setText("Estimasi Waktu");

        jLabel7.setText("Garansi");

        txtJasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGaransi, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstimasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGaransi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(169, 169, 169))
        );

        tambahjasa.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 145, -1, 580));

        jPanelGradient1.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient1.setColorStart(new java.awt.Color(0, 191, 255));
        jPanelGradient1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSimpan.setText("Simpan");
        txtSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSimpanMouseClicked(evt);
            }
        });
        jPanelGradient1.add(txtSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 99, -1, 33));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jasa > Dashboard");
        jPanelGradient1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(798, 34, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Tools.png"))); // NOI18N
        jPanelGradient1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Data Jasa");
        jPanelGradient1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, -1, -1));

        txtBack.setText("Back");
        txtBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBackMouseClicked(evt);
            }
        });
        jPanelGradient1.add(txtBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 74, 33));

        tambahjasa.add(jPanelGradient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 720));

        mainpanel.add(tambahjasa, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtJasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJasaActionPerformed
        
    }//GEN-LAST:event_txtJasaActionPerformed

    private void txtTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTambahMouseClicked
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        
        mainpanel.add(tambahjasa);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_txtTambahMouseClicked

    private void txtSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSimpanMouseClicked
      
        try {
        Connection conn = config.configDB();

        String sql = "INSERT INTO jasa (nama_jasa,kategori,deskripsi,harga,estimasi_waktu,garansi) " +
                     "VALUES (?,?,?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, txtJasa.getText());
        pst.setString(2, txtKategori.getText());
        pst.setString(3, txtDeskripsi.getText());
        pst.setString(4, txtHarga.getText());
        pst.setString(5, txtEstimasi.getText());
        pst.setString(6, txtGaransi.getText());
        pst.executeUpdate();

        conn.close();

        // 🔥 TAMBAH RIWAYAT HARIAN
        Dashboard db = new Dashboard();
     

        JOptionPane.showMessageDialog(null, "Jasa berhasil disimpan");

        loadTableJasa();
        resetForm();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    

    }//GEN-LAST:event_txtSimpanMouseClicked

    private void txtBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBackMouseClicked
   mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        
        mainpanel.add(datajasa);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_txtBackMouseClicked

    private void txtUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUpdateMouseClicked
   
    String nama = txtJasa.getText();
    String harga = txtHarga.getText();

    try {Connection conn = config.configDB();
        String sql = "UPDATE jasa SET nama_jasa=?, harga=? WHERE id_jasa=?";
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, nama);
        pst.setString(2, harga);
      

        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
        loadTableJasa();   // refresh tabel
        clearForm();   // reset input
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Update gagal: " + e.getMessage());
    }
    
    }//GEN-LAST:event_txtUpdateMouseClicked

    private void txtHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHapusMouseClicked

        hapusDataJasa();
       
    }//GEN-LAST:event_txtHapusMouseClicked

    private void tablejasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablejasaMouseClicked
    int row = tablejasa.rowAtPoint(evt.getPoint());

    
    txtJasa.setText(tablejasa.getValueAt(row, 1).toString());
    txtKategori.setText(tablejasa.getValueAt(row, 2).toString());
    txtDeskripsi.setText(tablejasa.getValueAt(row, 3).toString());
    txtHarga.setText(tablejasa.getValueAt(row, 4).toString());
    txtEstimasi.setText(tablejasa.getValueAt(row, 5).toString());
    txtGaransi.setText(tablejasa.getValueAt(row, 6).toString());
    }//GEN-LAST:event_tablejasaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel datajasa;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private jpanelgradient.JPanelGradient jPanelGradient1;
    private jpanelgradient.JPanelGradient jPanelGradient2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel mainpanel;
    private jtable_custom.JTable_Custom tablejasa;
    private javax.swing.JPanel tambahjasa;
    private javax.swing.JButton txtBack;
    private javax.swing.JTextField txtDeskripsi;
    private javax.swing.JTextField txtEstimasi;
    private javax.swing.JTextField txtGaransi;
    private javax.swing.JButton txtHapus;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJasa;
    private javax.swing.JTextField txtKategori;
    private javax.swing.JButton txtSimpan;
    private javax.swing.JButton txtTambah;
    private javax.swing.JButton txtUpdate;
    // End of variables declaration//GEN-END:variables
}
