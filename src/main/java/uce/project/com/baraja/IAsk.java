package uce.project.com.baraja;

public interface IAsk<T> {
    T ask(String promt);
    T ask(String promt, String instructions);
}
