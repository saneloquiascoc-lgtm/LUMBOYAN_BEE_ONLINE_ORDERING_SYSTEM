/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lumboyanbee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author loqui
 */
public class ShopFrame extends javax.swing.JFrame {
    

    

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ShopFrame.class.getName());
    private int qtyCigarette = 1;
    private int qtyDriedLeaf = 1;
    private int qtyTobaccoLeaf = 1;

    public ShopFrame() {

    initComponents(); 
    

    setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

    // Product prices
    jLabel9.setText("₱ 50.00");
    jLabel10.setText("₱ 30.00");
    jLabel11.setText("₱ 50.00");

    // Price color
    jLabel9.setForeground(java.awt.Color.WHITE);
    jLabel10.setForeground(java.awt.Color.WHITE);
    jLabel11.setForeground(java.awt.Color.WHITE);

    // Format Price column in cart
    jTable4.getColumnModel().getColumn(3)
        .setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                java.awt.Component c =
                    super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (value != null) {
                    setText("₱ " + String.format(
                        "%.2f",
                        Double.parseDouble(value.toString())
                    ));
                }

                return c;
            }
        });



        
        
       
        
        

        
    }

    public void addtable(
        int ID, String Name, int Qty, double Price) {

    DefaultTableModel dt =
        (DefaultTableModel) jTable4.getModel();

    int existingQty = 0;
    int existingRow = -1;

    for (int row = 0; row < dt.getRowCount(); row++) {
        int modelRow = jTable4.convertRowIndexToModel(row);

        int cartId = Integer.parseInt(
            dt.getValueAt(modelRow, 0).toString()
        );

        if (cartId == ID) {
            existingQty = Integer.parseInt(
                dt.getValueAt(modelRow, 2).toString()
            );
            existingRow = modelRow;
            break;
        }
    }

    // Read current stock from the database.
    String sql = "SELECT stock FROM product WHERE id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, ID);

        try (ResultSet rs = pst.executeQuery()) {
            if (!rs.next()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Product not found in the database.",
                    "Product Error",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int stock = rs.getInt("stock");
            int requestedQty = existingQty + Qty;

            if (Qty <= 0 || requestedQty > stock) {
                JOptionPane.showMessageDialog(
                    this,
                    "Not enough stock for " + Name + ".\n"
                    + "Available stock: " + stock + "\n"
                    + "Already in cart: " + existingQty + "\n"
                    + "Additional quantity requested: " + Qty,
                    "Insufficient Stock",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (existingRow >= 0) {
                dt.setValueAt(requestedQty, existingRow, 2);
                dt.setValueAt(Price * requestedQty, existingRow, 3);
            } else {
                dt.addRow(new Object[]{
                    ID, Name, Qty, Price * Qty
                });
            }

            cal();
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Unable to check stock:\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
private void cal() {
    DefaultTableModel model =
            (DefaultTableModel) jTable4.getModel();

    double total = 0.0;

    for (int i = 0; i < model.getRowCount(); i++) {
        total += Double.parseDouble(
                model.getValueAt(i, 3).toString()
        );
    }

   total1.setText("\u20B1 " + String.format("%.2f", total));
}

private boolean canIncreaseQuantity(int productId) {
    int currentQty;
    int cartQty = 0;

    switch (productId) {
        case 1:
            currentQty = qtyCigarette;
            break;
        case 2:
            currentQty = qtyDriedLeaf;
            break;
        case 3:
            currentQty = qtyTobaccoLeaf;
            break;
        default:
            return false;
    }

    DefaultTableModel model =
            (DefaultTableModel) jTable4.getModel();

    for (int i = 0; i < model.getRowCount(); i++) {
        int id = Integer.parseInt(
                model.getValueAt(i, 0).toString());

        if (id == productId) {
            cartQty = Integer.parseInt(
                    model.getValueAt(i, 2).toString());
            break;
        }
    }

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(
                 "SELECT stock FROM product WHERE id = ?")) {

        pst.setInt(1, productId);

        try (ResultSet rs = pst.executeQuery()) {
            if (!rs.next()) {
                return false;
            }

            int stock = rs.getInt("stock");

            if (cartQty + currentQty + 1 > stock) {
                JOptionPane.showMessageDialog(
                        this,
                        "Not enough stock available.",
                        "Stock Limit",
                        JOptionPane.WARNING_MESSAGE
                );
                return false;
            }

            return true;
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Unable to check stock: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
        return false;
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnMyOrders = new javax.swing.JButton();
        btnMyOrders1 = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        btnCheckout1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        total2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnPlusCigarette = new javax.swing.JButton();
        btnMinusCigarette = new javax.swing.JButton();
        lblQtyCigarette = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        a1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        btnMinusDriedLeaf = new javax.swing.JButton();
        btnPlusDriedLeaf = new javax.swing.JButton();
        lblQtyDriedLeaf = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        q2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblStock2 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        btnMinusTobaccoLeaf = new javax.swing.JButton();
        btnPlusTobaccoLeaf = new javax.swing.JButton();
        lblQtyTobaccoLeaf = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        q3 = new javax.swing.JButton();
        lblStock3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 2));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        btnMyOrders.setBackground(new java.awt.Color(0, 51, 51));
        btnMyOrders.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btnMyOrders.setForeground(new java.awt.Color(255, 255, 255));
        btnMyOrders.setText("My Orders");
        btnMyOrders.addActionListener(this::btnMyOrdersActionPerformed);

        btnMyOrders1.setBackground(new java.awt.Color(0, 51, 51));
        btnMyOrders1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btnMyOrders1.setForeground(new java.awt.Color(255, 255, 255));
        btnMyOrders1.setText("About Us");
        btnMyOrders1.addActionListener(this::btnMyOrders1ActionPerformed);

        btnLogout.setBackground(new java.awt.Color(0, 51, 51));
        btnLogout.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lumboyanbee/Screenshot 2026-10-09 041649.png"))); // NOI18N
        jButton4.setText("jButton4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMyOrders1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMyOrders, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMyOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMyOrders1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(712, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 150, 930));

        jLabel4.setFont(new java.awt.Font("Baskerville Old Face", 3, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cart");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 410, 100, 50));

        total1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        total1.setForeground(new java.awt.Color(255, 255, 255));
        total1.setText("₱ 00");
        jPanel1.add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 760, 110, 40));

        btnCheckout1.setBackground(new java.awt.Color(255, 153, 51));
        btnCheckout1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCheckout1.setText("Checkout");
        btnCheckout1.addActionListener(this::btnCheckout1ActionPerformed);
        jPanel1.add(btnCheckout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 760, 100, 40));

        btnDelete1.setBackground(new java.awt.Color(255, 153, 51));
        btnDelete1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnDelete1.setText("Delete");
        btnDelete1.addActionListener(this::btnDelete1ActionPerformed);
        jPanel1.add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 760, 100, 40));

        total2.setFont(new java.awt.Font("Franklin Gothic Book", 1, 24)); // NOI18N
        total2.setForeground(new java.awt.Color(255, 255, 255));
        total2.setText("Total:");
        jPanel1.add(total2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 760, 70, 40));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1310, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 1310, 10));

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1450, 180, 10, 750));

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 830, 740, 20));

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jPanel9.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 10, 710));

        jPanel10.setBackground(new java.awt.Color(0, 51, 51));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTable4.setBackground(new java.awt.Color(0, 51, 51));
        jTable4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 102, 102))); // NOI18N
        jTable4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTable4.setForeground(new java.awt.Color(255, 255, 255));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Qty", "Price"
            }
        ));
        jScrollPane1.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setHeaderValue("ID");
            jTable4.getColumnModel().getColumn(1).setHeaderValue("Name");
            jTable4.getColumnModel().getColumn(2).setHeaderValue("Qty");
            jTable4.getColumnModel().getColumn(3).setHeaderValue("Price");
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 480, 530, 270));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 920, 750, 10));

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 920, 690, 10));

        jPanel13.setBackground(new java.awt.Color(0, 51, 51));
        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 1, true));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(0, 51, 51));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Lumboy Cigarette ");
        jPanel13.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 680, 200, 30));

        btnPlusCigarette.setBackground(new java.awt.Color(0, 51, 51));
        btnPlusCigarette.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPlusCigarette.setForeground(new java.awt.Color(255, 255, 255));
        btnPlusCigarette.setText("+");
        btnPlusCigarette.addActionListener(this::btnPlusCigaretteActionPerformed);
        jPanel13.add(btnPlusCigarette, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 50, 30));

        btnMinusCigarette.setBackground(new java.awt.Color(0, 51, 51));
        btnMinusCigarette.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnMinusCigarette.setForeground(new java.awt.Color(255, 255, 255));
        btnMinusCigarette.setText("-");
        btnMinusCigarette.addActionListener(this::btnMinusCigaretteActionPerformed);
        jPanel13.add(btnMinusCigarette, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 50, 30));

        lblQtyCigarette.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblQtyCigarette.setForeground(new java.awt.Color(255, 255, 255));
        lblQtyCigarette.setText("1");
        jPanel13.add(lblQtyCigarette, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 50, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("₱ 50");
        jPanel13.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 80, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Lumboy cigarette[1pack]");
        jPanel13.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lumboyanbee/cigarette.jpg"))); // NOI18N
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel13.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 190));

        a1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Screenshot 2026-10-06 225215.png"))); // NOI18N
        a1.setMargin(new java.awt.Insets(1, 10, 3, 10));
        a1.addActionListener(this::a1ActionPerformed);
        jPanel13.add(a1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 210, 30));

        jLabel8.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cigarettes made  from lumboy  ");
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 230, 20));

        jLabel13.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("and tabacco leaves.");
        jPanel13.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 140, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("  leaves");
        jPanel13.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 70, -1));

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 230, 410));

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 810, 560, 10));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 460, 0, 420));

        jPanel16.setBackground(new java.awt.Color(0, 51, 51));
        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 1, true));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lumboyanbee/Screenshot 2026-10-11 201830.png"))); // NOI18N
        jButton5.addActionListener(this::jButton5ActionPerformed);

        btnMinusDriedLeaf.setBackground(new java.awt.Color(0, 51, 51));
        btnMinusDriedLeaf.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnMinusDriedLeaf.setForeground(new java.awt.Color(255, 255, 255));
        btnMinusDriedLeaf.setText("-");
        btnMinusDriedLeaf.addActionListener(this::btnMinusDriedLeafActionPerformed);

        btnPlusDriedLeaf.setBackground(new java.awt.Color(0, 51, 51));
        btnPlusDriedLeaf.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnPlusDriedLeaf.setForeground(new java.awt.Color(255, 255, 255));
        btnPlusDriedLeaf.setText("+");
        btnPlusDriedLeaf.addActionListener(this::btnPlusDriedLeafActionPerformed);

        lblQtyDriedLeaf.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblQtyDriedLeaf.setForeground(new java.awt.Color(255, 255, 255));
        lblQtyDriedLeaf.setText("1");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Lomboy [1bundle]");

        q2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lumboyanbee/Screenshot 2026-10-06 225215.png"))); // NOI18N
        q2.addActionListener(this::q2ActionPerformed);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("₱ 30");

        jLabel14.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Natural  dried   lumboy ");

        jLabel15.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("leaves.");

        lblStock2.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnMinusDriedLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lblQtyDriedLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlusDriedLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(q2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(lblStock2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(lblStock2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPlusDriedLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMinusDriedLeaf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQtyDriedLeaf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(q2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 410, 230, 410));

        jPanel17.setBackground(new java.awt.Color(0, 51, 51));
        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 1, true));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lumboyanbee/Screenshot 2026-10-11 201553.png"))); // NOI18N

        btnMinusTobaccoLeaf.setBackground(new java.awt.Color(0, 51, 51));
        btnMinusTobaccoLeaf.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnMinusTobaccoLeaf.setForeground(new java.awt.Color(255, 255, 255));
        btnMinusTobaccoLeaf.setText("-");
        btnMinusTobaccoLeaf.addActionListener(this::btnMinusTobaccoLeafActionPerformed);

        btnPlusTobaccoLeaf.setBackground(new java.awt.Color(0, 51, 51));
        btnPlusTobaccoLeaf.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnPlusTobaccoLeaf.setForeground(new java.awt.Color(255, 255, 255));
        btnPlusTobaccoLeaf.setText("+");
        btnPlusTobaccoLeaf.addActionListener(this::btnPlusTobaccoLeafActionPerformed);

        lblQtyTobaccoLeaf.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblQtyTobaccoLeaf.setForeground(new java.awt.Color(255, 255, 255));
        lblQtyTobaccoLeaf.setText("  1");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("₱ 50");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tabacco [1bundle]");

        jLabel16.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Natural tobacco leaves that have");

        jLabel17.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("been dried.");

        q3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lumboyanbee/Screenshot 2026-10-06 225215.png"))); // NOI18N
        q3.addActionListener(this::q3ActionPerformed);

        lblStock3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(q3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lblStock3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(btnMinusTobaccoLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblQtyTobaccoLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlusTobaccoLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lblStock3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMinusTobaccoLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlusTobaccoLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQtyTobaccoLeaf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(q3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 410, 240, 410));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lumboyanbee/Screenshot 2026-10-09 041026.png"))); // NOI18N
        jButton3.setText("jButton3");
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 410, 50, 50));

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 400, 10, 520));

        jPanel7.setBackground(new java.awt.Color(0, 51, 51));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 1, true));

        jPanel19.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 95, Short.MAX_VALUE)
        );

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Screenshot 2026-10-11 222735.png"))); // NOI18N
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(1281, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 1300, 200));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1310, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 1310, 10));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 5, -1, 830));

        jLabel1.setFont(new java.awt.Font("Baskerville Old Face", 1, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Welcome!");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 290, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void q3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_q3ActionPerformed
        // TODO add your handling code here:

        addtable(
            3,
            "Tabacco [1Bundle]",
            qtyTobaccoLeaf,
            50.00
        );

        cal();
    }//GEN-LAST:event_q3ActionPerformed

    private void btnPlusTobaccoLeafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusTobaccoLeafActionPerformed
        if (canIncreaseQuantity(3)) {
            qtyTobaccoLeaf++;
            lblQtyTobaccoLeaf.setText(
                String.valueOf(qtyTobaccoLeaf)
            );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnPlusTobaccoLeafActionPerformed

    private void btnMinusTobaccoLeafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusTobaccoLeafActionPerformed
        if (qtyTobaccoLeaf > 1) {
            qtyTobaccoLeaf--;
            lblQtyTobaccoLeaf.setText(String.valueOf(qtyTobaccoLeaf));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnMinusTobaccoLeafActionPerformed

    private void q2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_q2ActionPerformed
        addtable(
            2,
            "Lomboy [1Bundle]",
            qtyDriedLeaf,
            30.00
        );

        cal();        // TODO add your handling code here:

    }//GEN-LAST:event_q2ActionPerformed

    private void btnPlusDriedLeafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusDriedLeafActionPerformed
        if (canIncreaseQuantity(2)) {
            qtyDriedLeaf++;
            lblQtyDriedLeaf.setText(
                String.valueOf(qtyDriedLeaf)
            );
        }       // TODO add your handling code here:
    }//GEN-LAST:event_btnPlusDriedLeafActionPerformed

    private void btnMinusDriedLeafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusDriedLeafActionPerformed
        if (qtyDriedLeaf > 1) {
            qtyDriedLeaf--;
            lblQtyDriedLeaf.setText(String.valueOf(qtyDriedLeaf));
        }
    }//GEN-LAST:event_btnMinusDriedLeafActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void a1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a1ActionPerformed
        addtable(
            1,
            "Lumboy Cigarette[1pack]",
            qtyCigarette,
            50.00
        );

        cal();

    }//GEN-LAST:event_a1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnMinusCigaretteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusCigaretteActionPerformed
        if (qtyCigarette > 1) {
            qtyCigarette--;
            lblQtyCigarette.setText(String.valueOf(qtyCigarette));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnMinusCigaretteActionPerformed

    private void btnPlusCigaretteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusCigaretteActionPerformed
        if (canIncreaseQuantity(1)) {
            qtyCigarette++;
            lblQtyCigarette.setText(
                String.valueOf(qtyCigarette)
            );
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPlusCigaretteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel dt
        = (DefaultTableModel) jTable4.getModel();

        // Check if an item is selected
        int selectedRow = jTable4.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an item to delete.",
                "Delete Item",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Convert selected row to model row
        int modelRow = jTable4.convertRowIndexToModel(selectedRow);

        // Get product information
        String productName
        = dt.getValueAt(modelRow, 1).toString();

        int productID
        = Integer.parseInt(
            dt.getValueAt(modelRow, 0).toString()
        );

        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete\n"
            + "\"" + productName + "\" from your cart?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        // If user clicks No
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Delete the selected item
        dt.removeRow(modelRow);

        // Reset the quantity button
        switch (productID) {
    case 1:
        qtyCigarette = 1;
        lblQtyCigarette.setText("1");
        break;

    case 2:
        qtyDriedLeaf = 1;
        lblQtyDriedLeaf.setText("1");
        break;

    case 3:
        qtyTobaccoLeaf = 1;
        lblQtyTobaccoLeaf.setText("1");
        break;
        }

        // Recalculate total after deleting
        cal();

        // Show success message
        JOptionPane.showMessageDialog(
            this,
            "\"" + productName + "\" was removed from your cart.",
            "Item Deleted",
            JOptionPane.INFORMATION_MESSAGE
        );

    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnCheckout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckout1ActionPerformed
        // TODO add your handling code here:



        if (Session.userId == 0) {
            JOptionPane.showMessageDialog(this, "Please login first.");
            return;
        }

        DefaultTableModel model =
        (DefaultTableModel) jTable4.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Your cart is empty.");
            return;
        }

        String customerName;
        String phone;
        String address;

        double subtotal = 0.0;
        double deliveryFee = 40.00;

        for (int i = 0; i < model.getRowCount(); i++) {
            subtotal += Double.parseDouble(
                model.getValueAt(i, 3).toString()
            );
        }

        double total = subtotal + deliveryFee;

        try {
            // Get customer information
            String userSQL =
            "SELECT fullname, phone, address FROM users WHERE id = ?";

            try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(userSQL)) {

                pst.setInt(1, Session.userId);

                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(
                            this, "Customer account not found.");
                        return;
                    }

                    customerName = rs.getString("fullname");
                    phone = rs.getString("phone");
                    address = rs.getString("address");
                }
            }

            // Choose payment method
            String[] paymentOptions = {
                "GCash", "Cash on Delivery"
            };

            int choice = JOptionPane.showOptionDialog(
                this,
                "Subtotal: ₱ " + String.format("%.2f", subtotal)
                + "\nDelivery Fee: ₱ "
                + String.format("%.2f", deliveryFee)
                + "\nTOTAL: ₱ " + String.format("%.2f", total)
                + "\n\nChoose payment method:",
                "Payment Method",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                paymentOptions,
                paymentOptions[0]
            );

            if (choice < 0 || choice >= paymentOptions.length) {
                return;
            }

            String paymentMethod = paymentOptions[choice];
            String paymentStatus;

            if ("GCash".equals(paymentMethod)) {
                java.net.URL qrURL =
                getClass().getResource("/img/gcash_qr.png");

                if (qrURL == null) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Cannot find /img/gcash_qr.png.",
                        "GCash QR Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                JOptionPane.showMessageDialog(
                    this,
                    new JLabel(new ImageIcon(qrURL)),
                    "GCash Payment",
                    JOptionPane.PLAIN_MESSAGE
                );

                int paid = JOptionPane.showConfirmDialog(
                    this,
                    "Total: ₱ " + String.format("%.2f", total)
                    + "\nHave you sent the GCash payment?",
                    "Confirm Payment",
                    JOptionPane.YES_NO_OPTION
                );

                if (paid != JOptionPane.YES_OPTION) {
                    return;
                }

                paymentStatus = "For Verification";
            } else {
                paymentStatus = "Unpaid";
            }

            // Confirm order
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Customer: " + customerName
                + "\nPhone: " + phone
                + "\nAddress: " + address
                + "\nPayment: " + paymentMethod
                + "\nSubtotal: ₱ " + String.format("%.2f", subtotal)
                + "\nDelivery: ₱ " + String.format("%.2f", deliveryFee)
                + "\nTOTAL: ₱ " + String.format("%.2f", total)
                + "\n\nPlace this order?",
                "Confirm Order",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Save order and deduct stock in one transaction
            try (Connection con = DBConnection.getConnection()) {

                con.setAutoCommit(false);

                try {
                    // IMPORTANT: Adjust these column names to match
                    // your actual orders table.
                    String orderSQL =
    "INSERT INTO orderss "
    + "(user_id, total, address, phone, payment_method, payment_status) "
    + "VALUES (?, ?, ?, ?, ?, ?)";

                    int orderId;

                    try (PreparedStatement orderPst =
                        con.prepareStatement(
                            orderSQL,
                            Statement.RETURN_GENERATED_KEYS)) {

                        orderPst.setInt(1, Session.userId);
orderPst.setDouble(2, total);
orderPst.setString(3, address);
orderPst.setString(4, phone);
orderPst.setString(5, paymentMethod);
orderPst.setString(6, paymentStatus);

                        orderPst.executeUpdate();

                        try (ResultSet keys =
                            orderPst.getGeneratedKeys()) {
                            if (!keys.next()) {
                                throw new SQLException(
                                    "Could not retrieve the order ID.");
                            }

                            orderId = keys.getInt(1);
                        }
                    }

                    // Deduct stock safely
                    String stockSQL =
                    "UPDATE product "
                    + "SET stock = stock - ? "
                    + "WHERE id = ? AND stock >= ?";

                    try (PreparedStatement stockPst =
                        con.prepareStatement(stockSQL)) {

                        for (int i = 0; i < model.getRowCount(); i++) {
                            int productId = Integer.parseInt(
                                model.getValueAt(i, 0).toString());

                            String productName =
                            model.getValueAt(i, 1).toString();

                            int qty = Integer.parseInt(
                                model.getValueAt(i, 2).toString());

                            if (qty <= 0) {
                                throw new SQLException(
                                    "Invalid quantity for " + productName);
                            }

                            stockPst.setInt(1, qty);
                            stockPst.setInt(2, productId);
                            stockPst.setInt(3, qty);

                            if (stockPst.executeUpdate() != 1) {
                                throw new SQLException(
                                    "Insufficient stock for "
                                    + productName + ".");
                            }
                        }
                    }

                    // Insert order items
                    // Keep order_itemss only if that is your actual table name.
                    String itemSQL =
                    "INSERT INTO order_itemss "
                    + "(order_id, product_name, qty, subtotal) "
                    + "VALUES (?, ?, ?, ?)";

                    try (PreparedStatement itemPst =
                        con.prepareStatement(itemSQL)) {

                        for (int i = 0; i < model.getRowCount(); i++) {
                            itemPst.setInt(1, orderId);
                            itemPst.setString(
                                2, model.getValueAt(i, 1).toString());
                            itemPst.setInt(
                                3, Integer.parseInt(
                                    model.getValueAt(i, 2).toString()));
                            itemPst.setDouble(
                                4, Double.parseDouble(
                                    model.getValueAt(i, 3).toString()));

                            itemPst.executeUpdate();
                        }
                    }

                    // Commit only after all inserts and stock updates succeed
                    con.commit();

                } catch (Exception ex) {
                    try {
                        con.rollback();
                    } catch (SQLException rollbackEx) {
                        ex.addSuppressed(rollbackEx);
                    }

                    throw ex;

                } finally {
                    con.setAutoCommit(true);
                }
            }

            JOptionPane.showMessageDialog(
                this,
                "Order placed successfully!\n"
                + "Payment: " + paymentMethod
                + "\nPayment Status: " + paymentStatus,
                "Order Successful",
                JOptionPane.INFORMATION_MESSAGE
            );

            model.setRowCount(0);
            total1.setText("₱ 0.00");

            // Reset product quantity selectors
            qtyCigarette = 1;
            qtyDriedLeaf = 1;
            qtyTobaccoLeaf = 1;

            lblQtyCigarette.setText("1");
            lblQtyDriedLeaf.setText("1");
            lblQtyTobaccoLeaf.setText("1");

            a1.setText("");
            q2.setText("");
            q3.setText("");

            new MyOrdersFrame().setVisible(true);
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error placing order:\n" + e.getMessage(),
                "Checkout Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_btnCheckout1ActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        new LoginFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnMyOrders1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyOrders1ActionPerformed
        new AboutUs().setVisible(true);
    }//GEN-LAST:event_btnMyOrders1ActionPerformed

    private void btnMyOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyOrdersActionPerformed
        new MyOrdersFrame().setVisible(true);
    }//GEN-LAST:event_btnMyOrdersActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ShopFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton a1;
    private javax.swing.JButton btnCheckout1;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMinusCigarette;
    private javax.swing.JButton btnMinusDriedLeaf;
    private javax.swing.JButton btnMinusTobaccoLeaf;
    private javax.swing.JButton btnMyOrders;
    private javax.swing.JButton btnMyOrders1;
    private javax.swing.JButton btnPlusCigarette;
    private javax.swing.JButton btnPlusDriedLeaf;
    private javax.swing.JButton btnPlusTobaccoLeaf;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable4;
    private javax.swing.JLabel lblQtyCigarette;
    private javax.swing.JLabel lblQtyDriedLeaf;
    private javax.swing.JLabel lblQtyTobaccoLeaf;
    private javax.swing.JLabel lblStock2;
    private javax.swing.JLabel lblStock3;
    private javax.swing.JButton q2;
    private javax.swing.JButton q3;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total2;
    // End of variables declaration//GEN-END:variables
}
