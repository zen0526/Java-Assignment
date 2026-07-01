
package oopassignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowSorter;
import javax.swing.table.TableRowSorter;
import java.util.Arrays;


public class CheckTimeSlot extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CheckTimeSlot.class.getName());
    private static final String FILE_TS = "timeslot.txt";
    private static final String FILE_AP = "appointments.txt";
    private static final String FILE_AS = "assignment.txt";
    private final String tpNum;
    
    private final DefaultTableModel model = new DefaultTableModel();
    private final String[] columnName = { "Supervisor ID", "Date", "Time", "Status" };
    private int row = -1;
    
    
    
    public CheckTimeSlot(String tpNum) {
        this.tpNum = tpNum;
        model.setColumnIdentifiers(columnName);
        initComponents();
        jTable3.setModel(model);
        jTable1.setDefaultEditor(Object.class, null);
        loadFromFileToTable();
    }
    
    
    private void loadFromFileToTable() {
        model.setRowCount(0);

        String mySupId = findSupervisorIdForStudent(tpNum);
        if (mySupId == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "can't find your supervisor, pls contect System Admin");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TS))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", 4);
                if (p.length < 4) continue;

                TimeSlot ts = new TimeSlot( p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim());

                if (!ts.getSupervisorId().equalsIgnoreCase(mySupId)) continue;
                if (!"available".equalsIgnoreCase(ts.getStatus())) continue;

                model.addRow(new Object[] { ts.getSupervisorId(), ts.getDate(), ts.getTime(), ts.getStatus() });
            }

            if (model.getRowCount() == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Your supervisor currently has no available time slots.");
            }
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "cannot read the file");
        }

        applySortByDateThenTime();
    }

    private String findSupervisorIdForStudent(String tpNum) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_AS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] a = line.split(",", 8);
                if (a.length < 8) continue;

                String supId  = a[1].trim();
                String tpNum2   = a[3].trim();
                String status = a[7].trim();

                if (tpNum2.equalsIgnoreCase(tpNum) && status.equalsIgnoreCase("Active")) {
                    return supId;
                }
            }
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "cannot read the file");
        }
        return null;
    }

    
    private boolean appointmentExists(String tp, String sup, String date, String time) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_AP))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", 6);
                if (p.length < 6) continue;

                Appointment ap = new Appointment( p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim());
                if (tp.equalsIgnoreCase(ap.getTpNum()) && sup.equalsIgnoreCase(ap.getSupervisorID()) && date.equals(ap.getDate()) && time.equals(ap.getTime())) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading appointments.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    private boolean timeslotAvailable(String sup, String date, String time) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TS))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", 4);
                if (p.length < 4) continue;

                TimeSlot ts = new TimeSlot( p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim() );
                if (sup.equalsIgnoreCase(ts.getSupervisorId()) && date.equals(ts.getDate()) && time.equals(ts.getTime()) && "available".equalsIgnoreCase(ts.getStatus())) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading timeslot.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    
    private void removeTimeslot(String sup, String date, String time) throws IOException {
        java.util.List<String> kept = new java.util.ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TS))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", 4);
                if (p.length < 4) continue;

                TimeSlot ts = new TimeSlot( p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim() );
                if (sup.equalsIgnoreCase(ts.getSupervisorId()) && date.equals(ts.getDate()) && time.equals(ts.getTime()) && "available".equalsIgnoreCase(ts.getStatus())) {
                    continue;
                }

                kept.add(String.join(",", ts.getSupervisorId(), ts.getDate(), ts.getTime(), ts.getStatus()));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_TS))) {
            for (String s : kept) {
                bw.write(s);
                bw.newLine();
            }
        }
    }
    
    private void applySortByDateThenTime() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTable3.setRowSorter(sorter);

        sorter.setSortKeys(Arrays.asList(
            new RowSorter.SortKey(1, javax.swing.SortOrder.ASCENDING),
            new RowSorter.SortKey(2, javax.swing.SortOrder.ASCENDING)
        ));
        sorter.sort();
    }

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
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Make Appointment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable3.setModel(model
        );
        jScrollPane3.setViewportView(jTable3);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Timeslots");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Please select your preferred time for booking");

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jButton4))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton3))
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        row = jTable3.getSelectedRow();
        if (row == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a timeslot to make appointment");
            return;
        }
        int modelRow = jTable3.convertRowIndexToModel(row);

        String supSel   = String.valueOf(model.getValueAt(modelRow, 0)).trim();
        String dateSel  = String.valueOf(model.getValueAt(modelRow, 1)).trim();
        String timeSel  = String.valueOf(model.getValueAt(modelRow, 2)).trim();
        String status   = String.valueOf(model.getValueAt(modelRow, 3)).trim();
        
        TimeSlot chosen = new TimeSlot(supSel, dateSel, timeSel, status);

        if (!"available".equalsIgnoreCase(chosen.getStatus())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Only available timeslots can be booked.");
            return;
        }

        int opt = javax.swing.JOptionPane.showConfirmDialog( this, 
                "Make appointment?\nSupervisor: " + supSel + "\nDate: " + dateSel + "\nTime: " + timeSel, "Confirm", 
                javax.swing.JOptionPane.YES_NO_OPTION );
        if (opt != javax.swing.JOptionPane.YES_OPTION) return;
        
        if (appointmentExists(tpNum, chosen.getSupervisorId(), chosen.getDate(), chosen.getTime())) {
            javax.swing.JOptionPane.showMessageDialog(this, "You already have this appointment.");
            return;
        }
        if (!timeslotAvailable(chosen.getSupervisorId(), chosen.getDate(), chosen.getTime())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Not available during this timeslot.");
            loadFromFileToTable();
            return;
        }

        
        Appointment ap = new Appointment( tpNum, chosen.getSupervisorId(), chosen.getDate(), chosen.getTime(), "Pending", "-" );
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_AP, true))) {
            bw.write((String.join(",", ap.getTpNum(), ap.getSupervisorID(), ap.getDate(), ap.getTime(), ap.getStatus(), ap.getFeedback())));
            bw.newLine();
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error writing appointments.txt.");
            return;
        }

        try {
            removeTimeslot(chosen.getSupervisorId(), chosen.getDate(), chosen.getTime());
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error updating timeslot.txt." );
            return;
        }
        model.removeRow(modelRow); 
        javax.swing.JOptionPane.showMessageDialog(this, "Appointment created!");
  
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    loadFromFileToTable();
    
    JOptionPane.showMessageDialog(this, "Timeslot refreshed!");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        StudentMainPage mp = new StudentMainPage(tpNum); //to create secondpage object
        mp.setVisible(true); //makingsecondpage display
        this.dispose(); //remove the fristpage
    }//GEN-LAST:event_jButton4ActionPerformed

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
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
