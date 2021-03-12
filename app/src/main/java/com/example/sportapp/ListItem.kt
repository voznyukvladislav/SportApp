package com.example.sportapp

class ListItem {
    private var name: String = ""
    private var nameButCooler: String = ""

    public constructor(name: String, nameButCooler: String) {
        this.name = name
        this.nameButCooler = nameButCooler
    }
    public fun getJopa(): String {
        return (this.name + " " + this.nameButCooler)
    }
    public fun getName(): String {
        return this.name
    }
    public fun getCoolerName(): String {
        return this.nameButCooler
    }
}