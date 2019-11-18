package arboles;

public class arbolbuscador {

    nodo root;
    int tamaño;

    public arbolbuscador() {
        root = null;
        tamaño = 0;
    }

    public arbolbuscador(nodo root) {
        root = root;
        tamaño = 1;
    }

    public boolean isEmpty() {
        if (root == null) {
            return true;
        } else {
            return false;
        }
    }

    public nodo getRoot() {
        return root;
    }

    public void insert(nodo root, nodo n) {
        if (isEmpty()) {
            this.root = n;
            tamaño++;
        } else {
            int datoRoot = (int) root.getDato();
            int datoN = (int) n.getDato();
            if (datoRoot > datoN) {
                if (root.getBack() == null) {
                    root.linkBack(n);
                    tamaño++;
                } else {
                    insert(root.getBack(), n);
                }
            } else {
                if (root.getNext() == null) {
                    root.linkNext(n);
                    tamaño++;
                } else {
                    insert(root.getNext(), n);
                }
            }
        }
    }

    public void print(nodo root) {
        if (root != null) {
            print(root.getBack());
            System.out.println(((nodo)root.getDato()).getDato());
            print(root.getNext());
        }
    }

    //Busca el nodo especificado pasandole la raiz del arbol
    protected nodo search(nodo raizSub, nodo buscado) {
        //Comprueba que no este vacio
        if (raizSub == null) {
            return null;
        } else if (buscado.igualQue((nodo)raizSub.getDato()))//Comprueba si el elemento esta en la raiz del arbol
        {
            return root;
        } else if (buscado.menorQue((raizSub.getDato()))) //Comienza busqueda por la izq
        {
            return search(raizSub.getBack(), buscado);
        } else {
            return search(raizSub.getNext(), buscado); //Comienza la busqueda por la derecha
        }
    }

    public nodo delete(nodo raizSub, nodo dato) {
        if (raizSub == null) {
            System.out.println("No encontrado el nodo con la clave");
        } else if (dato.menorQue((nodo)raizSub.getDato())) {
            nodo iz;
            iz = delete(raizSub.getBack(), dato);
            raizSub.linkBack(iz);
        } else if (dato.mayorQue((nodo)raizSub.getDato())) {
            nodo dr;
            dr = delete(raizSub.getNext(), dato);
            raizSub.linkNext(dr);
        } else {
            nodo q;
            q = raizSub;
            if (q.getBack() == null) {
                raizSub = q.getBack();
            } else {
                q = replace(q);
            }
            q = null;
        }
        return raizSub;
    }

    private nodo replace(nodo act) {
        nodo a, p;
        p = act;
        a = act.getBack();
        while (a.getNext() != null) {
            p = a;
            a = a.getNext();
        }
        act.putDato(a.getDato());
        if (p == act) {
            p.linkBack(a.getBack());
        } else {
            p.linkNext(a.getBack());
        }
        return a;
    }

    //Rotaciones
    private nodo rotacionII(nodo n, nodo n1) {
        n.linkBack(n1.getNext());
        n1.linkNext(n);
// actualización de los factores de equilibrio
        if (n1.fe == -1) // se cumple en la inserción
        {
            n.fe = 0;
            n1.fe = 0;
        } else {
            n.fe = -1;
            n1.fe = 1;
        }
        return n1;
    }

    private nodo rotacionDD(nodo n, nodo n1) {
        n.linkNext(n1.getBack());
        n1.linkBack(n);
// actualización de los factores de equilibrio
        if (n1.fe == +1) // se cumple en la inserción
        {
            n.fe = 0;
            n1.fe = 0;
        } else {
            n.fe = +1;
            n1.fe = -1;
        }
        return n1;
    }

    private nodo rotacionID(nodo n, nodo n1) {
        nodo n2;
        n2 = (nodo) n1.getNext();
        n.linkBack(n2.getNext());
        n2.linkNext(n);
        n1.linkNext(n2.getBack());
        n2.linkBack(n1);
// actualización de los factores de equilibrio
        if (n2.fe == +1) {
            n1.fe = -1;
        } else {
            n1.fe = 0;
        }
        if (n2.fe == -1) {
            n.fe = 1;
        } else {
            n.fe = 0;
        }
        n2.fe = 0;
        return n2;
    }

    private nodo rotacionDI(nodo n, nodo n1) {
        nodo n2;
        n2 = (nodo) n1.getBack();
        n.linkNext(n2.getBack());
        n2.linkBack(n);
        n1.linkBack(n2.getNext());
        n2.linkNext(n1);
// actualización de los factores de equilibrio
        if (n2.fe == +1) {
            n.fe = -1;
        } else {
            n.fe = 0;
        }
        if (n2.fe == -1) {
            n1.fe = 1;
        } else {
            n1.fe = 0;
        }
        n2.fe = 0;
        return n2;
    }

    //Insercion con balanceo
    public void insertar(nodo valor) throws Exception {
        nodo dato;
        Logical h = new Logical(false); // intercambia un valor booleano
        dato =  valor;
        root = insertarAvl(root, dato, h);
    }

    public nodo insertarAvl(nodo raiz, nodo dt, Logical h) throws Exception{
        nodo n1;
        if (raiz == null) {
            raiz = new nodo(dt);
            h.setLogical(true);
        } else if (dt.menorQue(raiz.getDato())) {
            nodo iz;
            iz = insertarAvl(raiz.getBack(), dt, h);
            raiz.linkBack(iz);
// regreso por los nodos del camino de búsqueda
            if (h.booleanValue()) {
// decrementa el fe por aumentar la altura de rama izquierda
                switch (raiz.fe) {
                    case 1:
                        raiz.fe = 0;
                        h.setLogical(false);
                        break;
                    case 0:
                        raiz.fe = -1;
                        break;
                    case -1: // aplicar rotación a la izquierda
                        n1 = raiz.getBack();
                        if (n1.fe == -1) {
                            raiz = rotacionII(raiz, n1);
                        } else {
                            raiz = rotacionID(raiz, n1);
                        }
                        h.setLogical(false);
                }
            }
        } else if (dt.mayorQue(raiz.getDato())) {
            nodo dr;
            dr = insertarAvl(raiz.getNext(), dt, h);
            raiz.linkNext(dr);
// regreso por los nodos del camino de búsqueda
            if (h.booleanValue()) {
// incrementa el fe por aumentar la altura de rama izquierda
                switch (raiz.fe) {
                    case 1: // aplicar rotación a la derecha
                        n1 = (nodo) raiz.getNext();
                        if (n1.fe == +1) {
                            raiz = rotacionDD(raiz, n1);
                        } else {
                            raiz = rotacionDI(raiz, n1);
                        }
                        h.setLogical(false);
                        break;
                    case 0:
                        raiz.fe = +1;
                        break;
                    case -1:
                        raiz.fe = 0;
                        h.setLogical(false);
                }
            }
        } else {
            throw new Exception("No puede haber claves repetidas ");
        }
        return raiz;
    }

}
