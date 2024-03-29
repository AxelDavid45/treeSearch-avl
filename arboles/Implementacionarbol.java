package arboles;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Implementacionarbol {

    public static void main(String[] args) {
        try {
            arbolbuscador myTree = new arbolbuscador();

            myTree.insertar(new nodo(8));
            myTree.insertar(new nodo(3));
            myTree.insertar(new nodo(1));
            myTree.insertar(new nodo(20));
            myTree.insertar(new nodo(10));
            myTree.insertar(new nodo(5));

            myTree.print(myTree.getRoot());
            System.out.println("-----------");
            myTree.eliminar(new nodo(10));
            myTree.print(myTree.getRoot());
            System.out.println("-------------");
            System.out.println("Altura del arbol: "+myTree.altura(myTree.getRoot()));

              
        } catch (Exception ex) {
            Logger.getLogger(Implementacionarbol.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
