package interfases;

/**
 * Интерфейс команды с параметром double
 */
public interface CommandWithDouble {
    /**
     * Вызывает команду
     * @param x Значение параметра double требуемого командой
     */
    void execute(double x);
}
