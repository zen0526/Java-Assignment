/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package oopassignment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JOptionPane;


public class AppointmentGUI extends javax.swing.JFrame {
    private String supervisorID;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AppointmentGUI.class.getName());

    /**
     * Creates new form AppointmentGUI
     */
    public AppointmentGUI(String supervisorID) {
        initComponents();
        this.supervisorID = supervisorID;
        jLabel1.setText("Supervisor ID: " + supervisorID);
        setupTable();
        loadappointmentsData();
    }
    
    private void setupTable() {
    String[] columnNames = {"tpNum","supervisorID", "Date", "Time", "Status","feedback"};
    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columnNames, 0);
    jTable1.setModel(model);
    
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);   // tpNum
    jTable1.getColumnModel().getColumn(1).setPreferredWidth(20);  // supervisorID
    jTable1.getColumnModel().getColumn(2).setPreferredWidth(30);  // Date
    jTable1.getColumnModel().getColumn(3).setPreferredWidth(30);   // Time
    jTable1.getColumnModel().getColumn(4).setPreferredWidth(30);   // Status
    jTable1.getColumnModel().getColumn(5).setPreferredWidth(700);
     }
    
    
    private void loadappointmentsData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        try (BufferedReader br = new BufferedReader(new FileReader("appointments.txt"))) {
            String line;          
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String tpNum = parts[0].trim();
                    String supID = parts[1].trim();
                    String date = parts[2].trim();
                    String time = parts[3].trim();
                    String status = parts[4].trim();
                    String feedback = parts[5].trim();

                    
                    if (this.supervisorID.equals(supID) && !"Completed".equalsIgnoreCase(status)) {
                        model.addRow(new Object[]{tpNum,supID,date,time,status,feedback});
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("can not read appointments.txt: " + e.getMessage());
        }
    }
    
//    private void updateStatus(String newStatus) {
//    int row = jTable1.getSelectedRow();
//    if (row < 0) {
//        JOptionPane.showMessageDialog(this, "Please select a row first.");
//        return;
//    }
//
//    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//    String tpNum = model.getValueAt(row, 0).toString();
//    String date = model.getValueAt(row, 2).toString();
//    String time = model.getValueAt(row, 3).toString();
//
//    model.setValueAt(newStatus, row, 4);
//
//    
//    List<String> lines = new ArrayList<>();
//    try (BufferedReader br = new BufferedReader(new FileReader("appointments.txt"))) {
//        String line;
//        while ((line = br.readLine()) != null) {
//            String[] parts = line.split(",");
//            if (parts.length == 6 &&
//                parts[0].trim().equals(tpNum) &&
//                parts[2].trim().equals(date) &&
//                parts[3].trim().equals(time)) {
//                parts[4] = newStatus; 
//                line = String.join(",", parts);
//            }
//            lines.add(line);
//        }
//    } catch (IOException e) {
//        JOptionPane.showMessageDialog(this, "Failed to read appointments.txt: " + e.getMessage());
//    }
//
//    try (PrintWriter pw = new PrintWriter(new FileWriter("appointments.txt"))) {
//        for (String l : lines) {
//            pw.println(l);
//        }
//    } catch (IOException e) {
//        JOptionPane.showMessageDialog(this, "Failed to write appointments.txt: " + e.getMessage());
//    }
//
//    if ("Completed".equalsIgnoreCase(newStatus)) {
//        model.removeRow(row);
//        JOptionPane.showMessageDialog(this, "Appointment completed .");
//    } else {
//        model.setValueAt(newStatus, row, 4);
//        JOptionPane.showMessageDialog(this, "Status updated to " + newStatus);
//    }
//    
//    
//    
//    
//}
//
    
    
    private void updateStatus(String newStatus) {
    int row = jTable1.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Please select a row first.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    String tpNum = model.getValueAt(row, 0).toString();
    String date = model.getValueAt(row, 2).toString();
    String time = model.getValueAt(row, 3).toString();

    model.setValueAt(newStatus, row, 4); // 更新界面状态

    List<Appointment> appointments = new ArrayList<>();

    // ✅ 读取文件成 Appointment 对象列表
    try (BufferedReader br = new BufferedReader(new FileReader("appointments.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                Appointment a = new Appointment(
                    parts[0].trim(), 
                    parts[1].trim(), 
                    parts[2].trim(), 
                    parts[3].trim(),
                    parts[4].trim(), 
                    parts[5].trim());
                


                if (a.getTpNum().equals(tpNum) && a.getDate().equals(date) && a.getTime().equals(time)) {
                    a.setStatus(newStatus);
                }

                appointments.add(a);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Failed to read appointments.txt: " + e.getMessage());
    }

    try (PrintWriter pw = new PrintWriter(new FileWriter("appointments.txt"))) {
        for (Appointment a : appointments) {
            pw.println(a.getTpNum()+ "," +
            a.getSupervisorID() + "," +
            a.getDate() + "," +a.getTime() +"," +
            a.getStatus() + "," +
            a.getFeedback());
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Failed to write appointments.txt: " + e.getMessage());
    }

    if ("Completed".equalsIgnoreCase(newStatus)) {
        model.removeRow(row);
        JOptionPane.showMessageDialog(this, "Appointment completed.");
    } else {
        JOptionPane.showMessageDialog(this, "Status updated to " + newStatus);
    }
}

    
//jTextField1
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Approved");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Completed");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("SupervisorID: ");

        jButton3.setText("Rejected");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1339, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(52, 52, 52)
                        .addComponent(jButton4)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    updateStatus("Approved");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
//     Mainpage sp = new Mainpage();
//      sp.setVisible(true);
//      this.dispose();

      new Mainpage(supervisorID).setVisible(true);
      this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    updateStatus("Completed");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    updateStatus("Rejected");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
