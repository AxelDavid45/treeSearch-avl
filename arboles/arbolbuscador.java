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
            System.out.println(((nodo) root.getDato()).getDato());
            print(root.getNext());
        }
    }

    //Busca el nodo especificado pasandole la raiz del arbol
    protected nodo search(nodo raizSub, nodo buscado) {
        //Comprueba que no este vacio
        if (raizSub == null) {
            return null;
        } else if (buscado.igualQue((nodo) raizSub.getDato()))//Comprueba si el elemento esta en la raiz del arbol
        {
            return root;
        } else if (buscado.menorQue((nodo) raizSub.getDato())) //Comienza busqueda por la izq
        {
            return search(raizSub.getBack(), buscado);
        } else {
            return search(raizSub.getNext(), buscado); //Comienza la busqueda por la derecha
        }
    }

    public nodo delete(nodo raizSub, nodo dato) {
        if (raizSub == null) {
            System.out.println("No encontrado el nodo con la clave");
        } else if (dato.menorQue((raizSub.getDato()))) {
            nodo iz;
            iz = delete(raizSub.getBack(), dato);
            raizSub.linkBack(iz);
        } else if (dato.mayorQue(raizSub.getDato())) {
            nodo dr;
            dr = delete(raizSub.getNext(), dato);
            raizSub.linkNext(dr);
        } else {
            nodo q;
            q = raizSub;
            if (q.getBack() == null) {
                raizSub = (nodo) q.getNext();
            } else if (q.getNext() == null) {
                raizSub = (nodo) q.getBack();
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
        dato = valor;
        root = insertarAvl(root, dato, h);
    }

    public nodo insertarAvl(nodo raiz, nodo dt, Logical h) throws Exception {
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

    public void eliminar(nodo valor) throws Exception {
        nodo dato;
        dato =  valor;
        Logical flag = new Logical(false);
        root = borrarAvl(root, dato, flag);
    }

    private nodo borrarAvl(nodo r, nodo clave,Logical cambiaAltura) throws Exception {
        if (r == null) {
            throw new Exception(" Nodo no encontrado ");
        } else if (clave.menorQue(r.getDato())) {
            nodo iz;
            iz = borrarAvl((nodo) r.getBack(), clave, cambiaAltura);
            r.linkBack(iz);
            if (cambiaAltura.booleanValue()) {
                r = equilibrar1(r, cambiaAltura);
            }
        } else if (clave.mayorQue(r.getDato())) {
            nodo dr;
            dr = borrarAvl((nodo) r.getNext(), clave, cambiaAltura);
            r.linkNext(dr);
            if (cambiaAltura.booleanValue()) {
                r = equilibrar2(r, cambiaAltura);
            }
        } else // Nodo encontrado
        {
            nodo q;
            q = r; // nodo a quitar del árbol
            if (q.getBack()== null) {
                r = (nodo) q.getNext();
                cambiaAltura.setLogical(true);
            } else if (q.getNext()== null) {
                r = (nodo) q.getBack();
                cambiaAltura.setLogical(true);
            } else { // tiene rama izquierda y derecha

                nodo iz;
                iz = reemplazar(r, (nodo) r.getBack(), cambiaAltura);
                r.linkBack(iz);
                if (cambiaAltura.booleanValue()) {
                    r = equilibrar1(r, cambiaAltura);
                }
            }
            q = null;
        }
        return r;
    }

    private nodo reemplazar(nodo n, nodo act, Logical cambiaAltura) {
        if (act.getNext()!= null) {
            nodo d;
            d = reemplazar(n, (nodo) act.getNext(), cambiaAltura);
            act.linkNext(d);
            if (cambiaAltura.booleanValue()) {
                act = equilibrar2(act, cambiaAltura);
            }
        } else {
            n.putDato(act.getDato());
            n = act;
            act = (nodo) act.getBack();
            n = null;
            cambiaAltura.setLogical(true);
        }
        return act;
    }

    private nodo equilibrar1(nodo n, Logical cambiaAltura) {
        nodo n1;
        switch (n.fe) {
            case -1:
                n.fe = 0;
                break;
            case 0:
                n.fe = 1;
                cambiaAltura.setLogical(false);
                break;
            case +1: //se aplicar un tipo de rotación derecha
                n1 = (nodo) n.getNext();
                if (n1.fe >= 0) {
                    if (n1.fe == 0) //la altura no vuelve a disminuir
                    {
                        cambiaAltura.setLogical(false);
                    }
                    n = rotacionDD(n, n1);
                } else {
                    n = rotacionDI(n, n1);
                }
                break;
        }
        return n;
    }

    private nodo equilibrar2(nodo n, Logical cambiaAltura) {
        nodo n1;
        switch (n.fe) {
            case -1: // Se aplica un tipo de rotación izquierda
                n1 = (nodo) n.getBack();
                if (n1.fe <= 0) {
                    if (n1.fe == 0) {
                        cambiaAltura.setLogical(false);
                    }
                    n = rotacionII(n, n1);
                } else {
                    n = rotacionID(n, n1);
                }
                break;
            case 0:
                n.fe = -1;
                cambiaAltura.setLogical(false);
                break;
            case +1:
                n.fe = 0;
                break;
        }
        return n;
    }

}
