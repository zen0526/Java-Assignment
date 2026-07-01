package oopassignment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class StudentPage extends javax.swing.JFrame {
    private int row = -1;
    private String tpNumBeforeEdit;
    private DefaultTableModel model = new DefaultTableModel();
    private String[] columnName = {"TP Number", "Name", "Password", "Email", "Phone Number", "Gender", "DOB", "Address", "Country", "Intake Code", "Course", "Status"};
    
    // Array list to store all objects created
    private ArrayList<Student> studentList = new ArrayList<>();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StudentPage.class.getName());

    /**
     * Creates new form SystemAdminPage
     */
    public StudentPage() {
        model.setColumnIdentifiers(columnName);
        initComponents();
        
        loadStudentsFromFile();
        
         String[] countries = Locale.getISOCountries();
        DefaultComboBoxModel<String> countrymodel = new DefaultComboBoxModel<>();

        for (String code : countries) {
            Locale locale = new Locale("", code);
            countrymodel.addElement(locale.getDisplayCountry());
        }

        jComboBox3.setModel(countrymodel);
        jComboBox3.setSelectedIndex(0);
    }
    
    // The function to initialize all student objects
    private void loadStudentsFromFile(){
        try {
            FileReader fr = new FileReader("students.txt");
            BufferedReader br = new BufferedReader(fr);

            String eachLine;

            while ((eachLine = br.readLine()) != null){
                
                if(eachLine.trim().isEmpty()){
                    continue;
                }
                
                String studentData[] = eachLine.split(",");
                
                if(studentData.length < 12){
                    continue;
                }    
                
                Student s = new Student(studentData[0], studentData[1], studentData[2], studentData[3], studentData[4], studentData[5], studentData[6], studentData[7], studentData[8], studentData[9], studentData[10], studentData[11]);
                studentList.add(s);
                model.addRow(studentData);
            }
            br.close();
            fr.close();
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error reading students.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private boolean validateData(String tpNum, String name, String password, String email, String phoneNum, String dob, String address, String intakeCode, String course, String oldID){
        if(!tpNum.matches("TP\\d{1,6}")){
            JOptionPane.showMessageDialog(this, "TP number must start with TP followed by 1-6 digits.");
            return false;
        }
        
        for (Student s: studentList){
            if(s.gettpNum().equalsIgnoreCase(tpNum) && !s.gettpNum().equalsIgnoreCase(oldID)){
                JOptionPane.showMessageDialog(this, "TP number already exists!");
                return false;
            }
        }
        
        if(tpNum.isEmpty()){
            JOptionPane.showMessageDialog(this, "TP number cannot be empty!");
            return false;
        }
        
        if(name.isEmpty() || !name.matches("[A-za-z ]{3,30}")){
            JOptionPane.showMessageDialog(this, "Name should only contain characters and should be between 3 and 30 characters.");
            return false;
        }
        
        if(password.isEmpty() || password.length() < 6){
            JOptionPane.showMessageDialog(this, "Password cannot be empty and must contain at least 6 characters!");
            return false;
        }
        
        if(!email.endsWith("@gmail.com")){
            JOptionPane.showMessageDialog(this, "Email must end with @gmail.com");
            return false;
        }
        
        if(!phoneNum.matches("\\d{8,15}")){
            JOptionPane.showMessageDialog(this, "Phone number must have a minimum of 8 characters and a maximum of 15 characters");
            return false;
        }

        if(dob.isEmpty()){
            JOptionPane.showMessageDialog(this, "Date of birth cannot be empty");
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(dob, formatter);
            if (parsedDate.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Date of birth cannot be in the future!");
                return false;
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Please enter in YYYY-MM-DD format (e.g. 2002-09-25).");
            return false;
        }
        
        if(address.length() < 5){
            JOptionPane.showMessageDialog(this, "Address cannot be less than 5 characters");
            return false;
        }
        
        if(intakeCode.isEmpty()){
            JOptionPane.showMessageDialog(this, "Intake Code cannot be empty");
            return false;
        }

        if(course.isEmpty()){
            JOptionPane.showMessageDialog(this, "Course cannot be empty");
            return false;
        }
        
        return true;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Add = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Student Management");

        jLabel2.setText("TP Number:");

        jLabel3.setText("Name:");

        jLabel4.setText("DOB:");

        jLabel5.setText("Address:");

        jLabel6.setText("Country:");

        jLabel7.setText("Intake Code:");

        jLabel8.setText("Course:");

        jLabel11.setText("Password:");

        jLabel12.setText("Email:");

        jLabel13.setText("Gender:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        jTable1.setModel(model);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        Add.setText("Add");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        jLabel14.setText("Phone Number:");

        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        jLabel10.setText("Status:");

        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Reset password");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel13)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(4, 4, 4))
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField7)
                                    .addComponent(jComboBox2, 0, 166, Short.MAX_VALUE)
                                    .addComponent(jTextField9)
                                    .addComponent(jTextField11)
                                    .addComponent(jTextField10)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(Add)
                                .addGap(10, 10, 10)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(37, 37, 37))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jLabel13))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(65, 65, 65))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        // TODO add your handling code here:
        // Validation of data needed in the future
        String tpNum = jTextField1.getText().trim();
        String name = jTextField2.getText().trim();
        String password = jTextField3.getText().trim();
        String email = jTextField4.getText().trim();
        String phoneNum = jTextField12.getText().trim();
        String gender = (String) jComboBox1.getSelectedItem();
        String dob = jTextField7.getText().trim();
        String address = jTextField11.getText().trim();
        String country = (String) jComboBox3.getSelectedItem();
        String intakeCode = jTextField10.getText().trim();
        String course = jTextField9.getText().trim();
        String status = (String) jComboBox2.getSelectedItem();
        
        if(!validateData(tpNum, name, password, email, phoneNum, dob, address, intakeCode, course, "")){
            return;
        }
        
        String[] newRowData = {tpNum, name, password, email, phoneNum, gender, dob, address, country, intakeCode, course, status};
        model.addRow(newRowData);
        studentList.add(new Student(tpNum, name, password, email, phoneNum, gender, dob, address, country, intakeCode, course, status));
        
        try {
            FileWriter fw = new FileWriter("students.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(tpNum +","+ name +","+ password +","+ email +","+ phoneNum +","+ gender +","+ dob +","+ address +","+ country +","+ intakeCode +","+ course +","+ status);
            bw.newLine();
            
            bw.close();
            fw.close();
            
            FileWriter fw2 = new FileWriter("Accounts.txt", true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            
            bw2.write(tpNum +","+ password +","+ "Student" + "," + "0");
            bw2.newLine();
            
            bw2.close();
            fw2.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error updating students.txt or Accounts.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField7.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        JOptionPane.showMessageDialog(this, "Student has been added!");
    }//GEN-LAST:event_AddActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
        row = jTable1.getSelectedRow();
        
        String tpNum = String.valueOf(model.getValueAt(row, 0));
        String name = String.valueOf(model.getValueAt(row, 1));
        String password = String.valueOf(model.getValueAt(row, 2));
        String email = String.valueOf(model.getValueAt(row, 3));
        String phoneNum = String.valueOf(model.getValueAt(row, 4));
        String gender = String.valueOf(model.getValueAt(row, 5));
        String dob = String.valueOf(model.getValueAt(row, 6));
        String address = String.valueOf(model.getValueAt(row, 7));
        String country = String.valueOf(model.getValueAt(row, 8));
        String intakeCode = String.valueOf(model.getValueAt(row, 9));
        String course = String.valueOf(model.getValueAt(row, 10));
        String status = String.valueOf(model.getValueAt(row, 11));
        
        tpNumBeforeEdit = tpNum;
        
        jTextField1.setText(tpNum);
        jTextField2.setText(name);
        jTextField3.setText(password);
        jTextField4.setText(email);
        jTextField12.setText(phoneNum);
        jComboBox1.setSelectedItem(gender);
        jTextField7.setText(dob);
        jTextField11.setText(address);
        jComboBox3.setSelectedItem(country);
        jTextField10.setText(intakeCode);
        jTextField9.setText(course);
        jComboBox2.setSelectedItem(status);
    }//GEN-LAST:event_jTable1MouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String new_tpNum = jTextField1.getText().trim();
        String new_name = jTextField2.getText().trim();
        String new_password = jTextField3.getText().trim();
        String new_email = jTextField4.getText().trim();
        String new_phoneNum = jTextField12.getText().trim();
        String new_gender = (String) jComboBox1.getSelectedItem();
        String new_dob = jTextField7.getText().trim();
        String new_address = jTextField11.getText().trim();
        String new_country = (String) jComboBox3.getSelectedItem();
        String new_intakeCode = jTextField10.getText().trim();
        String new_course = jTextField9.getText().trim();
        String new_status = (String) jComboBox2.getSelectedItem();
        
        if(!validateData(new_tpNum, new_name, new_password, new_email, new_phoneNum, new_dob, new_address, new_intakeCode, new_course, tpNumBeforeEdit)){
            return;
        }
        
        model.setValueAt(new_tpNum, row, 0);
        model.setValueAt(new_name, row, 1);
        model.setValueAt(new_password, row, 2);
        model.setValueAt(new_email, row, 3);
        model.setValueAt(new_phoneNum, row, 4);
        model.setValueAt(new_gender, row, 5);
        model.setValueAt(new_dob, row, 6);
        model.setValueAt(new_address, row, 7);
        model.setValueAt(new_country, row, 8);
        model.setValueAt(new_intakeCode, row, 9);
        model.setValueAt(new_course, row, 10);
        model.setValueAt(new_status, row, 11);

        studentList.get(row).settpNum(new_tpNum);
        studentList.get(row).setName(new_name);
        studentList.get(row).setPassword(new_password);
        studentList.get(row).setEmail(new_email);
        studentList.get(row).setphoneNum(new_phoneNum);
        studentList.get(row).setGender(new_gender);
        studentList.get(row).setdob(new_dob);
        studentList.get(row).setAddress(new_address);
        studentList.get(row).setCountry(new_country);
        studentList.get(row).setintakecode(new_intakeCode);
        studentList.get(row).setCourse(new_course);
        studentList.get(row).setStatus(new_status);
        
        String editedID = studentList.get(row).gettpNum();
        String editedPassword = studentList.get(row).getPassword();
        
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField7.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        
        try{
            FileReader fr = new FileReader("Accounts.txt");
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> accountList = new ArrayList<>();
            String eachLine;
            
            while((eachLine = br.readLine()) != null){
                if(eachLine.split(",")[0].equals(tpNumBeforeEdit)){
                    accountList.add(editedID +","+ editedPassword +","+ "Student" +","+ eachLine.split(",")[3]);
                    continue;
                }
                accountList.add(eachLine);
            }
            
            br.close();
            fr.close();
            
            FileWriter fw = new FileWriter("Accounts.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(String line : accountList){
                bw.write(line);
                bw.newLine();
            }
            
            bw.close();
            fw.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error updating Accounts.txt: " + e.getMessage(), "File error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{            
            // for writing the changed data into students.txt
            FileWriter fw = new FileWriter("students.txt");
            BufferedWriter bw = new BufferedWriter(fw);
                     
            for(Student s : studentList){
                String eachLine = s.gettpNum() +","+ s.getName() +","+ s.getPassword() +","+ s.getEmail() +","+ s.getphoneNum() +","+ s.getGender() +","+ s.getdob() +","+ s.getAddress() +","+ s.getCountry() +","+ s.getintakecode() +","+ s.getCourse() +","+ s.getStatus(); 
                bw.write(eachLine);
                bw.newLine();
                
            }
            bw.close();
            fw.close();
                        
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error updating students.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        jTable1.clearSelection();
        row = -1;
        JOptionPane.showMessageDialog(this, "Student record successfully edited!");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (row == -1){
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
        }else{
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?");
            if (option == 0){
                String deletedID = studentList.get(row).gettpNum();
                model.removeRow(row);
                studentList.remove(row);
                
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField7.setText("");
                jTextField9.setText("");
                jTextField10.setText("");
                jTextField11.setText("");
                jTextField12.setText("");
                
                
                try{
                    FileReader fr = new FileReader("Accounts.txt");
                    BufferedReader br = new BufferedReader(fr);
                    ArrayList<String> accountList = new ArrayList<>();

                    String eachLine;
                    
                    while((eachLine = br.readLine()) != null){
                        if(!eachLine.split(",")[0].equals(deletedID)){
                            accountList.add(eachLine);
                        }
                    }
                    
                    br.close();
                    fr.close();
                    
                    FileWriter fw = new FileWriter("Accounts.txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    for(String line : accountList){
                        bw.write(line);
                        bw.newLine();
                    }
                    
                    bw.close();
                    fw.close();
                    
                }catch(IOException e){
                    JOptionPane.showMessageDialog(this, "Error updating Accounts.txt: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try{
                    FileWriter fw = new FileWriter("students.txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    for (Student s : studentList){
                        bw.write(s.gettpNum() +","+ s.getName() +","+ s.getPassword() +","+ s.getEmail() +","+ s.getphoneNum() +","+ s.getGender() +","+ s.getdob() +","+ s.getAddress() +","+ s.getCountry() +","+ s.getintakecode() +","+ s.getCourse() +","+ s.getStatus());
                        bw.newLine();
                    }
                    
                    bw.close();
                    fw.close();
                    
                    JOptionPane.showMessageDialog(this, "Student successfully deleted.");
                    jTable1.clearSelection();
                    row = -1;
                    
                }catch(IOException e){
                    JOptionPane.showMessageDialog(this, "Error updating students.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please select a row to reset password");
        }else{
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset password for this account?\nThis action will reset the password of the account to a default password and cannot be undone", "Confirm Reset Password", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION){
                String defaultPassword = "password123";
                model.setValueAt(defaultPassword, row, 2);
                studentList.get(row).setPassword(defaultPassword);
                
                String tpNum = studentList.get(row).gettpNum();
                
                try{
                    FileReader fr = new FileReader("Accounts.txt");
                    BufferedReader br = new BufferedReader(fr);
                    ArrayList<String> accountList = new ArrayList<>();
                    String eachLine;
                    
                    while((eachLine = br.readLine()) != null){
                        if(eachLine.split(",")[0].equals(tpNum)){
                            accountList.add(tpNum +","+ defaultPassword +","+ "Student" +","+ "0");
                            continue;
                        }
                        accountList.add(eachLine);
                    }
                    
                    br.close();
                    fr.close();
                    
                    FileWriter fw = new FileWriter("Accounts.txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (String line : accountList){
                        bw.write(line);
                        bw.newLine();
                    }
                    
                    bw.close();
                    fw.close();
                    
                }catch(IOException e){
                    JOptionPane.showMessageDialog(this, "Error updating Accounts.txt: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                try{
                    FileWriter fw = new FileWriter("students.txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    for (Student s : studentList){
                        bw.write(s.gettpNum() +","+ s.getName() +","+ s.getPassword() +","+ s.getEmail() +","+ s.getphoneNum() +","+ s.getGender() +","+ s.getdob() +","+ s.getAddress() +","+ s.getCountry() +","+ s.getintakecode() +","+ s.getCourse() +","+ s.getStatus());
                        bw.newLine();
                    }
                    
                    bw.close();
                    fw.close();
                    
                    JOptionPane.showMessageDialog(this, "The password of this account has been reset to the default password: " + defaultPassword, "Password reset successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    jTable1.clearSelection();
                    row = -1;
                    
                }catch(IOException e){
                    JOptionPane.showMessageDialog(this, "Error updating students.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        SystemAdminMainMenu menu = new SystemAdminMainMenu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new StudentPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
