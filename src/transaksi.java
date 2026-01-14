import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class transaksi extends javax.swing.JPanel {

    private final SimpleDateFormat sdfTanggal =
            new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdfTanggalJam =
            new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public transaksi() {
        initComponents();
        setTanggalHariIni();
        loadTableTransaksi();
        initHitungOtomatis();
    }

    // =====================================================
    // SET TANGGAL
    // =====================================================
    private void setTanggalHariIni() {
        txtTanggalTransaksi.setText(
                sdfTanggal.format(Calendar.getInstance().getTime())
        );
    }

    // =====================================================
    // HITUNG TRANSAKSI
    // =====================================================
    private void hitungTransaksi() {
        try {
            double total = txtTotalBayar.getText().isEmpty() ? 0
                    : Double.parseDouble(txtTotalBayar.getText());
            double bayar = txtUangBayar.getText().isEmpty() ? 0
                    : Double.parseDouble(txtUangBayar.getText());
            double diskon = txtDiskon.getText().isEmpty() ? 0
                    : Double.parseDouble(txtDiskon.getText());

            double akhir = total - diskon;
            double kembalian = bayar - akhir;

            txtKembalian.setText(kembalian >= 0 ? String.valueOf(kembalian) : "0");

        } catch (Exception e) {
            txtKembalian.setText("0");
        }
    }

    // =====================================================
    // CLEAR FORM
    // =====================================================
    private void clearForm() {
        txtID.setText("");
        txtNamaPelanggan.setText("");
        txtDiskon.setText("0");
        txtTotalBayar.setText("");
        txtUangBayar.setText("");
        txtKembalian.setText("0");
        setTanggalHariIni();
    }

    // =====================================================
    // LOAD TABLE
    // =====================================================
    private void loadTableTransaksi() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("ID Pelanggan");
        model.addColumn("Nama");
        model.addColumn("Diskon");
        model.addColumn("Total");
        model.addColumn("Bayar");
        model.addColumn("Kembalian");
        model.addColumn("Metode");
        model.addColumn("Tanggal");

        try {
            Connection conn = config.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM transaksi ORDER BY idtransaksi DESC"
            );

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("tanggal_transaksi");
                String tgl = ts != null ? sdfTanggalJam.format(ts) : "-";

                model.addRow(new Object[]{
                    rs.getInt("idtransaksi"),
                    rs.getInt("id_pelanggan"),
                    rs.getString("nama_pelanggan"),
                    rs.getDouble("diskon"),
                    rs.getDouble("total_bayar"),
                    rs.getDouble("uang_bayar"),
                    rs.getDouble("kembalian"),
                    rs.getString("metode_pembayaran"),
                    tgl
                });
            }

            tabletransaksi.setModel(model);
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Load error : " + e.getMessage());
        }
    }

