/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yiron.omcomponent;


import com.yiron.omcomponent.OMComponent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.zkoss.zul.Button;

import org.zkoss.zul.Grid;

/**
 *
 * @author Nitin
 */
public class DbTable {

    private String TableName = "";
    private String DataLoadView = "";
    private OMComponent KeyFieldComponent;
    private String RefKeyFieldName="";
    private OMComponent RefKeyFieldComponent;
    //private OMComponent tmpKeyField=new OMComponent("textbox");
    private boolean isDisabled = false;
    private Hashtable<Integer, OMComponent> DataComponentField = new Hashtable<Integer, OMComponent>();
    private int count = 0;
    private boolean isChild=false;
    private DbTable ParentTable; 
    private int keyValue=0;
    private String Id="";
    private Grid ActionGrid;
    private Button btnBeforeAdd,btnAdd, btnRemove;
    private List<String> labelnames=new ArrayList<String>();
    private Object popup;
    public enum TableType{
        Grid,Form;
    }
    private TableType tableType=TableType.Form;
    public DbTable() {
    }

    public DbTable(String TableName, OMComponent KeyFieldComponent, OMComponent RefKeyFieldComponent,boolean ischild) {
        this.TableName = TableName;
        this.KeyFieldComponent = KeyFieldComponent;
        this.RefKeyFieldComponent = RefKeyFieldComponent;
    }

    public DbTable(String TableName, OMComponent KeyField,boolean isChild,boolean test) {
        this.TableName = TableName;
        this.KeyFieldComponent = KeyField;
        this.isChild = isChild;
    }

    public List<String> getLabelnames() {
		return labelnames;
	}

	public void setLabelnames(String label) {
		    
		labelnames.add(label);
	}

	public boolean isIsChild() {
        return isChild;
    }

    public int getKeyValue() {
        return keyValue;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public void setKeyValue(int keyValue) {
        this.keyValue = keyValue;
    }

    public Grid getActionGrid() {
        return ActionGrid;
    }

    public void setActionGrid(Grid ActionGrid) {
        this.ActionGrid = ActionGrid;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    
    public void setIsChild(boolean isChild) {
        this.isChild = isChild;
    }

    public DbTable getParentTable() {
        return ParentTable;
    }

    public void setParentTable(DbTable ParentTable) {
        this.ParentTable = ParentTable;
    }

    public void AddComponent(OMComponent comp) {
        DataComponentField.put(count, comp);
        count++;
    }
    public OMComponent[] getComponents(){
        OMComponent[] components=new OMComponent[count];
        for (int i = 0; i < count; i++) {
            components[i]=DataComponentField.get(i);
        }
        return components;
    }

    public Hashtable<Integer, OMComponent> getDataComponentField() {
        return DataComponentField;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public OMComponent getKeyField() {
        return KeyFieldComponent;
    }

    public void setKeyField(OMComponent KeyField) {
        this.KeyFieldComponent = KeyField;
    }

    public OMComponent getRefKeyField() {
        return RefKeyFieldComponent;
    }

    public void setRefKeyField(String RefKeyFieldName, OMComponent RefKeyField) {
        this.RefKeyFieldComponent = RefKeyField;
        this.RefKeyFieldName=RefKeyFieldName;
    }

    public String getRefKeyFieldName() {
        return RefKeyFieldName;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public int getCount() {
        return count;
    }

    public String getDataLoadView() {
        return DataLoadView;
    }

    public void setDataLoadView(String DataLoadView) {
        this.DataLoadView = DataLoadView;
    }
    public OMComponent getComponent(String fieldName){
        for (int i = 0; i < count; i++) {
            if (DataComponentField.get(i).getFieldName().equals(fieldName)){
                return DataComponentField.get(i);
            }
        }
        return null;
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(Button btnRemove) {
        this.btnRemove = btnRemove;
    }

    public void setPopup(Object popup) {
        this.popup = popup;
    }

    public Object getPopup() {
        return popup;
    }

    public void setBtnBeforeAdd(Button btnBeforeAdd) {
        this.btnBeforeAdd = btnBeforeAdd;
    }

    public Button getBtnBeforeAdd() {
        return btnBeforeAdd;
    }
    
}
