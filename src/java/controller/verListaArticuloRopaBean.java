/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOArticuloRopa;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.ArticuloRopa;

/**
 *
 * @author david
 */
@ManagedBean
@ViewScoped
public class verListaArticuloRopaBean {

    private List<ArticuloRopa> listArticuloRopa;
    
    private DAOArticuloRopa daoArticuloRopa;
    
    public verListaArticuloRopaBean() {
        daoArticuloRopa = new DAOArticuloRopa();
        
        listArticuloRopa = daoArticuloRopa.getListArticuloRopa();
    }

    public List<ArticuloRopa> getListArticuloRopa() {
        return listArticuloRopa;
    }

    public void setListArticuloRopa(List<ArticuloRopa> listArticuloRopa) {
        this.listArticuloRopa = listArticuloRopa;
    }
}