// =====================================================
// SIMPAN TRANSAKSI + CETAK STRUK
// =====================================================
private void simpanTransaksi() {

    if (txtID.getText().isEmpty()
            || txtTotalBayar.getText().isEmpty()
            || txtUangBayar.getText().isEmpty()) {

        JOptionPane.showMessageDialog(this,
                "Data belum lengkap!");
        return;
    }

    Connection conn = null;

    try {
        double total = Double.parseDouble(txtTotalBayar.getText());
        double bayar = Double.parseDouble(txtUangBayar.getText());
        double diskon = txtDiskon.getText().isEmpty() ? 0
                : Double.parseDouble(txtDiskon.getText());

        double kembalian = bayar - (total - diskon);

        if (kembalian < 0) {
            JOptionPane.showMessageDialog(this,
                    "Uang bayar kurang!");
            return;
        }

        txtKembalian.setText(String.valueOf(kembalian));

        conn = config.configDB();
        conn.setAutoCommit(false);

        String sql = "INSERT INTO transaksi "
                + "(id_pelanggan, nama_pelanggan, diskon, total_bayar, "
                + "uang_bayar, kembalian, metode_pembayaran, tanggal_transaksi) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        pst.setInt(1, Integer.parseInt(txtID.getText()));
        pst.setString(2, txtNamaPelanggan.getText());
        pst.setDouble(3, diskon);
        pst.setDouble(4, total);
        pst.setDouble(5, bayar);
        pst.setDouble(6, kembalian);
        pst.setString(7, txtMetodePembayaran.getText());
        pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

        pst.executeUpdate();

        // commit simpan transaksi
        conn.commit();

        int idTransaksi = 0;

        // --- ambil id transaksi ---
        ResultSet rs = pst.getGeneratedKeys();
        if (rs != null && rs.next()) {
            idTransaksi = rs.getInt(1);
        }

        // jika database tidak support GENERATED_KEYS
        if (idTransaksi == 0) {
            Statement st = conn.createStatement();
            ResultSet rs2 = st.executeQuery(
                    "SELECT idtransaksi FROM transaksi ORDER BY idtransaksi DESC LIMIT 1"
            );
            if (rs2.next()) {
                idTransaksi = rs2.getInt("idtransaksi");
            }
            rs2.close();
            st.close();
        }

        pst.close();
        conn.setAutoCommit(true);
        conn.close();

        // ============================
        // CETAK STRUK OTOMATIS
        // ============================
        cetakStrukJasper(idTransaksi);

        JOptionPane.showMessageDialog(this,
                "Transaksi berhasil dan struk dicetak otomatis");

        loadTableTransaksi();
        clearForm();

    } catch (Exception e) {

        try {
            if (conn != null) {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (Exception ex) { }

        JOptionPane.showMessageDialog(this,
                "Simpan error : " + e.getMessage());
    }
}


   private void hapusTransaksi() {

    int baris = tabletransaksi.getSelectedRow();

    // Tidak ada baris yang dipilih
    if (baris == -1) {
        JOptionPane.showMessageDialog(this,
                "Silahkan pilih data transaksi yang akan dihapus");
        return;
    }

    // Ambil idtransaksi dari kolom pertama (index 0)
    String idTransaksi = tabletransaksi.getValueAt(baris, 0).toString();

    int konf = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus transaksi dengan ID " + idTransaksi + " ?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);

    if (konf != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        Connection conn = config.configDB();

        String sql = "DELETE FROM transaksi WHERE idtransaksi = ?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, idTransaksi);
        pst.executeUpdate();

        JOptionPane.showMessageDialog(this,
                "Data transaksi berhasil dihapus");

        // reload tabel
        loadTableTransaksi();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Gagal menghapus transaksi : " + e.getMessage());
    }
}


 private void cetakStrukJasper(int idTransaksi) {
    Connection conn = null;

    try {
        conn = config.configDB();

        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("idtransaksi", idTransaksi);

        // SESUAIKAN NAMA FILE JASPER DI SINI
        InputStream file = getClass().getResourceAsStream("/report/reportTransaksi.jasper");

        // cek kalau file tidak ditemukan
        if (file == null) {
            throw new RuntimeException("File reportTransaksi.jasper tidak ditemukan di folder /report");
        }

        JasperPrint jp = JasperFillManager.fillReport(file, parameter, conn);

        // tampilkan preview
        JasperViewer.viewReport(jp, false);

        // jika mau langsung print tanpa preview, aktifkan ini:
        // JasperPrintManager.printReport(jp, true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Gagal mencetak struk : " + e.getMessage());
    } finally {
        try { if (conn != null) conn.close(); } catch (Exception ex) {}
    }
}




    // =====================================================
    // HITUNG OTOMATIS
    // =====================================================
    private void initHitungOtomatis() {

        txtUangBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungTransaksi();
            }
        });

        txtTotalBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungTransaksi();
            }
        });

        txtDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungTransaksi();
            }
        });
    }





    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main_panel = new javax.swing.JPanel();
        jPanelGradient1 = new jpanelgradient.JPanelGradient();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtTotalBayar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDiskon = new javax.swing.JTextField();
        txtKembalian = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabletransaksi = new jtable_custom.JTable_Custom();
        btnUpdate = new javax.swing.JButton();
        btnCetakstruk = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        backDashboard = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtUangBayar = new javax.swing.JTextField();
        txtNamaPelanggan = new javax.swing.JTextField();
        txtMetodePembayaran = new javax.swing.JTextField();
        txtTanggalTransaksi = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        main_panel.setLayout(new java.awt.CardLayout());

        jPanelGradient1.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient1.setColorStart(new java.awt.Color(0, 191, 255));
        jPanelGradient1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Pelanggan");
        jPanelGradient1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 133, -1, -1));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal Transaksi");
        jPanelGradient1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 250, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Pelanggan");
        jPanelGradient1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, -1, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Metode Pembayaran");
        jPanelGradient1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 136, -1, -1));
        jPanelGradient1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 223, -1));
        jPanelGradient1.add(txtTotalBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 190, 170, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total Bayar");
        jPanelGradient1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 190, -1, -1));
        jPanelGradient1.add(txtDiskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 93, -1));
        jPanelGradient1.add(txtKembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, 172, -1));

        btnSimpan.setText("Simpan");
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
        });
        jPanelGradient1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 390, -1, -1));

        btnbatal.setText("Delete");
        btnbatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbatalMouseClicked(evt);
            }
        });
        jPanelGradient1.add(btnbatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 390, -1, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kembalian");
        jPanelGradient1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 310, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Transaksi Kasir");
        jPanelGradient1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 49, -1, -1));

        tabletransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Pelanggan", "Nama Pelanggan", "Diskon", "Uang Bayar", "Metode Pembayaran", "Total Bayar", "Tanggal Transaksi", "Kembalian"
            }
        ));
        jScrollPane1.setViewportView(tabletransaksi);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );

        jPanelGradient1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 468, 960, -1));

        btnUpdate.setText("Update");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        jPanelGradient1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, -1, -1));

        btnCetakstruk.setText("Cetak Struk Transaksi");
        btnCetakstruk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCetakstrukMouseClicked(evt);
            }
        });
        jPanelGradient1.add(btnCetakstruk, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 390, 200, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Transaction_1.png"))); // NOI18N
        jPanelGradient1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 60, 50));

        backDashboard.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        backDashboard.setForeground(new java.awt.Color(255, 255, 255));
        backDashboard.setText("Transaksi Kasir > Dashboard");
        backDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backDashboardMouseClicked(evt);
            }
        });
        jPanelGradient1.add(backDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, -1, 30));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Uang Bayar");
        jPanelGradient1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, -1, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Diskon");
        jPanelGradient1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, -1, -1));
        jPanelGradient1.add(txtUangBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 223, -1));
        jPanelGradient1.add(txtNamaPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 223, -1));
        jPanelGradient1.add(txtMetodePembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 133, 170, -1));

        txtTanggalTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTanggalTransaksiKeyReleased(evt);
            }
        });
        jPanelGradient1.add(txtTanggalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 172, -1));

        main_panel.add(jPanelGradient1, "card2");

        add(main_panel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
 
 simpanTransaksi();

    }//GEN-LAST:event_btnSimpanMouseClicked

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
   try {
    String sql = "UPDATE transaksi SET "
            + "id_pelanggan = ?, "
            + "nama_pelanggan = ?, "
            + "diskon = ?, "
            + "total_bayar = ?, "
            + "kembalian = ?, "
            + "metode_pembayaran = ? "
            + "WHERE idtransaksi = ?";

    java.sql.Connection conn = config.configDB();
    java.sql.PreparedStatement pst = conn.prepareStatement(sql);

    int idPelanggan = Integer.parseInt(txtID.getText());
    String nama = txtUangBayar.getText();
    double diskon = Double.parseDouble(txtDiskon.getText());
    double totalBayar = Double.parseDouble(txtUangBayar.getText());
    double kembalian = Double.parseDouble(txtKembalian.getText());
  

    pst.setInt(1, idPelanggan);
    pst.setString(2, nama);
    pst.setDouble(3, diskon);
    pst.setDouble(4, totalBayar);
    pst.setDouble(5, kembalian);
    pst.setString(6, txtTotalBayar.getText()); // atau JComboBox
    

    pst.executeUpdate();

    javax.swing.JOptionPane.showMessageDialog(this, "Transaksi Berhasil Diupdate");
    resetForm();

} catch (Exception e) {
    javax.swing.JOptionPane.showMessageDialog(this, "Gagal Update: " + e.getMessage());
}


    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnbatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbatalMouseClicked
      hapusTransaksi();
    }//GEN-LAST:event_btnbatalMouseClicked

    private void backDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backDashboardMouseClicked
    main_panel.removeAll();
    main_panel.add(new Dashboard());
    main_panel.repaint();
    main_panel.revalidate();
    }//GEN-LAST:event_backDashboardMouseClicked

    private void txtTanggalTransaksiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTanggalTransaksiKeyReleased
        
    }//GEN-LAST:event_txtTanggalTransaksiKeyReleased

    private void btnCetakstrukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakstrukMouseClicked

    }//GEN-LAST:event_btnCetakstrukMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backDashboard;
    private javax.swing.JButton btnCetakstruk;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnbatal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private jpanelgradient.JPanelGradient jPanelGradient1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel main_panel;
    private jtable_custom.JTable_Custom tabletransaksi;
    private javax.swing.JTextField txtDiskon;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtMetodePembayaran;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtTanggalTransaksi;
    private javax.swing.JTextField txtTotalBayar;
    private javax.swing.JTextField txtUangBayar;
    // End of variables declaration//GEN-END:variables

   private void loadData() {
    int row = tabletransaksi.getSelectedRow();
    if (row == -1) return;

    txtID.setText(tabletransaksi.getValueAt(row, 1).toString());
    txtUangBayar.setText(tabletransaksi.getValueAt(row, 2).toString());
    txtDiskon.setText(tabletransaksi.getValueAt(row, 3).toString());
    txtTotalBayar.setText(tabletransaksi.getValueAt(row, 4).toString());
    txtUangBayar.setText(tabletransaksi.getValueAt(row, 5).toString());
    txtKembalian.setText(tabletransaksi.getValueAt(row, 6).toString());

    // Metode Pembayaran
    txtTotalBayar.setText(
        tabletransaksi.getValueAt(row, 7).toString()
    );

    // TANGGAL TRANSAKSI (JTextField)
    txtKembalian.setText(
        tabletransaksi.getValueAt(row, 8).toString()
    );
   }

    private void resetForm() {
         txtID.setText("");
    txtNamaPelanggan.setText("");
    txtDiskon.setText("0");
    txtTotalBayar.setText("");
    txtUangBayar.setText("");
    txtKembalian.setText("0");
    txtMetodePembayaran.setText("");
    setTanggalHariIni();
    tabletransaksi.clearSelection();
    }

    
}