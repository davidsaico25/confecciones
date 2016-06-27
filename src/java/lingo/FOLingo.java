/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lingo;

import com.lindo.Lingd15;
import java.util.ArrayList;
import java.util.List;
import model.ArticuloRopa;
import model.ArticuloRopaHasMaterial;
import model.Linea;
import model.Material;

/**
 *
 * @author david
 */
public class FOLingo {

    double[] costoDisenio = new double[2];
    double[] costoDesfile = new double[2];
    double[] precioArticulo = new double[11];
    double[] costoMOMQ = new double[11];
    double[] demanda = new double[11];
    double[] precioYarda = new double[7];
    double[] matDisp = new double[7];
    double[] cantidadYarda = new double[77];

    double[] solX = new double[11];
    double[] solY = new double[7];

    // Load the Lingo JNI interface
    static {
        System.loadLibrary("Lingj64_15");
    }

    // Create a new Lingo object, which we will use to interface with Lingo
    Lingd15 lng = new Lingd15();

    // Stores the Lingo JNI environment pointer
    Object pLngEnv;

    int nLastIterationCount;

    public FOLingo(List<Linea> listLinea, List<ArticuloRopa> listArticuloRopa, List<Material> listMaterial, List<ArticuloRopaHasMaterial> listArticuloRopaHasMaterial) {
        System.out.println("FOLingo");
        // Create the Lingo environment.
        // We do this here in the constructor so as not to repeat this
        // for each subsequent solve, improving performance.
        // Be sure to delete the Lingo environment on exit.
        pLngEnv = lng.LScreateEnvLng();
        if (pLngEnv == null) {
            System.out.println("Unable to create Lingo environment");
            return;
        }

        for (int i = 0; i < costoDisenio.length; i++) {
            costoDisenio[i] = listLinea.get(i).getCostoDiseno();
            costoDesfile[i] = listLinea.get(i).getCostoDesfile();
            System.out.println(costoDisenio[i] + " " + costoDesfile[i]);
        }
        for (int i = 0; i < listArticuloRopa.size(); i++) {
            precioArticulo[i] = listArticuloRopa.get(i).getPrecioVenta();
            costoMOMQ[i] = listArticuloRopa.get(i).getPrecioCosto();
            demanda[i] = listArticuloRopa.get(i).getDemanda();
        }
        for (int i = 0; i < listMaterial.size(); i++) {
            precioYarda[i] = listMaterial.get(i).getPrecioCostoPorYarda();
            matDisp[i] = listMaterial.get(i).getCantidad();
        }
        for (int i = 0; i < listArticuloRopaHasMaterial.size(); i++) {
            cantidadYarda[i] = listArticuloRopaHasMaterial.get(i).getCantidad();
        }
    }

    private static int MySolverCallback(Object pnLng, int iLoc, Object jobj) {
        FOLingo foLingo = (FOLingo) jobj;
        int nIterations[] = new int[1];
        foLingo.lng.LSgetCallbackInfoIntLng(pnLng, Lingd15.LS_IINFO_ITERATIONS_LNG, nIterations);
        return (0);
    }

    private static int MyErrorCallback(Object pnLng, int nErrorCode, String jsErrMessage, Object jobj) {
        System.out.println("Lingo error code: " + nErrorCode);
        System.out.println("Lingo error message:\n\n " + jsErrMessage);
        return (0);
    }

    public List<List<Double>> solve() {
        List<List<Double>> listSolucion = new ArrayList<>();

        double dStatus[] = new double[1];

        try {
        } catch (Exception e) {
            System.out.println("Invalid staffing needs data.");
            return null;
        }

        // open a log file
        int nErr = lng.LSopenLogFileLng(pLngEnv, "lingo.log");
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSopenLogFileLng() error: " + nErr);
            return null;
        }

        // pass Lingo the name of the solver callback function...
        nErr = lng.LSsetCallbackSolverLng(pLngEnv, "MySolverCallback", this);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetCallbackSolverLng() error");
            return null;
        }

        // ...and the error callback function
        nErr = lng.LSsetCallbackErrorLng(pLngEnv, "MyErrorCallback", this);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetCallbackErrorLng() error");
            return null;
        }

        // pass lingo a pointer to the DAYS set
        int[] nPointersNow = new int[1];

        // pass lingo a pointer to the staffing needs
        nErr = lng.LSsetPointerLng(pLngEnv, costoDisenio, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        // pass pointers to output areas
        nErr = lng.LSsetPointerLng(pLngEnv, costoDesfile, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, precioArticulo, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, costoMOMQ, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, demanda, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, precioYarda, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, matDisp, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, cantidadYarda, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, solX, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, solY, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        nErr = lng.LSsetPointerLng(pLngEnv, dStatus, nPointersNow);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("LSsetPointerLng() error");
            return null;
        }

        // construct the script
        // echo input to log file
        String sScript = "SET ECHOIN 1" + "\n";

        // load the model from disk
        sScript = sScript + "TAKE C:\\Users\\david\\Documents\\NetBeansProjects\\confecciones\\proyecto.lng" + "\n";

        // view the formulation
        sScript = sScript + "LOOK ALL" + "\n";

        // solve
        sScript = sScript + "GO" + "\n";

        // exit script processor
        sScript = sScript + "QUIT" + "\n";

        // run the script
        dStatus[0] = -1;
        nLastIterationCount = -1;
        nErr = lng.LSexecuteScriptLng(pLngEnv, sScript);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("***LSexecuteScriptLng error***: " + nErr);
            return null;
        }

        // clear the pointers to force update of local arrays
        // ***NOTE*** solution won't get passed to local arrays until
        // LSclearPointersLng is called!!!
        nErr = lng.LSclearPointersLng(pLngEnv);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("***LSclearPointerLng() error***: " + nErr);
            return null;
        }

        // check the solution status
        if (dStatus[0] != lng.LS_STATUS_GLOBAL_LNG) {
            System.out.println("***Unable to Solve*** dStatus:" + dStatus[0]);
        }

        // close Lingo's log file
        nErr = lng.LScloseLogFileLng(pLngEnv);
        if (nErr != lng.LSERR_NO_ERROR_LNG) {
            System.out.println("***LScloseLogFileLng() error***: " + nErr);
            return null;
        }

        List<Double> listSolX = new ArrayList<>();
        List<Double> listSolY = new ArrayList<>();
        for (int i = 0; i < solX.length; i++) {
            listSolX.add(solX[i]);
        }
        for (int i = 0; i < solY.length; i++) {
            listSolY.add(solY[i]);
        }
        listSolucion.add(listSolX);
        listSolucion.add(listSolY);
        return listSolucion;
    }
}
