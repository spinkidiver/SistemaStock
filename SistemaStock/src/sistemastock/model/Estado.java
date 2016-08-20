/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemastock.model;

/**
 *
 * @author Tke Kaiser
 */
public enum Estado {
     ACTIVO(true), DESACTIVO(false);
        boolean estado;

        private Estado(boolean estado) {
            this.estado = estado;
        }

        public boolean getEstado() {
            return estado;
        }
    
}
