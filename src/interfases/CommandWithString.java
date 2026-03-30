package interfases;

/**
 * Интерфейс команды с параметром String
 */
public interface CommandWithString {
    /**
     * Вызывает команду
     * @param x Значение параметра String требуемого командой
     */
    void execute(String x);
}
