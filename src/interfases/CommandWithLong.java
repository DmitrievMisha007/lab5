package interfases;

/**
 * Интерфейс команды с параметром long
 */
public interface CommandWithLong {
    /**
     * Вызывает команду
     * @param x Значение параметра long требуемого командой
     */
    void execute(long x);
}
