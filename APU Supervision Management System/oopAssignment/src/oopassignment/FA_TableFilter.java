/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopassignment;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class FA_TableFilter {
    //Declare attributes for tables
    private DefaultTableModel model;
    // Store original table data
    private ArrayList<String[]> originalData;
    
    /**
     * Constructor to initialize filter for a specific table
     */
    public FA_TableFilter(DefaultTableModel model) {
        this.model = model;
        saveOriginalTableData();
    }
    
    //save original table data
    public void saveOriginalTableData(){
        originalData = new ArrayList<>();
        for(int row=0; row < model.getRowCount(); row++){
            //set row size
            String[] rowData = new String[model.getColumnCount()];
            for(int col=0; col < model.getColumnCount(); col++){
                rowData[col] = model.getValueAt(row, col).toString();
            }
            originalData.add(rowData);
                
        }
    }
    /**
     * Apply filters to the table based on filter criteria
     * @param filterValues Array of filter texts inputted by user (empty string means no filter for that column) exp: Computing
     * @param filterColumns Array of indexes of the columns to be filtered to apply filters
     */
    public void applyFilters(String[] filterValues, int[] filterColumns) {
        //Clear Current Table Data
        model.setRowCount(0);
        
        //Check filterValues with Original data
        for(String[] row : originalData){
            boolean rowMatch = true;
            
            //Get Filter value and column
            for(int i=0 ; i < filterValues.length; i++){
                String filterValue = filterValues[i].trim().toLowerCase();
                int column = filterColumns[i];
                
                //Check if value isn't Empty
                if (filterValue.isEmpty()){
                    continue;
                }
                
                //Check if filterValue is in original data row
                if (row[column].toLowerCase().contains(filterValue) == false){
                    rowMatch = false;
                    break;
                }
            }
            if (rowMatch == true){
                model.addRow(row);
            } 
        }
    }
}
