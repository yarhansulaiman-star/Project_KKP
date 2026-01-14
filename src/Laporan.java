
public class Laporan extends javax.swing.JPanel {

  
    public Laporan() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        DataLaporan = new javax.swing.JPanel();
        jPanelGradient1 = new jpanelgradient.JPanelGradient();
        btnBatal = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Custom1 = new jtable_custom.JTable_Custom();

        setLayout(new java.awt.CardLayout());

        MainPanel.setLayout(new java.awt.CardLayout());

        jPanelGradient1.setColorEnd(new java.awt.Color(0, 31, 63));
        jPanelGradient1.setColorStart(new java.awt.Color(0, 191, 255));
        jPanelGradient1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBatal.setText("Hapus");
        jPanelGradient1.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 80, -1));

        btnTambah.setText("Tambah");
        jPanelGradient1.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, -1, -1));

        jTable_Custom1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable_Custom1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout DataLaporanLayout = new javax.swing.GroupLayout(DataLaporan);
        DataLaporan.setLayout(DataLaporanLayout);
        DataLaporanLayout.setHorizontalGroup(
            DataLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelGradient1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DataLaporanLayout.setVerticalGroup(
            DataLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataLaporanLayout.createSequentialGroup()
                .addComponent(jPanelGradient1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainPanel.add(DataLaporan, "card2");

        add(MainPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DataLaporan;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnTambah;
    private javax.swing.JPanel jPanel1;
    private jpanelgradient.JPanelGradient jPanelGradient1;
    private javax.swing.JScrollPane jScrollPane1;
    private jtable_custom.JTable_Custom jTable_Custom1;
    // End of variables declaration//GEN-END:variables
}
