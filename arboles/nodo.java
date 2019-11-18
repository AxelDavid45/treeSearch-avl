package arboles;


public  class nodo<E> implements Comparador{

    Object dato;
    nodo next, back;
    int fe; //Propiedad que ayuda a identificar si el nodo esta en equilibrio tomando valores -1 , 0, 1

    public nodo() {
        dato = null;
        next = back = null;
        fe = 0;
    }

    public nodo(E dato) {
        this.dato = dato;
        next = back = null;
        fe = 0;
    }

    public nodo(E dato, nodo after, nodo next) {
        this.dato = dato;
        this.next = next;
        this.back = back;
    }

    void putDato(E dato) {
        this.dato = dato;
    }

    public E getDato() {
        return (E) dato;
    }

    void linkBack(nodo x) {
        this.back = x;
    }

    void linkNext(nodo x) {
        this.next = x;
    }

    public nodo getNext() {
        return next;
    }

    public nodo getBack() {
        return back;
    }
    
    
    @Override
    public boolean igualQue(Object q){
        nodo x = (nodo) q;
        return (int)dato==(int)x.getDato();
    }
  

    @Override
    public boolean menorQue(Object q){
        nodo x = (nodo) q;
        return (int) dato < (int) x.getDato();
    }
    
    @Override
    public boolean menorIgualQue(Object q){
        nodo x = (nodo) q;
        return (int) dato <= (int) x.getDato();
        
    }
    

    @Override
    public boolean mayorQue(Object q){
        nodo x = (nodo) q;
        return (int)dato>(int) x.getDato();
    }
   
    @Override
    public boolean mayorIgualQue(Object q){
        nodo x = (nodo) q;
        return (int)dato>=(int) x.getDato();
    }
    
    //Rotaciones del arbol
    

}
