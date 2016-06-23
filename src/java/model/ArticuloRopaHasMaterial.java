package model;
// Generated Jun 22, 2016 1:19:40 PM by Hibernate Tools 4.3.1



/**
 * ArticuloRopaHasMaterial generated by hbm2java
 */
public class ArticuloRopaHasMaterial  implements java.io.Serializable {


     private ArticuloRopaHasMaterialId id;
     private ArticuloRopa articuloRopa;
     private Material material;
     private int cantidad;

    public ArticuloRopaHasMaterial() {
    }

    public ArticuloRopaHasMaterial(ArticuloRopaHasMaterialId id, ArticuloRopa articuloRopa, Material material, int cantidad) {
       this.id = id;
       this.articuloRopa = articuloRopa;
       this.material = material;
       this.cantidad = cantidad;
    }
   
    public ArticuloRopaHasMaterialId getId() {
        return this.id;
    }
    
    public void setId(ArticuloRopaHasMaterialId id) {
        this.id = id;
    }
    public ArticuloRopa getArticuloRopa() {
        return this.articuloRopa;
    }
    
    public void setArticuloRopa(ArticuloRopa articuloRopa) {
        this.articuloRopa = articuloRopa;
    }
    public Material getMaterial() {
        return this.material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }




}


