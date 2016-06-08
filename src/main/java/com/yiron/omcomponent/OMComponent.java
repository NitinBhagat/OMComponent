/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yiron.omcomponent;

//import com.RuleEngine.Automate_Build;
//import com.yiron.databaseconnection.Connection;
//import com.yiron.databaseconnection.DataTable;
//import com.yiron.databaseconnection.DatabaseControler;
//import com.yiron.databaseconnection.DbTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import org.zkoss.zk.ui.event.KeyEvent;

/**
 *
 * @author Nitin
 */
public class OMComponent {

    private Component comp;
    private String Type;
    private String FieldName;
    private boolean isKey;
    private String searchproc = "", dispfield = "", valuefield = "", filter = "";
    private String treeparent = "";
    private DbTable TableName;
    private boolean isMandatory = false;
    private boolean isEditable = false;
    private String refCombo = "";
    private String Title = "";
    private String list;
    private String formula = "";
    private Label lblTitle;
    private Label lblMandat;
    private String Value="";

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
        if (lblTitle != null) {
            lblTitle.setValue(Title);
        }
    }

    public void setTitle(String Title, Label lblTitle, Label lblMandat) {
        this.Title = Title;
        this.lblTitle = lblTitle;
        this.lblMandat = lblMandat;
    }

    public boolean isIsEditable() {
        return isEditable;
    }

    public String getTreeparent() {
        return treeparent;
    }

    public void setTreeparent(String treeparent) {
        this.treeparent = treeparent;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public void setIsEditable(String isEditable) {
        this.isEditable = (isEditable.equals("1") ? true : false);
    }

    public DbTable getTableName() {
        return TableName;
    }

    public void setTableName(DbTable TableName) {
        this.TableName = TableName;
    }

    public String getSearchproc() {
        return searchproc;
    }

    public void setSearchproc(String searchproc) {
        this.searchproc = searchproc;
    }

    public String getDispfield() {
        return dispfield;
    }

    public boolean isIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
        if (this.lblMandat != null) {
            if (isMandatory) {
                this.lblMandat.setValue("*");
            } else {
                this.lblMandat.setValue("");
            }
        }
    }

    public void setDispfield(String dispfield) {
        this.dispfield = dispfield;
    }

    public String getValuefield() {
        return valuefield;
    }

    public void setValuefield(String valuefield) {
        this.valuefield = valuefield;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isIsKey() {
        return isKey;
    }

    public void setIsKey(boolean isKey) {
        this.isKey = isKey;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }
    private String oldValue;

    public OMComponent(String type) {
        Type = type;
        if (Type.equals("textbox")) {
            comp = new Textbox();
        } else if (Type.equals("fileupload") || Type.equals("display")) {
            comp = new Label();
            //comp1.setStyle("background-color: #3498DB;border-color: black;border-style: inset;border-width: thin;padding: inherit;");
//            comp = comp1;
        } else if (Type.equals("datebox")) {
            final Datebox dt = new Datebox();
            dt.setCtrlKeys("#up#down");
            dt.addEventListener("onCtrlKey", new EventListener<KeyEvent>() {

                @Override
                public void onEvent(KeyEvent event) throws Exception {
                    int keyCode = ((KeyEvent) event).getKeyCode();
                    Calendar cal = Calendar.getInstance();
                    if (keyCode == 38) { //Up
                        if (dt.getText().equals("")) {
                            dt.setValue(cal.getTime());
                        } else {
                            cal.set(dt.getValue().getYear(), dt.getValue().getMonth(), dt.getValue().getDay() + 1);
                            dt.setValue(cal.getTime());
                        }
                    } else if (keyCode == 40) { //Down
                        if (dt.getText().equals("")) {
                            dt.setValue(cal.getTime());
                        } else {
                            cal.set(dt.getValue().getYear(), dt.getValue().getMonth(), dt.getValue().getDay() - 1);
                            dt.setValue(cal.getTime());
                        }
                    }
                }

            });
            comp = dt;
        } else if (Type.equals("button")) {
            comp = new Button();
        } else if (Type.equals("intbox")) {
            comp = new Intbox();
        } else if (Type.equals("decimalbox")) {
            comp = new Decimalbox();
        } else if (Type.equals("combobox")) {
            comp = new Combobox();
        } else if (Type.equals("checkbox")) {
            comp = new Checkbox();
        } else if (Type.equals("treeview")) {
            comp = new Tree();
        } else if (Type.equals("timebox")) {
            comp = new Timebox();
        } else if (Type.equals("password")) {
            Textbox txt = new Textbox();
            txt.setType("password");
            comp = txt;
        }
        comp.setAttribute("TYPE", Type);
    }

    public String getUID() {
        return comp.getUuid();
    }

    public String getRefCombo() {
        return refCombo;
    }

    public void setRefCombo(String refCombo) {
        this.refCombo = refCombo;
    }

    public void setVisible(boolean isvisible) {
        comp.setVisible(isvisible);
    }

    public Component getComponent() {
//        if (comp instanceof Combobox) {
//            if (!getSearchproc().equals("") && !getDispfield().equals("") && !getValuefield().equals("")) {
//                fillCombo();
//            }
//        } else if (comp instanceof Tree) {
//            if (!getSearchproc().equals("") && !getDispfield().equals("") && !getValuefield().equals("")) {
//                fillTree();
//            }
//        }
        return comp;
    }

    public String getDataValue() {
        if (Type.equals("intbox") || Type.equals("decimalbox") || Type.equals("checkbox")) {
            return getValue().toString();
        } else if (Type.equals("datebox")) {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            Datebox dbox = (Datebox) getComponent();
            //System.out.println("------Date Value---" + df2.format(value));
            return df2.format(dbox.getValue());
        } else {
            return "'" + getValue() + "'";
        }
    }

    public boolean getBooleanValue() {
        if (Type.equals("checkbox")) {
            Checkbox txt = (Checkbox) comp;
            if (txt.isChecked()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Object getValue() {
        if (Type.equals("textbox")) {
            Textbox txt = (Textbox) comp;
            return txt.getText();
        } else if (Type.equals("datebox")) {
            Datebox txt = (Datebox) comp;
            if (txt.getValue() != null) {
//                SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                return txt.getValue();
            } else {
                return "";
            }
        } else if (Type.equals("intbox")) {
            Intbox txt = (Intbox) comp;
            if (txt.getValue() != null) {
                return txt.getValue();
            }
        } else if (Type.equals("display")) {
            Label txt = (Label) comp;
            if (!this.Value.equals("")) {
                return this.Value;
            }
            if (txt.getValue() != null) {
                return txt.getValue();
            }
        } else if (Type.equals("decimalbox")) {
            Decimalbox txt = (Decimalbox) comp;
            if (txt.getValue() != null) {
                return txt.getValue();
            }
        } else if (Type.equals("timebox")) {
            Timebox txt = (Timebox) comp;
            if (txt.getValue() != null) {
                return txt.getValue();
            }
        } else if (Type.equals("combobox")) {
            Combobox txt = (Combobox) comp;
            if (txt.getSelectedItem() != null) {
                //System.out.println("--Combo Value ---" + txt.getSelectedItem().getValue() + "--" + txt.getSelectedItem().getLabel() + "---" + txt.getValue());
                if (txt.getSelectedItem().getValue() != null) {
                    return txt.getSelectedItem().getValue();
                } else {
                    return txt.getSelectedItem().getLabel();
                }
//                return txt.getValue();
            } else {
                return "";
            }
        } else if (Type.equals("treeview")) {
            Tree txt = (Tree) comp;
            if (txt.getSelectedItem() != null) {
                //System.out.println("--Combo Value ---" + txt.getSelectedItem().getValue() + "--" + txt.getSelectedItem().getLabel() + "---" + txt.getValue());
                if (txt.getSelectedItem().getValue() != null) {
                    return txt.getSelectedItem().getValue();
                } else {
                    return txt.getSelectedItem().getLabel();
                }
//                return txt.getValue();
            } else {
                return "";
            }
        } else if (Type.equals("checkbox")) {
            Checkbox txt = (Checkbox) comp;
            if (txt.isChecked()) {
                return "1";
            } else {
                return "0";
            }
        } else {
            return "";
        }
        return "";
    }

    public void setHeight(String height) {
        if (comp instanceof Textbox) {
            Textbox txt = (Textbox) comp;
            if (!height.equals("")) {
                txt.setRows(Integer.parseInt(height));
            }
        }
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object value) {
        oldValue = getValue().toString();
    }

    public void setDisplayValue(Object value,Object dispvalue){
        if (value == null) {
            value = "";
        }
        if (dispvalue == null) {
            dispvalue = "";
        }
        if (Type.equals("display")) {
            Label txt = (Label) comp;
            txt.setValue(dispvalue.toString());
            this.Value=value.toString();
        }
    }
    
    public void setValue(Object value) {
        if (value == null) {
            value = "";
        }
        if (Type.equals("textbox")) {
            Textbox txt = (Textbox) comp;
            txt.setText(value.toString());
        } else if (Type.equals("datebox")) {
            Datebox txt = (Datebox) comp;
            //txt.setText(value.toString());
            //txt.setValue(value);
            if (!value.equals("")) {
                try {
                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                    //System.out.println("------Date Value---" + df2.format(value));
                    txt.setValue(df2.parse(value.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                txt.setText("");
            }
        } else if (Type.equals("intbox")) {
            Intbox txt = (Intbox) comp;
            txt.setText(value.toString());
        } else if (Type.equals("display")) {
            Label txt = (Label) comp;
            txt.setValue(value.toString());
        } else if (Type.equals("timebox")) {
            Timebox txt = (Timebox) comp;
            try {
                DateFormat df2 = new SimpleDateFormat("h:mm:ss");
                //System.out.println("------Date Value---" + df2.format(value));
                txt.setValue(df2.parse(value.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Type.equals("decimalbox")) {
            Decimalbox txt = (Decimalbox) comp;
            txt.setText(value.toString());
        } else if (Type.equals("combobox")) {
            Combobox txt = (Combobox) comp;
            if (value.equals("")) {
                txt.setValue("");
                txt.setText("");
            } else {
                for (int i = 0; i < txt.getItems().size(); i++) {
                    if (nullfinder(txt.getItems().get(i).getValue()).equals(value)) {
                        txt.setSelectedItem(txt.getItems().get(i));
                        txt.setText(txt.getItems().get(i).getLabel());
                        break;
                    }
                }
            }
        } else if (Type.equals("treeview")) {
            Tree txt = (Tree) comp;
            Object[] obs = txt.getItems().toArray();
            for (int i = 0; i < obs.length; i++) {
                try {
                    Treeitem item = (Treeitem) obs[i];
                    if (value.equals(item.getValue())) {
                        item.setSelected(true);
                        OpenTree(item);
                        break;
                    }
                } catch (Exception e) {
                }
            }
        } else if (Type.equals("checkbox")) {
            Checkbox txt = (Checkbox) comp;
            if (value.toString().toLowerCase().equals("1")) {
                txt.setChecked(true);
            } else if (value.toString().toLowerCase().equals("true")) {
                txt.setChecked(true);
            } else {
                txt.setChecked(false);
            }
        }
    }

    private void OpenTree(Treeitem item) {
        try {
            if (item instanceof Treeitem) {
                item.setOpen(true);
                if (item.getParentItem() != null) {
                    OpenTree(item.getParentItem());
                }
            }
        } catch (Exception e) {
        }
    }

    public static String nullfinder(Object data) {
        if (data == null) {
            data = "";
        }
        return data + "";
    }

    public void setWidth(String value) {
        if (Type.equals("textbox")) {
            Textbox txt = (Textbox) comp;
            txt.setWidth(value.toString());
        } else if (Type.equals("datebox")) {
            Datebox txt = (Datebox) comp;
            txt.setWidth(value.toString());
        } else if (Type.equals("timebox")) {
            Timebox txt = (Timebox) comp;
            txt.setWidth(value.toString());
        } else if (Type.equals("intbox")) {
            Intbox txt = (Intbox) comp;
            txt.setWidth(value.toString());
        } else if (Type.equals("decimalbox")) {
            Decimalbox txt = (Decimalbox) comp;
            txt.setWidth(value.toString());
        } else if (Type.equals("combobox")) {
            Combobox txt = (Combobox) comp;
            txt.setWidth(value.toString());
        } else if (Type.equals("display")) {
            Label txt = (Label) comp;
            txt.setWidth(value.toString());
        }
    }

    public void setDecimals(String decimal) {
        if (comp instanceof Decimalbox) {
            Decimalbox txt = (Decimalbox) comp;
            //txt.set(format);
        }
    }

    public void setList(String list) {
        this.list = list;
        if (comp instanceof Combobox) {
            Combobox txt = (Combobox) comp;
            String[] lst = list.split(",");
            for (int i = 0; i < lst.length; i++) {
                Comboitem item = new Comboitem(lst[i]);
                item.setValue(lst[i]);
                txt.appendChild(item);

            }
            //txt.set(format);
        }
    }

    public String getList() {
        return list;
    }

    private Component getParentTree(Treechildren treechildren, String parent) {
        for (int i = 0; i < treechildren.getChildren().size(); i++) {
            if (treechildren.getChildren().get(i) instanceof Treeitem) {
                Treeitem item = (Treeitem) treechildren.getChildren().get(i);
                //System.out.println("--" + item.getLabel() + "--" + item.getChildren());
                if (item.getValue().equals(parent)) {
                    if (item.getTreechildren() == null) {
                        item.appendChild(new Treechildren());
                    }
                    return item.getTreechildren();
                }
                if (item.getChildren() != null) {
                    for (int j = 0; j < item.getChildren().size(); j++) {
                        if (item.getChildren().get(j) instanceof Treeitem) {
                            Treeitem childComp = (Treeitem) item.getChildren().get(j);
                            return getParentTree(childComp.getTreechildren(), parent);
                        } else if (item.getChildren().get(j) instanceof org.zkoss.zul.Treechildren) {
                            return getParentTree((Treechildren) item.getChildren().get(j), parent);
                        }
                    }
                } else {
                    return item.getTreechildren();
                }
//                else {
//
//                    if (item.getTreechildren() != null) {
//                        Component comp = getParentTree(item.getTreechildren(), parent);
//                        System.out.println("--"+parent+"--"+item.getTreechildren());
//                        return comp;
//                    }
//                }
            } else if (treechildren.getChildren().get(i) instanceof Treechildren) {
                Treechildren item = (Treechildren) treechildren.getChildren().get(i);
                System.out.println("----Child Item--" + item);
                return getParentTree(item, parent);
            }
        }
        return null;
    }

    public static Treeitem addTreeRow(String value, String disp) {
        Treeitem treeitem = new Treeitem();
        treeitem.setId(value);
        treeitem.setValue(value);
        treeitem.setLabel(disp);
        treeitem.setOpen(false);
        //Treerow row=new Treerow();
        //Treecell cell=new Treecell(disp);
        //row.appendChild(cell);
        //treeitem.appendChild(row);
        return treeitem;
    }

    public void setFormat(String format) {
        if (comp instanceof Datebox) {
            Datebox txt = (Datebox) comp;
            txt.setFormat(format);
        } else if (comp instanceof Timebox) {
            Timebox txt = (Timebox) comp;
            txt.setFormat(format);
        } else if (comp instanceof Decimalbox) {
            Decimalbox txt = (Decimalbox) comp;
            txt.setFormat(format);
        }
    }

    public void setLength(String length) {
        if (!length.equals("")) {
            int len = Integer.parseInt(length);
            if (comp instanceof Textbox) {
                Textbox txt = (Textbox) comp;
                txt.setMaxlength(len);
            } else if (comp instanceof Decimalbox) {
                Decimalbox txt = (Decimalbox) comp;
                txt.setMaxlength(len);
            } else if (comp instanceof Intbox) {
                Intbox txt = (Intbox) comp;
                txt.setMaxlength(len);
            }
        }
    }

    public void setDisable(boolean enable) {
        if (comp instanceof Textbox) {
            Textbox txt = (Textbox) comp;
            txt.setDisabled(enable);
//            if (comp.getAttribute("TYPE")!=null) {
//                if (comp.getAttribute("TYPE").equals("display") || comp.getAttribute("TYPE").equals("fileupload")) {
//                    txt.setDisabled(true);
//                }
//            }
        } else if (comp instanceof Datebox) {
            Datebox txt = (Datebox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Timebox) {
            Timebox txt = (Timebox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Decimalbox) {
            Decimalbox txt = (Decimalbox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Intbox) {
            Intbox txt = (Intbox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Combobox) {
            Combobox txt = (Combobox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Tree) {
            Tree txt = (Tree) comp;
            if (enable) {
                txt.setNonselectableTags("*");
            } else {
                txt.setNonselectableTags("");
            }
        } else if (comp instanceof Checkbox) {
            Checkbox txt = (Checkbox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Radiogroup) {
            Radiogroup txt = (Radiogroup) comp;
            //txt.setD(true);
            for (int j = 0; j < txt.getChildren().size(); j++) {
                Radio radio = (Radio) txt.getChildren().get(j);
                radio.setDisabled(enable);
            }
        }
    }

    public void setReadonly(boolean enable) {
        if (comp instanceof Textbox) {
            Textbox txt = (Textbox) comp;
            txt.setReadonly(enable);
//            if (comp.getAttribute("TYPE")!=null) {
//                if (comp.getAttribute("TYPE").equals("display") || comp.getAttribute("TYPE").equals("fileupload")) {
//                    txt.setReadonly(true);
//                }
//            }
        } else if (comp instanceof Datebox) {
            Datebox txt = (Datebox) comp;
            txt.setReadonly(enable);
        } else if (comp instanceof Decimalbox) {
            Decimalbox txt = (Decimalbox) comp;
            txt.setReadonly(enable);
        } else if (comp instanceof Intbox) {
            Intbox txt = (Intbox) comp;
            txt.setReadonly(enable);
        } else if (comp instanceof Combobox) {
            Combobox txt = (Combobox) comp;
            txt.setReadonly(enable);
        } else if (comp instanceof Checkbox) {
            Checkbox txt = (Checkbox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Timebox) {
            Timebox txt = (Timebox) comp;
            txt.setDisabled(enable);
        } else if (comp instanceof Tree) {
            Tree txt = (Tree) comp;
            if (enable) {
                txt.setNonselectableTags("*");
            } else {
                txt.setNonselectableTags("");
            }
        } else if (comp instanceof Radiogroup) {
            Radiogroup txt = (Radiogroup) comp;
            //txt.setD(true);
            for (int j = 0; j < txt.getChildren().size(); j++) {
                Radio radio = (Radio) txt.getChildren().get(j);
                radio.setDisabled(enable);
            }
        }
    }

    public void setInlineEdit(boolean edit) {
        if (comp instanceof Textbox) {
            Textbox txt = (Textbox) comp;
            txt.setInplace(edit);
        } else if (comp instanceof Datebox) {
            Datebox txt = (Datebox) comp;
            txt.setInplace(edit);
        } else if (comp instanceof Combobox) {
            Combobox txt = (Combobox) comp;
            txt.setInplace(edit);
        } else if (comp instanceof Intbox) {
            Intbox txt = (Intbox) comp;
            txt.setInplace(edit);
        } else if (comp instanceof Decimalbox) {
            Decimalbox txt = (Decimalbox) comp;
            txt.setInplace(edit);
        }
    }

    public void setId(String id) {
        comp.setAttribute("Name", id);
    }

    public String setId() {
        if (comp.getAttribute("Name") == null) {
            return "";
        }
        return comp.getAttribute("Name").toString();
    }

    public void setMessage(String msg) {
        if (comp instanceof Textbox) {
            Textbox txt = (Textbox) comp;
            txt.setErrorMessage(msg);
        } else if (comp instanceof Datebox) {
            Datebox txt = (Datebox) comp;
            txt.setErrorMessage(msg);
        } else if (comp instanceof Combobox) {
            Combobox txt = (Combobox) comp;
            txt.setErrorMessage(msg);
        }

    }

    public void setChangeEvent(final String ClassName, final String Code) {
        comp.addEventListener("onChange", new EventListener<Event>() {

            public void onEvent(Event event) throws Exception {
                System.out.println("--------in Change Event-" + ClassName + Code);
                //Automate_Build.automateBuild(ClassName, Code, OMComponent.this);
                // Automate_Build.automateBuild("YironTest", "System.out.println(args[0].getValue())",this);
            }
        });
    }

    public String getType() {
        return Type;
    }

    public void setFormula(final String formula, DbTable MainTable) {
        this.formula = formula;
        final List<OMComponent> compsList = new ArrayList<OMComponent>();
        for (int i = 0; i < MainTable.getComponents().length; i++) {
            if (formula.contains(MainTable.getComponents()[i].getFieldName())) {
                compsList.add(MainTable.getComponents()[i]);
            }
        }
        for (int i = 0; i < compsList.size(); i++) {
            OMComponent formulaComp = compsList.get(i);
            formulaComp.getComponent().addEventListener("onChange", new EventListener<Event>() {

                @Override
                public void onEvent(Event event) throws Exception {
                    String tmpFormula = formula;
                    for (int i = 0; i < compsList.size(); i++) {
                        OMComponent tmpformulaComp = compsList.get(i);
                        String value = tmpformulaComp.getValue().toString();
                        if (value.equals("")) {
                            value = "0";
                        }
                        tmpFormula = tmpFormula.replace(tmpformulaComp.getFieldName(), value);
                        System.out.println("--tmpFormula->" + tmpFormula);
                    }

                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    try {
                        setValue(engine.eval(tmpFormula));
                    } catch (Exception e) {
                        System.out.println("---" + e);
                    }
                }
            });
        }
    }
}
