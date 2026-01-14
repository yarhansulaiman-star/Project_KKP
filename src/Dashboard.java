import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Dashboard extends javax.swing.JPanel {

    public Dashboard() {
        initComponents();

        // 🔄 sinkron data hari ini
        syncTotalPelangganHarian();
        syncTotalJasaHarian();
        syncTotalTransaksiHarian();

        refreshDashboard();
    }

    // ===================================================
    // 🔄 SYNC TOTAL PELANGGAN HARIAN
    // ===================================================
    public void syncTotalPelangganHarian() {
        try {
            Connection conn = config.configDB();

            String sql =
                "INSERT INTO riwayat_harian (tanggal, total_pelanggan) " +
                "VALUES (CURDATE(), (SELECT COUNT(*) FROM datapelanggan)) " +
                "ON DUPLICATE KEY UPDATE total_pelanggan = VALUES(total_pelanggan)";

            conn.prepareStatement(sql).executeUpdate();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sync pelanggan error: " + e.getMessage());
        }
    }

    // ===================================================
    // 🔄 SYNC TOTAL JASA HARIAN
    // ===================================================
    public void syncTotalJasaHarian() {
        try {
            Connection conn = config.configDB();

            String sql =
                "INSERT INTO riwayat_harian (tanggal, total_jasa) " +
                "VALUES (CURDATE(), (SELECT COUNT(*) FROM jasa)) " +
                "ON DUPLICATE KEY UPDATE total_jasa = VALUES(total_jasa)";

            conn.prepareStatement(sql).executeUpdate();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sync jasa error: " + e.getMessage());
        }
    }

    // ===================================================
    // 🔄 SYNC TOTAL TRANSAKSI HARIAN
    // ===================================================
    public void syncTotalTransaksiHarian() {
        try {
            Connection conn = config.configDB();

            String sql =
                "INSERT INTO riwayat_harian (tanggal, total_transaksi) " +
                "VALUES (CURDATE(), (SELECT COUNT(*) FROM transaksi)) " +
                "ON DUPLICATE KEY UPDATE total_transaksi = VALUES(total_transaksi)";

            conn.prepareStatement(sql).executeUpdate();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sync transaksi error: " + e.getMessage());
        }
    }

    // ===================================================
    // 📊 LOAD TABEL RIWAYAT (HARI INI)
    // ===================================================
    private void loadTableRiwayat() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Total Pelanggan");
        model.addColumn("Total Jasa");
        model.addColumn("Total Transaksi");
        model.addColumn("Total Laporan");
        model.addColumn("Tanggal");

        try {
            Connection conn = config.configDB();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT IFNULL(total_pelanggan,0), " +
                "IFNULL(total_jasa,0), " +
                "IFNULL(total_transaksi,0), " +
                "IFNULL(total_laporan,0), tanggal " +
                "FROM riwayat_harian WHERE tanggal = CURDATE()"
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getDate(5)
                });
            }

            TableRiwayat.setModel(model);
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load riwayat error: " + e.getMessage());
        }
    }

    // ===================================================
    // 📌 TOTAL PELANGGAN REALTIME
    // ===================================================
    private void loadJumlahPelanggan() {
        try {
            Connection conn = config.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM datapelanggan");

            if (rs.next()) {
                lblJumlahPelanggan.setText(rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            lblJumlahPelanggan.setText("0");
        }
    }

    // ===================================================
    // 📌 TOTAL JASA REALTIME
    // ===================================================
    private void loadJumlahJasa() {
        try {
            Connection conn = config.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM jasa");

            if (rs.next()) {
                lblJasa.setText(rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            lblJasa.setText("0");
        }
    }

    // ===================================================
    // 📌 TOTAL TRANSAKSI REALTIME
    // ===================================================
    private void loadJumlahTransaksi() {
        try {
            Connection conn = config.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM transaksi");

            if (rs.next()) {
                lblTransaksi.setText(rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            lblTransaksi.setText("0");
        }
    }

    // ===================================================
    // 🧹 HAPUS RIWAYAT SELAIN HARI INI
    // ===================================================
    private void bersihkanRiwayatJikaKosong() {
        try {
            Connection conn = config.configDB();
            String sql = "DELETE FROM riwayat_harian WHERE tanggal < CURDATE()";
            conn.prepareStatement(sql).executeUpdate();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ===================================================
    // 🔄 REFRESH DASHBOARD
    // ===================================================
    private void refreshDashboard() {
        bersihkanRiwayatJikaKosong();
        loadJumlahPelanggan();
        loadJumlahJasa();
        loadJumlahTransaksi();
        loadTableRiwayat();
    }



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableRiwayat = new jtable_custom.JTable_Custom();
        jPanelGradient1 = new jpanelgradient.JPanelGradient();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanelGradient8 = new jpanelgradient.JPanelGradient();
        jLabel8 = new javax.swing.JLabel();
        lblLaporan = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanelGradient7 = new jpanelgradient.JPanelGradient();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTransaksi = new javax.swing.JLabel();
        jPanelGradient6 = new jpanelgradient.JPanelGradient();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblJasa = new javax.swing.JLabel();
        jPanelGradient5 = new jpanelgradient.JPanelGradient();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblJumlahPelanggan = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        TableRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Total Pelanggan", "Total Jasa", "Total Transaksi", "Total Laporan", "Tanggal Transaksi"
            }
        ));
        jScrollPane1.setViewportView(TableRiwayat);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 968, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 303, 960, 430));

        jPanelGradient1.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient1.setColorStart(new java.awt.Color(0, 191, 255));
        jPanelGradient1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total Data Akumulasi Perbulan");
        jPanelGradient1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 13, 296, 44));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Riwayat Total Data PerHari");
        jPanelGradient1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 240, 943, 42));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Master Data > Dashboard");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanelGradient1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Dashboard Gauge_1.png"))); // NOI18N
        jPanelGradient1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, -1, 51));

        jPanelGradient8.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient8.setColorStart(new java.awt.Color(0, 191, 255));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Analyze.png"))); // NOI18N

        lblLaporan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        lblLaporan.setForeground(new java.awt.Color(255, 255, 255));
        lblLaporan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLaporan.setText("0");

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Laporan");

        javax.swing.GroupLayout jPanelGradient8Layout = new javax.swing.GroupLayout(jPanelGradient8);
        jPanelGradient8.setLayout(jPanelGradient8Layout);
        jPanelGradient8Layout.setHorizontalGroup(
            jPanelGradient8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelGradient8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient8Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient8Layout.createSequentialGroup()
                        .addGap(0, 35, Short.MAX_VALUE)
                        .addGroup(jPanelGradient8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient8Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(61, 61, 61))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient8Layout.createSequentialGroup()
                                .addComponent(lblLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))))))
        );
        jPanelGradient8Layout.setVerticalGroup(
            jPanelGradient8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanelGradient1.add(jPanelGradient8, new org.netbeans.lib.awtextra.AbsoluteConstraints(737, 82, -1, -1));

        jPanelGradient7.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient7.setColorStart(new java.awt.Color(0, 191, 255));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Transaction_1.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Transaksi");

        lblTransaksi.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        lblTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        lblTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTransaksi.setText("0");

        javax.swing.GroupLayout jPanelGradient7Layout = new javax.swing.GroupLayout(jPanelGradient7);
        jPanelGradient7.setLayout(jPanelGradient7Layout);
        jPanelGradient7Layout.setHorizontalGroup(
            jPanelGradient7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient7Layout.createSequentialGroup()
                .addGroup(jPanelGradient7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGradient7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelGradient7Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelGradient7Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanelGradient7Layout.setVerticalGroup(
            jPanelGradient7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanelGradient1.add(jPanelGradient7, new org.netbeans.lib.awtextra.AbsoluteConstraints(511, 82, -1, -1));

        jPanelGradient6.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient6.setColorStart(new java.awt.Color(0, 191, 255));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Tools.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Jasa");

        lblJasa.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        lblJasa.setForeground(new java.awt.Color(255, 255, 255));
        lblJasa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJasa.setText("0");

        javax.swing.GroupLayout jPanelGradient6Layout = new javax.swing.GroupLayout(jPanelGradient6);
        jPanelGradient6.setLayout(jPanelGradient6Layout);
        jPanelGradient6Layout.setHorizontalGroup(
            jPanelGradient6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGradient6Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(lblJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(jPanelGradient6Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanelGradient6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGradient6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelGradient6Layout.setVerticalGroup(
            jPanelGradient6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanelGradient1.add(jPanelGradient6, new org.netbeans.lib.awtextra.AbsoluteConstraints(282, 82, -1, -1));

        jPanelGradient5.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient5.setColorStart(new java.awt.Color(0, 191, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/People.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Data Pelanggan");

        lblJumlahPelanggan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        lblJumlahPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lblJumlahPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJumlahPelanggan.setText("0");

        javax.swing.GroupLayout jPanelGradient5Layout = new javax.swing.GroupLayout(jPanelGradient5);
        jPanelGradient5.setLayout(jPanelGradient5Layout);
        jPanelGradient5Layout.setHorizontalGroup(
            jPanelGradient5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient5Layout.createSequentialGroup()
                .addGroup(jPanelGradient5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblJumlahPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelGradient5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelGradient5Layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelGradient5Layout.createSequentialGroup()
                            .addGap(61, 61, 61)
                            .addComponent(jLabel2))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanelGradient5Layout.setVerticalGroup(
            jPanelGradient5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradient5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblJumlahPelanggan)
                .addGap(21, 21, 21))
        );

        jPanelGradient1.add(jPanelGradient5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 82, -1, -1));

        jPanel2.add(jPanelGradient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 310));

        javax.swing.GroupLayout main_panelLayout = new javax.swing.GroupLayout(main_panel);
        main_panel.setLayout(main_panelLayout);
        main_panelLayout.setHorizontalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        main_panelLayout.setVerticalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        add(main_panel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
      
    }//GEN-LAST:event_jLabel11MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jtable_custom.JTable_Custom TableRiwayat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private jpanelgradient.JPanelGradient jPanelGradient1;
    private jpanelgradient.JPanelGradient jPanelGradient5;
    private jpanelgradient.JPanelGradient jPanelGradient6;
    private jpanelgradient.JPanelGradient jPanelGradient7;
    private jpanelgradient.JPanelGradient jPanelGradient8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJasa;
    private javax.swing.JLabel lblJumlahPelanggan;
    private javax.swing.JLabel lblLaporan;
    private javax.swing.JLabel lblTransaksi;
    private javax.swing.JPanel main_panel;
    // End of variables declaration//GEN-END:variables


}
 
    

