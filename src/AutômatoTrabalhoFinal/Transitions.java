/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutômatoTrabalhoFinal;

/**
 *
 * @author gustavo
 */
public class Transitions {

    private String estadoI;
    private String estadoF;
    private String simbolo;

    public Transitions(String estadoI, String simbolo, String estadoF) {
        this.estadoI = estadoI;
        this.simbolo = simbolo;
        this.estadoF = estadoF;
    }

    public String getEstadoI() {
        return estadoI;
    }

    public void setEstadoI(String estadoI) {
        this.estadoI = estadoI;
    }

    public String getEstadoF() {
        return estadoF;
    }

    public void setEstadoF(String estadoF) {
        this.estadoF = estadoF;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public String toString() {
        return String.format("T(%s, %s) = %s",
                this.estadoI, this.simbolo, this.estadoF);
    }

}
