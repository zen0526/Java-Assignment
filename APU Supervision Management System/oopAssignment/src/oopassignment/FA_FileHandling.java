/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopassignment;

import oopassignment.Assignment;
import java.util.ArrayList;

import java.io.*;
import javax.swing.JOptionPane;


public class FA_FileHandling {
    
    public static ArrayList<String[]> readFile(String fileName){
        ArrayList<String[]> lines = new ArrayList<>();
        
        // try-with-resources, with auto close filereader and buffered reader
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String eachLine;
            
            while ((eachLine = br.readLine()) != null){
                eachLine = eachLine.trim();
                //Skip blank lines
                if (eachLine.isEmpty()){
                    continue;
                }
                String[] eachLineValue = eachLine.split(",");
                //Safety check for any mistyped characters
                if (eachLineValue.length < 2){
                    continue;
                }
                lines.add(eachLineValue);
            }
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, fileName +"Not Found");
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error reading file: " + fileName);
        }
        return lines;
        
    }
    //Write file with ArrayList of String[]
    public static void writeFile(String fileName, ArrayList<String[]> fileData, boolean appendFlag){
        
        //write File with try-with-resources
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,appendFlag))){
            for (String[] lineData : fileData){
                String eachLine = String.join(",", lineData);
                bw.write(eachLine);
                bw.newLine();
            }
            
            
            
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, fileName +"Not Found");
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error reading file: " + fileName);
        }
        
        
    }   
    //Write file with Assignment objects
    public static void writeAssignmentFile(String fileName, ArrayList<Assignment> assignmentList){
        
        //write File with try-with-resources
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            for (Assignment data : assignmentList){
                bw.write(String.join(",", data.getAssignmentDataList()));
                bw.newLine();
            }

        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, fileName +"Not Found");
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error reading file: " + fileName);
        }
        
        
    }
    //Write File with supervisor file
    public static void writeSupervisorFile(String fileName, ArrayList<Supervisor> supervisorList){
        
        //write File with try-with-resources
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            for (Supervisor supervisor : supervisorList){
                bw.write(String.join(",", supervisor.getSupervisorFileDataList()));
                bw.newLine();
            }

        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, fileName +"Not Found");
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error reading file: " + fileName);
        }
        
        
    }
}
