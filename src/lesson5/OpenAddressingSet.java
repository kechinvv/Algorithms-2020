package lesson5;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;
    private boolean[] deleted;

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
        deleted = new boolean[capacity];
    }

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }


    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (!deleted[index] && current.equals(o)) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     * <p>
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     * <p>
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null && !deleted[index]) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        deleted[index] = false;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     * <p>
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        int remIndex = startingIndex(o);
        int index = remIndex;
        while (true) {
            if (storage[index] == null || deleted[index]) return false;
            if (storage[index].equals(o)) {
                deleted[index] = true;
                break;
            }
            index = (index + 1) % capacity;
            if (index == remIndex) {
                return false;
            }
        }
        size--;
        return true;
    }
    // сложность: в худшем случае O(n)  Ресурсоемкость: O(n)

    /**
     * Создание итератора для обхода таблицы
     * <p>
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Средняя (сложная, если поддержан и remove тоже)
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new OpenAddressingSetIterator();
    }

    public class OpenAddressingSetIterator implements Iterator<T> {
        int already = 0;
        Object cur = null;
        private int index = -1;

        @Override
        public boolean hasNext() {
            return already < size;
        }
        // сложность: O(1)  Ресурсоемкость: O(1)

        @Override
        public T next() {
            if (hasNext()) {
                index++;
                while (storage[index] == null || deleted[index]) index++;
                cur = storage[index];
                already++;
                return (T) cur;
            } else throw new NoSuchElementException();
        }
        // сложность: в худшем случае O(n)  Ресурсоемкость: O(n)

        @Override
        public void remove() {
            if (index>-1 && storage[index] != null && !deleted[index]) {
                deleted[index]=true;
                size--;
                already--;
            } else throw new IllegalStateException();
        }
        // сложность: O(1)  Ресурсоемкость: O(1)
    }
}
