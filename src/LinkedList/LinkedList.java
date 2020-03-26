package LinkedList;

public class  LinkedList<T> {
    private node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public LinkedList(T[] valuesArray){
        for (T value:valuesArray) {
            add(value);
        }
        size = valuesArray.length;
    }

    public void add(T value){
        if(head == null){
            head = new node<>(value,null);
        }else{
            node<T> buffer = head;
            while(buffer.next!=null){
                buffer = buffer.next;
            }
            buffer.next = new node<T>(value,buffer);
        }
        size++;
    }

    public void add(T value,int index){
        if(index<0) throw new IndexOutOfBoundsException("Бро, не бывает отрицательного индекса");
        else if(index>size || index == 0 && size == 0){//Если значение за правой границей списка или если первый элемент а список пустой
            add(value);
        }else if (index == 0){//если просто в голову
            head.previous = new node<T>(value,null);
            node<T> nextNode = head;
            head = head.previous;
            head.next = nextNode;
        }else{
            node<T> buffer = head;
            for(int i = 0; i<index-1;i++){//Когда ебанул тут -1 тупо мемесная ситуация))
                buffer = buffer.next;
            }
            node<T> nextNode = buffer;
            node<T> prevNode = buffer.previous;
            prevNode.next = new node<T>(value,prevNode);
            prevNode.next.next = buffer;
            buffer.previous = prevNode.next;
        }
        size++;
    }

    public T get(int index){
        if(index >= size || index < 0) throw new IndexOutOfBoundsException("Индекс находится вне границ списка");
        node<T> buffer = head;
        for(int i = 0; i<index;i++){
            buffer = buffer.next;
        }
        return buffer.value;
    }

    public T set(T value, int index){
        if(index<0 || index>=size) throw new IndexOutOfBoundsException("Нельзя менять значения с номерами которых нет в списке");
        node<T> buffer = head;
        for(int i = 0; i<index;i++){
            buffer = buffer.next;
        }
        T forReturn = buffer.value;
        buffer.value = value;
        return forReturn;
    }

    public T delete(int index){
        if(index >= size || index < 0) throw new IndexOutOfBoundsException("Индекс находится вне границ списка");
        if(index == 0){//Если первый элемент то сдвигаем следующий на его метсо
            node<T> buffer = head;
            head = head.next;
            return buffer.value;
        }
        node<T> buffer = head;
        for(int i = 0; i<=index-1;i++){
            buffer = buffer.next;
        }
        if(index == size-1){//если у нас последний элемент то последующий в предыдущем заменяется на нул
            node<T> forReturn = buffer;
            buffer = buffer.previous;
            buffer.next = null;
            return forReturn.value;
        }
        //ну и если мы получили элемент где то по середине - выпилим его из мира
        //вообще канешн и наверн это можно упростить, но мне кажется что при упрощении потеряется значение buffer
        buffer.previous.next = buffer.next;
        return buffer.value;
    }

    public T delete(T value){
        if(head.value.equals(value)){
            node<T> buffer = head;
            head = head.next;
            size--;
            return buffer.value;
        }
        node<T> buffer = head;
        while (!head.value.equals(value)){
            buffer = buffer.next;
            if(buffer.value.equals(value)){
                if(buffer.next!=null){//Если это не последний элементо, то
                    buffer.previous.next = buffer.next;
                    size--;
                    return buffer.value;
                }else{//Если всё же последний
                    node<T> forReturn = buffer;
                    buffer = buffer.previous;
                    buffer.next = null;
                    size--;
                    return forReturn.value;
                }
            }
        }
        return null;
    }

    public int indexOf(T value){
        if(head==null) return -1;
        int count = 0;
        node buf = head;
        while(head.next!=null){
            buf = buf.next;
            count++;
            if(buf.value.equals(value)) return count;
        }
        return -1;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if(head!=null){
            sb.append('[').append(head.value.toString()).append(']');
            node<T> buffer = head;
            while(buffer.next!=null){
                buffer = buffer.next;
                sb.append('[').append(buffer.value.toString()).append(']');
            }
        } else {
            return sb.append("[пустой список]").toString();
        }
        return sb.toString();
    }
}

class node<T>{
    public T value;
    public node<T> next;
    public node<T> previous;

    public node(T value, node<T> previous) {
        this.value = value;
        this.previous = previous;
    }
}
