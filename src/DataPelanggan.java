import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataPelanggan extends javax.swing.JPanel {

    public DataPelanggan() {
        initComponents();
        loadTableDataPelanggan();
    }

    // ============================================================
    // CLEAR FORM
    // ============================================================
    private void clearForm() {
        txtNama.setText("");
        txtalamat.setText("");
        txtnotlpn.setText("");
        txtemail.setText("");
    }

    // ============================================================
    // RESET FORM
    // ============================================================
    private void resetForm() {
        clearForm();
        tableData.clearSelection();
    }

    // ============================================================
    // LOAD TABLE DATA PELANGGAN
    // ============================================================
    private void loadTableDataPelanggan() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No Telp");
        model.addColumn("Email");

        try {
            Connection conn = config.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM datapelanggan ORDER BY idpelanggan DESC"
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idpelanggan"),
                    rs.getString("namapelanggan"),
                    rs.getString("alamatpelanggan"),
                    rs.getString("notlpn"),
                    rs.getString("email")
                });
            }

            tableData.setModel(model);
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load error: " + e.getMessage());
        }
    }

    // ============================================================
    // SIMPAN DATA PELANGGAN
    // ============================================================
    private void simpanDataPelanggan() {

        if (txtNama.getText().isEmpty() ||
            txtalamat.getText().isEmpty() ||
            txtnotlpn.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Data wajib diisi!");
            return;
        }

        try {
            Connection conn = config.configDB();

            String sql =
                "INSERT INTO datapelanggan (namapelanggan, alamatpelanggan, notlpn, email) " +
                "VALUES (?,?,?,?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtNama.getText());
            pst.setString(2, txtalamat.getText());
            pst.setString(3, txtnotlpn.getText());
            pst.setString(4, txtemail.getText());
            pst.executeUpdate();

            // 🔥 UPDATE RIWAYAT HARIAN (PELANGGAN)
            updateTotalPelangganPerhari(conn);

            conn.close();

            JOptionPane.showMessageDialog(this, "Data pelanggan berhasil disimpan");
            loadTableDataPelanggan();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Simpan error: " + e.getMessage());
        }
    }

    // ============================================================
    // HAPUS DATA PELANGGAN
    // ============================================================
    private void hapusDataPelanggan() {

        int row = tableData.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu");
            return;
        }

        int id = Integer.parseInt(
            tableData.getValueAt(row, 0).toString()
        );

        int confirm = JOptionPane.showConfirmDialog(
            this, "Yakin hapus data pelanggan?", "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = config.configDB();

                PreparedStatement pst = conn.prepareStatement(
                    "DELETE FROM datapelanggan WHERE idpelanggan=?"
                );
                pst.setInt(1, id);
                pst.executeUpdate();

                // 🔥 KURANGI RIWAYAT HARIAN (PELANGGAN)
                kurangiPelangganHarian(conn);

                conn.close();

                JOptionPane.showMessageDialog(this, "Data pelanggan berhasil dihapus");
                loadTableDataPelanggan();
                resetForm();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hapus error: " + e.getMessage());
            }
        }
    }

    private void updateDataPelanggan() {

    int row = tableData.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!");
        return;
    }

    if (!validasiInput()) return;

    String id = tableData.getValueAt(row, 0).toString();

    try {
        Connection conn = config.configDB();

        String sql =
            "UPDATE datapelanggan SET " +
            "namapelanggan=?, alamatpelanggan=?, notlpn=?, email=? " +
            "WHERE idpelanggan=?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, txtNama.getText());
        pst.setString(2, txtalamat.getText());
        pst.setString(3, txtnotlpn.getText());
        pst.setString(4, txtemail.getText());
        pst.setString(5, id);
        pst.executeUpdate();
        conn.close();

        JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
        loadTableDataPelanggan();
        resetForm();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Update error: " + e.getMessage());
    }
   }
        
    
    // ============================================================
    // UPDATE TOTAL PELANGGAN PER HARI
    // ============================================================
    private void updateTotalPelangganPerhari(Connection conn) throws Exception {

        String sql =
            "INSERT INTO riwayat_harian " +
            "(total_pelanggan, total_jasa, total_transaksi, total_laporan, tanggal) " +
            "VALUES (1,0,0,0,CURDATE()) " +
            "ON DUPLICATE KEY UPDATE total_pelanggan = total_pelanggan + 1";

        conn.prepareStatement(sql).executeUpdate();
    }

    // ============================================================
    // KURANGI TOTAL PELANGGAN HARIAN
    // ============================================================
    private void kurangiPelangganHarian(Connection conn) throws Exception {

        String sql =
            "UPDATE riwayat_harian " +
            "SET total_pelanggan = IF(total_pelanggan > 0, total_pelanggan - 1, 0) " +
            "WHERE tanggal = CURDATE()";

        conn.prepareStatement(sql).executeUpdate();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        Main_Panel = new javax.swing.JPanel();
        jPanelGradient1 = new jpanelgradient.JPanelGradient();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btndelete = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        txtNama = new javax.swing.JTextField();
        txtalamat = new javax.swing.JTextField();
        txtnotlpn = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new jtable_custom.JTable_Custom();

        jRadioButton1.setText("jRadioButton1");

        jLabel2.setText("jLabel2");

        setLayout(new java.awt.CardLayout());

        Main_Panel.setLayout(new java.awt.CardLayout());

        jPanelGradient1.setToolTipText("");
        jPanelGradient1.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient1.setColorStart(new java.awt.Color(0, 191, 255));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nama Pelanggan");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Alamat");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("No Telpon");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("E-mail");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Data Pelanggan");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/People.png"))); // NOI18N

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Dashboard > Data Pelanggan");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        btndelete.setText("Hapus");
        btndelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndeleteMouseClicked(evt);
            }
        });

        btnsimpan.setText("Simpan");
        btnsimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsimpanMouseClicked(evt);
            }
        });

        btnupdate.setText("Update");
        btnupdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnupdateMouseClicked(evt);
            }
        });

        txtnotlpn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnotlpnActionPerformed(evt);
            }
        });

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Pelanggan", "Alamat Pelanggan", "No Telpon", "Email"
            }
        ));
        jScrollPane1.setViewportView(tableData);

        javax.swing.GroupLayout jPanelGradient1Layout = new javax.swing.GroupLayout(jPanelGradient1);
        jPanelGradient1.setLayout(jPanelGradient1Layout);
        jPanelGradient1Layout.setHorizontalGroup(
            jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(56, 56, 56)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnotlpn, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(167, 167, 167)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnsimpan)
                .addGap(50, 50, 50)
                .addComponent(btnupdate)
                .addGap(50, 50, 50)
                .addComponent(btndelete)
                .addGap(107, 107, 107))
            .addComponent(jScrollPane1)
        );
        jPanelGradient1Layout.setVerticalGroup(
            jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient1Layout.createSequentialGroup()
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGradient1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel9))
                    .addGroup(jPanelGradient1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelGradient1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel8)))
                .addGap(51, 51, 51)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtnotlpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanelGradient1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan)
                    .addComponent(btndelete)
                    .addComponent(btnupdate))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
        );

        Main_Panel.add(jPanelGradient1, "card2");

        add(Main_Panel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtnotlpnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnotlpnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnotlpnActionPerformed

    private void btnsimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsimpanMouseClicked
      if (txtNama.getText().isEmpty() ||
        txtalamat.getText().isEmpty() ||
        txtnotlpn.getText().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Data wajib diisi!");
        return;
    }

    try {
        Connection conn = config.configDB();

        // ================= SIMPAN DATA PELANGGAN =================
        String sql =
            "INSERT INTO datapelanggan " +
            "(namapelanggan, alamatpelanggan, notlpn, email) " +
            "VALUES (?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, txtNama.getText());
        pst.setString(2, txtalamat.getText());
        pst.setString(3, txtnotlpn.getText());
        pst.setString(4, txtemail.getText());
        pst.executeUpdate();

        // ================= UPDATE RIWAYAT HARIAN =================
        updateTotalPelangganPerhari(conn);

        conn.close();

        JOptionPane.showMessageDialog(this, "Data pelanggan berhasil disimpan");
        loadTableDataPelanggan();
        resetForm();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Simpan error: " + e.getMessage());
    }
    
    }//GEN-LAST:event_btnsimpanMouseClicked

    private void btnupdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnupdateMouseClicked
 int row = tableData.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate");
        return;
    }

    if (txtNama.getText().isEmpty() ||
        txtalamat.getText().isEmpty() ||
        txtnotlpn.getText().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Data wajib diisi!");
        return;
    }

    int id = Integer.parseInt(
        tableData.getValueAt(row, 0).toString()
    );

    try {
        Connection conn = config.configDB();

        String sql =
            "UPDATE datapelanggan SET " +
            "namapelanggan=?, alamatpelanggan=?, notlpn=?, email=? " +
            "WHERE idpelanggan=?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, txtNama.getText());
        pst.setString(2, txtalamat.getText());
        pst.setString(3, txtnotlpn.getText());
        pst.setString(4, txtemail.getText());
        pst.setInt(5, id);
        pst.executeUpdate();

        conn.close();

        JOptionPane.showMessageDialog(this, "Data pelanggan berhasil diupdate");
        loadTableDataPelanggan();
        resetForm();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Update error: " + e.getMessage());
    }
    }//GEN-LAST:event_btnupdateMouseClicked

    private void btndeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteMouseClicked
   
    int row = tableData.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Yakin hapus data pelanggan?",
        "Konfirmasi",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    try {
        Connection conn = config.configDB();

        // ================= HAPUS DATA =================
        int id = Integer.parseInt(
            tableData.getValueAt(row, 0).toString()
        );

        String sql = "DELETE FROM datapelanggan WHERE idpelanggan=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();

        // ================= KURANGI RIWAYAT HARIAN =================
        kurangiTotalPelangganPerhari(conn);

        conn.close();

        JOptionPane.showMessageDialog(this, "Data pelanggan berhasil dihapus");
        loadTableDataPelanggan();
        resetForm();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Hapus error: " + e.getMessage());
    }
    }//GEN-LAST:event_btndeleteMouseClicked

    private void tableDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDataMouseClicked
    int row = tableData.getSelectedRow();
    if (row == -1) return;

    txtNama.setText(tableData.getValueAt(row, 1).toString());
    txtalamat.setText(tableData.getValueAt(row, 2).toString());
    txtnotlpn.setText(tableData.getValueAt(row, 3).toString());
    txtemail.setText(tableData.getValueAt(row, 4).toString());
    }//GEN-LAST:event_tableDataMouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
    Main_Panel.removeAll();
    Main_Panel.add(new Dashboard());
    Main_Panel.repaint();
    Main_Panel.revalidate();
    }//GEN-LAST:event_jLabel9MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Main_Panel;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private jpanelgradient.JPanelGradient jPanelGradient1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private jtable_custom.JTable_Custom tableData;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtnotlpn;
    // End of variables declaration//GEN-END:variables

    private void kurangiTotalPelangganPerhari(Connection conn) throws SQLException {
         String sql =
        "UPDATE riwayat_harian " +
        "SET total_pelanggan = IF(total_pelanggan > 0, total_pelanggan - 1, 0) " +
        "WHERE tanggal = CURDATE()";

    PreparedStatement pst = conn.prepareStatement(sql);
    pst.executeUpdate();
    }

    private boolean validasiInput() {
        if (txtNama.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama pelanggan wajib diisi!");
        txtNama.requestFocus();
        return false;
    }

    if (txtalamat.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Alamat wajib diisi!");
        txtalamat.requestFocus();
        return false;
    }

    if (txtnotlpn.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "No Telpon wajib diisi!");
        txtnotlpn.requestFocus();
        return false;
    }

    if (!txtnotlpn.getText().matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "No Telpon hanya boleh angka!");
        txtnotlpn.requestFocus();
        return false;
    }

    if (txtemail.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Email wajib diisi!");
        txtemail.requestFocus();
        return false;
    }

    if (!txtemail.getText().contains("@")) {
        JOptionPane.showMessageDialog(this, "Format email tidak valid!");
        txtemail.requestFocus();
        return false;
    }

    return true;
    }
}
