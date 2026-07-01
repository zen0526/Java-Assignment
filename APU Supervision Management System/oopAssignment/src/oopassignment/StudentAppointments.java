
package oopassignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class StudentAppointments extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StudentAppointments.class.getName());
    private static final String FILE_TS = "timeslot.txt";
    private static final String FILE_AP = "appointments.txt";
    private static final String FILE_AS = "assignment.txt";
    private final String tpNum;

    private final DefaultTableModel model = new DefaultTableModel();
    private final String[] columnName = {"TP Num","Supervisor ID", "Date", "Time", "Status"};
    
    public StudentAppointments(String tpNum) {
        model.setColumnIdentifiers(columnName);
        this.tpNum = tpNum;
        initComponents();
        jTable1.setModel(model);
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int viewRow = jTable1.getSelectedRow();
            int modelRow = (viewRow >= 0) ? jTable1.convertRowIndexToModel(viewRow) : -1;
            updateActionButtonsFor(modelRow);
        });
        applySortByDateThenTime();
        loadFromFile();
        updateActionButtonsFor(-1);
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
    }

    private void loadFromFile() {
        model.setRowCount(0);
        
        String mySupId = findSupervisorIdForStudent(tpNum);
        if (mySupId == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "can't find your supervisor, pls contect System Admin");
            return;
        }

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_AP))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", 6);
                if (p.length < 6) continue;

                Appointment ap = new Appointment( p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim());
                
                if (!ap.getTpNum().equalsIgnoreCase(this.tpNum)) continue;
                if (!ap.getSupervisorID().equalsIgnoreCase(mySupId)) continue;

                model.addRow(new Object[]{ ap.getTpNum(), ap.getSupervisorID(), ap.getDate(), ap.getTime(), ap.getStatus() });
            }

            if (model.getRowCount() == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "There are currently no appointment for you");
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
            javax.swing.JOptionPane.showMessageDialog(this, "cannot read the file: " + e.getMessage());
        }
        return null;
        
    }
    
    private void applySortByDateThenTime() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTable1.setRowSorter(sorter);
        sorter.setSortKeys(Arrays.asList(
            new RowSorter.SortKey(2, javax.swing.SortOrder.ASCENDING),
            new RowSorter.SortKey(3, javax.swing.SortOrder.ASCENDING)));
        sorter.sort();
    }
    
    private boolean isLockedRow(int modelRow) {
        if (modelRow < 0 || modelRow >= model.getRowCount()) return false;
        Object statusObj = model.getValueAt(modelRow, 4);
        String status = statusObj == null ? "" : statusObj.toString().replace('\u00A0', ' ').trim();
        return "completed".equalsIgnoreCase(status) || "rejected".equalsIgnoreCase(status);
    }

    private void updateActionButtonsFor(int modelRow) {
        boolean locked = isLockedRow(modelRow);
        jButton1.setEnabled(!locked);
        jButton2.setEnabled(!locked);
        
        if (!locked) {
            jButton1.setToolTipText(null);
            jButton2.setToolTipText(null);
            return;
        }

        String status = String.valueOf(model.getValueAt(Math.max(modelRow, 0), 4)).trim();
        if ("completed".equalsIgnoreCase(status)) {
            jButton1.setToolTipText("Completed appointments cannot be cancelled.");
            jButton2.setToolTipText("Completed appointments cannot be rescheduled.");
        } else {
            jButton1.setToolTipText("Rejected appointments cannot be cancelled.");
            jButton2.setToolTipText("Rejected appointments cannot be rescheduled.");
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("MyAppointments");

        jTable1.setModel(model);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Cancel Appointment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reschedule");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Feedback");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(423, 423, 423)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4))
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int viewRow = jTable1.getSelectedRow();
        
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "There are currently no available appointments for rescheduling.", "You can't reschedule.", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to reschedule");
            return;
        }
        int modelRow = jTable1.convertRowIndexToModel(viewRow);
        if (isLockedRow(modelRow)) {
            JOptionPane.showMessageDialog(this, "This appointment cannot be rescheduled.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to reschedule this timeslot?", "Reschedule time slot", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String supervisorID = String.valueOf(model.getValueAt(modelRow, 1));
            String date = String.valueOf(model.getValueAt(modelRow, 2));
            String time = String.valueOf(model.getValueAt(modelRow, 3));
            Reschedule r = new Reschedule(tpNum, supervisorID, date, time);
            r.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        StudentMainPage mp = new StudentMainPage(tpNum); //to create secondpage object
        mp.setVisible(true); //makingsecondpage display
        this.dispose(); //remove the fristpage
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTextArea1.setText("");

        loadFromFile();
        jTable1.clearSelection();
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton1.setToolTipText(null);
        jButton2.setToolTipText(null);

        JOptionPane.showMessageDialog(this, "Appointments list refreshed!");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int viewRow = jTable1.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
            return;
        }

        int modelRow = jTable1.convertRowIndexToModel(viewRow);
        if (isLockedRow(modelRow)) {
            JOptionPane.showMessageDialog(this, "This appointment cannot be cancelled.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?");
        if (option != JOptionPane.YES_OPTION) return;
        
        Appointment selected = new Appointment(
            String.valueOf(model.getValueAt(modelRow, 0)).trim(),
            String.valueOf(model.getValueAt(modelRow, 1)).trim(),
            String.valueOf(model.getValueAt(modelRow, 2)).trim(),
            String.valueOf(model.getValueAt(modelRow, 3)).trim(),
            String.valueOf(model.getValueAt(modelRow, 4)).trim(),
            "-"
        );
        
        boolean apptFound = false;
        StringBuilder all = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_AP))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { all.append("\n"); continue; }

                String[] p = line.split(",", 6);
                if (p.length < 6) { all.append(line).append("\n"); continue; }

                Appointment ap = new Appointment( p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim() );
                if (!apptFound
                    && selected.getTpNum().equalsIgnoreCase(ap.getTpNum())
                    && selected.getSupervisorID().equalsIgnoreCase(ap.getSupervisorID())
                    && selected.getDate().equals(ap.getDate())
                    && selected.getTime().equals(ap.getTime())) {

                    all.append(String.join(",", ap.getTpNum(), ap.getSupervisorID(), ap.getDate(), 
                            ap.getTime(), "Rejected", ap.getFeedback())).append("\n");
                    apptFound = true;
                } else {
                    all.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File Read Error");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_AP))) {
            bw.write(all.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File Write Error");
            return;
        }


        boolean tsUpdated = false;
        StringBuilder tsAll = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TS))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { tsAll.append("\n"); continue; }

                String[] t = line.split(",", 4);
                if (t.length < 4) { tsAll.append(line).append("\n"); continue; }

                TimeSlot ts = new TimeSlot(t[0].trim(), t[1].trim(), t[2].trim(), t[3].trim());

                if (!tsUpdated
                    && selected.getSupervisorID().equalsIgnoreCase(ts.getSupervisorId())
                    && selected.getDate().equals(ts.getDate())
                    && selected.getTime().equals(ts.getTime())) {

                    ts.setStatus("available");
                    tsAll.append(String.join(",",
                            ts.getSupervisorId(),
                            ts.getDate(),
                            ts.getTime(),
                            ts.getStatus()
                    )).append("\n");
                    tsUpdated = true;
                } else {
                    tsAll.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading timeslot.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!tsUpdated) {
            TimeSlot add = new TimeSlot(selected.getSupervisorID(), selected.getDate(), selected.getTime(), "available");
            tsAll.append(String.join(",", add.getSupervisorId(), add.getDate(), add.getTime(), add.getStatus())).append("\n");
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_TS))) {
            bw.write(tsAll.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing timeslot.txt");
            return;
        }
        
        model.setValueAt("Rejected", modelRow, 4);
        jTextArea1.setText("-");
        JOptionPane.showMessageDialog(this, "Appointment cancelled.");
        int curView = jTable1.getSelectedRow();
        int curModel = (curView >= 0) ? jTable1.convertRowIndexToModel(curView) : -1;
        updateActionButtonsFor(curModel);
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
        int viewRow = jTable1.getSelectedRow();
        if (viewRow < 0) return;
        int modelRow = jTable1.convertRowIndexToModel(viewRow);
        
        updateActionButtonsFor(modelRow);

        Appointment selected = new Appointment(
            String.valueOf(model.getValueAt(modelRow, 0)).trim(),
            String.valueOf(model.getValueAt(modelRow, 1)).trim(),
            String.valueOf(model.getValueAt(modelRow, 2)).trim(),
            String.valueOf(model.getValueAt(modelRow, 3)).trim(),
            String.valueOf(model.getValueAt(modelRow, 4)).trim(),
            "-"
        );

        jTextArea1.setText("");
        jTextArea1.setEditable(false);

        if (!"Completed".equalsIgnoreCase(selected.getStatus())) {
            jTextArea1.setText("-");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_AP))) {
            String eachLine;
            while ((eachLine = br.readLine()) != null) {
                if (eachLine.trim().isEmpty()) continue;

                String[] p = eachLine.split(",", 6);
                if (p.length < 6) continue;

                Appointment ap = new Appointment(
                    p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim()
                );

                if (ap.getTpNum().equalsIgnoreCase(selected.getTpNum())
                        && ap.getSupervisorID().equalsIgnoreCase(selected.getSupervisorID())
                        && ap.getDate().equals(selected.getDate())
                        && ap.getTime().equals(selected.getTime())) {

                    boolean isCompleted  = "completed".equalsIgnoreCase(ap.getStatus());
                    String fb = ap.getFeedback() == null ? "" : ap.getFeedback().trim();
                    boolean hasFeedback = !fb.isEmpty() && !"-".equals(fb);

                    if (isCompleted && hasFeedback) {
                        jTextArea1.setText(fb);
                    } else if (isCompleted) {
                        jTextArea1.setText("No feedback yet.");
                    } else {
                        jTextArea1.setText("-");
                    }

                    break;
                }
            }
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Read File Error");
        }

        
        
    }//GEN-LAST:event_jTable1MouseReleased

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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
