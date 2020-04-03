package com.zt.data.observe.bean;

import com.orient.me.data.table.ICellItem;

public class TableIntCell implements ICellItem {

    private String name;
    private String value;
    private int type;
    private long id;
    private int row;
    private int col;
    private int widthSpan = 1;
    private int heightSpan = 1;

    public TableIntCell() {
    }


    public TableIntCell(String name, String value, int type, int row, int col, int rowSpan, int colSpan) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.row = row;
        this.col = col;
        this.widthSpan = rowSpan;
        this.heightSpan = colSpan;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public void setValue(float value) {
        this.value = String.valueOf(value);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setWidthSpan(int widthSpan) {
        this.widthSpan = widthSpan;
    }

    public void setHeightSpan(int heightSpan) {
        this.heightSpan = heightSpan;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public int getWidthSpan() {
        return widthSpan;
    }

    @Override
    public int getHeightSpan() {
        return heightSpan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
