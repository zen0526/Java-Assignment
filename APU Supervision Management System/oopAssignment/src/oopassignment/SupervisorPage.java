package oopassignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SupervisorPage extends javax.swing.JFrame {
    private int row = -1;
    private String supIdBeforeEdit;
    private DefaultTableModel model = new DefaultTableModel();
    private String[] columnName = {"Supervisor ID", "Supervisor Name", "Supervisor Password", "Supervisor Email", "Gender", "DOB", "Phone Number", "Department", "Number of Students", "Office Location", "Status"};
    
    private ArrayList<Supervisor> supervisorList = new ArrayList<>();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SupervisorPage.class.getName());

    /**
     * Creates new form SupervisorPage
     */
    public SupervisorPage() {
        model.setColumnIdentifiers(columnName);
        initComponents();
        
        loadSupervisorFromFile();
        updateStudentCounts();
    }

    private void loadSupervisorFromFile(){
        try{
            FileReader fr = new FileReader("supervisor.txt");
            BufferedReader br = new BufferedReader(fr);
            
            String eachLine;
            
            while ((eachLine = br.readLine()) != null){
                if(eachLine.trim().isEmpty()){
                    continue;
                }
                
                String supervisorData[] = eachLine.split(",");
                
                // Skips incomplete lines
                if(supervisorData.length < 11){
                    continue;
                }
                
                
                Supervisor s = new Supervisor(supervisorData[0], supervisorData[1], supervisorData[2], supervisorData[3], supervisorData[4], supervisorData[5], supervisorData[6], supervisorData[7], supervisorData[8], supervisorData[9], supervisorData[10]);
                supervisorList.add(s);
                model.addRow(supervisorData);
            }
            
            br.close();
            fr.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error reading supervisor.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);            
        }
    }
    
    private void updateStudentCounts(){
        try {
            FileReader fr = new FileReader("assignment.txt");
            BufferedReader br = new BufferedReader(fr);
            
            ArrayList<String> supervisorIDs = new ArrayList<>();
            ArrayList<Integer> supervisorCounts = new ArrayList<>();
            
            String eachLine;
            while((eachLine = br.readLine()) != null ){
                if(eachLine.trim().isEmpty()){
                    continue;
                }
                
                String parts[] = eachLine.split(",");
                if(parts.length < 2){
                    continue;
                }
                
                String supervisorID = parts[1].trim();
                
                int index = supervisorIDs.indexOf(supervisorID);
                if(index == -1){
                    supervisorIDs.add(supervisorID);
                    supervisorCounts.add(1);
                }else{
                    supervisorCounts.set(index, supervisorCounts.get(index) + 1);
                }
            }
            
            br.close();
            fr.close();
            
            for(int i=0; i < supervisorList.size(); i++){
                Supervisor s = supervisorList.get(i);
                int index = supervisorIDs.indexOf(s.getsupervisorID());
                int count = (index != -1) ? supervisorCounts.get(index) : 0;
                
                s.setnumberofStudent(String.valueOf(count));
                model.setValueAt(String.valueOf(count), i, 8);
            }
            
            try{
                FileWriter fw = new FileWriter("supervisor.txt");
                BufferedWriter bw = new BufferedWriter(fw);
                
                for(Supervisor s : supervisorList){
                    bw.write(s.getsupervisorID() +","+ s.getName() +","+ s.getPassword() +","+ s.getEmail() +","+s.getGender() +","+s.getdob() +","+s.getphoneNum() +","+s.getDepartment() +","+s.getnumberofStudent() +","+s.getofficeLocation() +","+s.getStatus());
                    bw.newLine();
                }
                
                bw.close();
                fw.close();
                
            }catch(IOException e){
                JOptionPane.showMessageDialog(this, "Error writing to supervisor.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error reading supervisor.txt or assignment.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateData(String ID, String name, String password, String email, String dob, String phoneNum, String Department, String NumberofStudent, String officeLocation, String oldID){
        if(!ID.matches("Sup\\d{1,6}")){
            JOptionPane.showMessageDialog(this, "Supervisor ID number must start with Sup followed by 1-6 digits.");
            return false;
        }
        
        for (Supervisor s: supervisorList){
            if(s.getsupervisorID().equalsIgnoreCase(ID) && !s.getsupervisorID().equalsIgnoreCase(oldID)){
                JOptionPane.showMessageDialog(this, "Supervisor ID already exists!");
                return false;
            }
        }
        
        if(ID.isEmpty()){
            JOptionPane.showMessageDialog(this, "Supervisor ID cannot be empty!");
            return false;
        }
        
        if(name.isEmpty() || !name.matches("[A-Za-z ]{3,30}")){
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
        
        if(Department.isEmpty()){
            JOptionPane.showMessageDialog(this, "Department cannot be empty!");
            return false;
        }
        
        try{
            int numberofstudent = Integer.parseInt(NumberofStudent);
            if(numberofstudent < 0){
                JOptionPane.showMessageDialog(this, "Number of student cannot be less than 0!");
                return false;
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Number of student must contain only numbers!");
            return false;
        }
        
        
        if(officeLocation.isEmpty()){
            JOptionPane.showMessageDialog(this, "Office location cannot be empty!");
            return false;
        }
        
        if(!officeLocation.matches("(?i)Block [A-Z]")){
            JOptionPane.showMessageDialog(this, "Office location must be 'block A', 'block B', 'block C', etc.");
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
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("System Admin Menu");

        jLabel2.setText("Supervisor ID:");

        jLabel3.setText("Supervisor Name:");

        jLabel4.setText("Supervisor Password:");

        jLabel5.setText("Supervisor email:");

        jLabel6.setText("Gender:");

        jLabel7.setText("DOB:");

        jLabel8.setText("Phone Number:");

        jLabel9.setText("Department:");

        jLabel10.setText("Number of Students:");

        jLabel11.setText("Office Location:");

        jTable1.setModel(model);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setText("Status:");

        jButton4.setText("Reset Password");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("back");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jButton5))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1095, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(7, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String id = jTextField1.getText().trim();
        String name = jTextField2.getText().trim();
        String password= jTextField3.getText().trim();
        String email = jTextField4.getText().trim();
        String gender = (String) jComboBox1.getSelectedItem();
        String dob = jTextField6.getText().trim();
        String phoneNum = jTextField7.getText().trim();
        String department = jTextField8.getText().trim();
        String numberofStudent = jTextField9.getText().trim();
        String officeLocation = jTextField10.getText().trim();
        String status = (String) jComboBox2.getSelectedItem();
        
        if(!validateData(id, name, password, email, dob, phoneNum, department, numberofStudent, officeLocation, "")){
            return;
        }
        
        String newRowData[] = {id, name, password, email, gender, dob, phoneNum, department, numberofStudent, officeLocation, status};
        model.addRow(newRowData);
        supervisorList.add(new Supervisor(id, name, password, email, gender, dob, phoneNum, department, numberofStudent, officeLocation, status));
        
        try{
            FileWriter fw = new FileWriter("supervisor.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(id +","+ name +","+ password +","+ email +","+ gender +","+ dob +","+ phoneNum +","+ department +","+ numberofStudent +","+ officeLocation +","+ status);
            bw.newLine();
            
            bw.close();
            fw.close();
            
            FileWriter fw2 = new FileWriter("Accounts.txt", true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            
            bw2.write(id +","+ password +","+ "Supervisor" +","+ "0");
            bw2.newLine();
            
            bw2.close();
            fw2.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error updating Accounts.txt or supervisor.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String new_id = jTextField1.getText().trim();
        String new_name = jTextField2.getText().trim();
        String new_password = jTextField3.getText().trim();
        String new_email = jTextField4.getText().trim();
        String new_gender = (String) jComboBox1.getSelectedItem();
        String new_dob = jTextField6.getText().trim();
        String new_phoneNum = jTextField7.getText().trim();
        String new_department = jTextField8.getText().trim();
        String new_numberofStudent = jTextField9.getText().trim();
        String new_officeLocation = jTextField10.getText().trim();
        String new_status = (String) jComboBox2.getSelectedItem();
         
        if(!validateData(new_id, new_name, new_password, new_email, new_dob, new_phoneNum, new_department, new_numberofStudent, new_officeLocation, supIdBeforeEdit)){
            return;
        }
        
        model.setValueAt(new_id, row, 0);
        model.setValueAt(new_name, row, 1);
        model.setValueAt(new_password, row, 2);
        model.setValueAt(new_email, row, 3);
        model.setValueAt(new_gender, row, 4);
        model.setValueAt(new_dob, row, 5);
        model.setValueAt(new_phoneNum, row, 6);
        model.setValueAt(new_department, row, 7);
        model.setValueAt(new_numberofStudent, row, 8);
        model.setValueAt(new_officeLocation, row, 9);
        model.setValueAt(new_status, row, 10);
        
        supervisorList.get(row).setsupervisorID(new_id);
        supervisorList.get(row).setName(new_name);
        supervisorList.get(row).setPassword(new_password);
        supervisorList.get(row).setEmail(new_email);
        supervisorList.get(row).setGender(new_gender);
        supervisorList.get(row).setdob(new_dob);
        supervisorList.get(row).setphoneNum(new_phoneNum);
        supervisorList.get(row).setDepartment(new_department);
        supervisorList.get(row).setnumberofStudent(new_numberofStudent);
        supervisorList.get(row).setofficeLocation(new_officeLocation);
        supervisorList.get(row).setStatus(new_status);
        
        String editedID = supervisorList.get(row).getsupervisorID();
        String editedPassword = supervisorList.get(row).getPassword();
                
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");

        try{
            FileReader fr = new FileReader("Accounts.txt");
            BufferedReader br = new BufferedReader(fr);
            
            ArrayList<String> accountList = new ArrayList<>();
            String eachLine;
            
            while((eachLine = br.readLine()) != null){
                if(eachLine.split(",")[0].equals(supIdBeforeEdit)){
                    accountList.add(editedID +","+ editedPassword +","+ "Supervisor" +","+ eachLine.split(",")[3]);
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
            FileWriter fw = new FileWriter("supervisor.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(Supervisor s : supervisorList){
                String eachLine = s.getsupervisorID() +","+ s.getName() +","+ s.getPassword() +","+ s.getEmail() +","+ s.getGender() +","+ s.getdob() +","+ s.getphoneNum() +","+ s.getDepartment() +","+ s.getnumberofStudent() +","+ s.getofficeLocation() +","+ s.getStatus();
                bw.write(eachLine);
                bw.newLine();
            }

            bw.close();
            fw.close();

        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error updating supervisor.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        jTable1.clearSelection();
        row = -1;
        JOptionPane.showMessageDialog(this, "Supervisor record successfully updated!");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
        row = jTable1.getSelectedRow();
        
        String id = String.valueOf(model.getValueAt(row, 0));
        String name = String.valueOf(model.getValueAt(row, 1));
        String password= String.valueOf(model.getValueAt(row, 2));
        String email = String.valueOf(model.getValueAt(row, 3));
        String gender = String.valueOf(model.getValueAt(row, 4));
        String dob = String.valueOf(model.getValueAt(row, 5));
        String phoneNum = String.valueOf(model.getValueAt(row, 6));
        String department = String.valueOf(model.getValueAt(row, 7));
        String numberofStudent = String.valueOf(model.getValueAt(row, 8));
        String officeLocation = String.valueOf(model.getValueAt(row, 9));
        String status = String.valueOf(model.getValueAt(row, 10));
        
        supIdBeforeEdit = id;
        
        jTextField1.setText(id);
        jTextField2.setText(name);
        jTextField3.setText(password);
        jTextField4.setText(email);
        jComboBox1.setSelectedItem(gender);
        jTextField6.setText(dob);
        jTextField7.setText(phoneNum);
        jTextField8.setText(department);
        jTextField9.setText(numberofStudent);
        jTextField10.setText(officeLocation);
        jComboBox2.setSelectedItem(status);
    }//GEN-LAST:event_jTable1MouseReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
        }else{
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?");
            if(option == 0){
                String deletedID = supervisorList.get(row).getsupervisorID();
                model.removeRow(row);
                supervisorList.remove(row);
                
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField6.setText("");
                jTextField7.setText("");
                jTextField8.setText("");
                jTextField9.setText("");
                jTextField10.setText("");

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
                    FileWriter fw = new FileWriter("supervisor.txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    for(Supervisor s : supervisorList){
                        bw.write(s.getsupervisorID() +","+ s.getName() +","+ s.getPassword() +","+ s.getEmail() +","+ s.getGender() +","+ s.getdob() +","+ s.getphoneNum() +","+ s.getDepartment() +","+ s.getnumberofStudent() +","+ s.getofficeLocation() +","+ s.getStatus());
                        bw.newLine();
                    }
                    bw.close();
                    fw.close();
                    
                    JOptionPane.showMessageDialog(this, "Supervisor record successfully deleted");
                    jTable1.clearSelection();
                    row = -1;
                    
                }catch(IOException e){
                    JOptionPane.showMessageDialog(this, "Error updating supervisor.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please select a row to reset password");
        }else{
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset password for this account?\nThis action will reset the password of the account to a default password and cannot be undone", "Confirm Reset Password", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION){
                String defaultPassword = "password123";
                model.setValueAt(defaultPassword, row, 2);
                supervisorList.get(row).setPassword(defaultPassword);
                String id = supervisorList.get(row).getsupervisorID();
                
                try{
                    FileReader fr = new FileReader("Accounts.txt");
                    BufferedReader br = new BufferedReader(fr);
                    ArrayList<String> accountList = new ArrayList<>();
                    String eachLine;
                    
                    while((eachLine = br.readLine()) != null){
                        if(eachLine.split(",")[0].equals(id)){
                            accountList.add(id +","+ defaultPassword +","+ "Supervisor" +","+ "0");
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
                    JOptionPane.showMessageDialog(this, "Error updating Accounts.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try{
                    FileWriter fw = new FileWriter("supervisor.txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    for (Supervisor s : supervisorList){
                        bw.write(s.getsupervisorID() +","+ s.getName() +","+ s.getPassword() +","+ s.getEmail() +","+ s.getGender() +","+ s.getdob() +","+ s.getphoneNum() +","+ s.getDepartment() +","+ s.getnumberofStudent() +","+ s.getofficeLocation() +","+ s.getStatus());
                        bw.newLine();
                    }
                    
                    bw.close();
                    fw.close();
                    
                    JOptionPane.showMessageDialog(this, "The password of this account has been reset to the default password: " + defaultPassword, "Password reset successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    jTable1.clearSelection();
                    row = -1;
                    
                }catch(IOException e){
                    JOptionPane.showMessageDialog(this, "Error updating supervisor.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        SystemAdminMainMenu menu = new SystemAdminMainMenu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new SupervisorPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
